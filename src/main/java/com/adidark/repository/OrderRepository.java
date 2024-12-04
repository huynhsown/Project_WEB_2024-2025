package com.adidark.repository;

import com.adidark.entity.OrderEntity;
import com.adidark.enums.StatusType;
import com.adidark.model.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findByIdContainingIgnoreCase(String query, Pageable pageable);

    @Query("SELECT o.id AS orderId, u.telephone AS userPhone " +
            "FROM OrderEntity o " +
            "JOIN o.userEntity u " +
            "WHERE u.telephone LIKE CONCAT('%', :phone, '%')")
    List<Object[]> findOrdersByUserPhone(@Param("phone") String phone);


    @Query("SELECT DISTINCT e.status FROM OrderEntity e")
    List<StatusType> findAllOrderStatuses();

}
