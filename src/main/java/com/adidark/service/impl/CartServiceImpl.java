package com.adidark.service.impl;

import com.adidark.converter.CartDTOConverter;
import com.adidark.entity.CartEntity;
import com.adidark.entity.CartItemEntity;
import com.adidark.entity.ProductSizeEntity;
import com.adidark.exception.DataNotFoundException;
import com.adidark.model.dto.CartDTO;
import com.adidark.repository.CartItemRepository;
import com.adidark.repository.CartRepository;
import com.adidark.repository.UserRepository;
import com.adidark.service.CartService;
import com.adidark.service.ProductService;
import com.adidark.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartDTOConverter cartDTOConverter;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSizeService productSizeService;

    @Override
    public void flush() {
        cartRepository.flush();
    }

    @Override
    public Optional<CartEntity> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public CartDTO findByUserId(Long userId) {
        CartEntity cartEntity = cartRepository.findByUserEntity_Id(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUserEntity(userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found")));
                    return cartRepository.save(newCart);
                });
        return cartDTOConverter.toCartDTO(cartEntity);
    }

    @Override
    public Optional<CartEntity> findEntityByUserId(Long userId) {
        return cartRepository.findByUserEntity_Id(userId);
    }

    @Override
    public CartEntity save(CartEntity cartEntity) {
        return cartRepository.save(cartEntity);
    }

    public void updateCartTotalPrice(Long cartId){
        // Lấy thông tin CartEntity của người dùng
        CartEntity cartEntity = findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Tính tổng TotalPrice từ các CartItem
        BigDecimal totalPrice = cartEntity.getCartItemList().stream()
            .map(CartItemEntity::getTotalPrice) // Lấy TotalPrice của từng CartItem
            .reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng tất cả TotalPrice lại

        // Cập nhật TotalPrice của CartEntity
        cartEntity.setTotalPrice(totalPrice);

        System.out.println("TOTAL PRICE: " + totalPrice);

        // Lưu CartEntity
        save(cartEntity);
    }

    @Override
    public CartDTO findByUsername(String username) {
        CartEntity cartEntity = cartRepository.findByUserEntity_UserName(username)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUserEntity(userRepository.findByUserName(username));
                    return cartRepository.save(newCart);
                });;
        return cartDTOConverter.toCartDTO(cartEntity);
    }

    @Override
    @Transactional
    public void addCartItemAndUpdateCart(Long cartId, Long productId, Long sizeId, Integer quantity) {
        // Lấy thông tin ProductSize
        ProductSizeEntity productSizeEntity = productSizeService.findByProductIdAndSizeId(productId, sizeId)
                .orElseThrow(() -> new RuntimeException("Product size not found, productId=" + productId + "; sizeId=" + sizeId));

        // Kiểm tra tồn kho
        if (productSizeEntity.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock for productId=" + productId);
        }

        // Lấy CartEntity
        CartEntity cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Tìm CartItem hoặc tạo mới
        CartItemEntity cartItem = cartEntity.getCartItemList().stream()
                .filter(item -> item.getProductSizeEntity().getId().equals(productSizeEntity.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItemEntity newCartItem = new CartItemEntity();
                    newCartItem.setCartEntity(cartEntity);
                    newCartItem.setProductSizeEntity(productSizeEntity);
                    cartEntity.getCartItemList().add(newCartItem); // Thêm vào danh sách
                    return newCartItem;
                });

        // Cập nhật thông tin
        BigDecimal productPrice = new BigDecimal(productService.findProductById(productId).getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setPrice(productPrice);
        cartItem.setTotalPrice(productPrice.multiply(BigDecimal.valueOf(quantity)));

        // Cập nhật tổng giá trị của CartEntity
        BigDecimal totalPrice = cartEntity.getCartItemList().stream()
                .map(CartItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cartEntity.setTotalPrice(totalPrice);

        // Lưu CartEntity (Hibernate sẽ tự động cập nhật CartItem)
        cartRepository.save(cartEntity);

        // Cập nhật tổng giá trị giỏ hàng
        // updateCartTotalPrice(cartId);
    }

    @Override
    @Transactional
    public void deleteCartItemAndUpdateCart(Long cartItemId) {
        // Lấy CartItemEntity
        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart Item not found"));

        // Lấy CartEntity liên kết
        CartEntity cartEntity = cartItem.getCartEntity();

        // Xóa CartItem khỏi danh sách
        cartEntity.getCartItemList().remove(cartItem);

        // Cập nhật tổng giá trị của CartEntity
        BigDecimal totalPrice = cartEntity.getCartItemList().stream()
            .map(CartItemEntity::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        cartEntity.setTotalPrice(totalPrice);

        // Lưu CartEntity (Hibernate sẽ tự động xóa CartItem nhờ orphanRemoval)
        cartRepository.save(cartEntity);

        // Cập nhật tổng giá trị giỏ hàng
        // updateCartTotalPrice(cartEntity.getId());
    }
}
