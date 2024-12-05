document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById("searchInput");
    const suggestionBox = document.getElementById("suggestionBox");

    // Xử lý tìm kiếm và hiển thị gợi ý
    if (searchInput) {
        searchInput.addEventListener("input", function() {
            const query = this.value;

            if (query.length >= 2) {
                fetch(`/v1/api/search-suggestions?query=${query}`)
                    .then(response => response.json())
                    .then(data => {
                        suggestionBox.innerHTML = "";
                        if (data.length > 0) {
                            suggestionBox.style.display = "block";
                            data.forEach(item => {
                                const div = document.createElement("div");
                                div.className = "suggestion-item";
                                div.textContent = item.name;
                                div.onclick = () => {
                                    window.location.href = `/admin/product/detail?id=${item.id}`;
                                };
                                suggestionBox.appendChild(div);
                            });
                        } else {
                            suggestionBox.style.display = "none";
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching suggestions:', error);
                        suggestionBox.style.display = "none";
                    });
            } else {
                suggestionBox.style.display = "none";
            }
        });

        // Đóng suggestion box khi nhấp ra ngoài
        document.addEventListener('click', function(event) {
            if (!searchInput.contains(event.target) && !suggestionBox.contains(event.target)) {
                suggestionBox.style.display = "none";
            }
        });
    }
});

// Thêm đoạn này vào file JS của trang admin/products hoặc trong thẻ <script> của trang đó
document.addEventListener('DOMContentLoaded', function() {
    // Hàm tạo và hiển thị alert
    function showAlert(type, message) {
        const alertContainer = document.getElementById('alertContainer');
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
        alertDiv.innerHTML = `
            <strong>${message}</strong>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
        alertContainer.appendChild(alertDiv);

        // Tự động đóng alert sau 3 giây
        setTimeout(() => {
            alertDiv.classList.remove('show');
            setTimeout(() => alertDiv.remove(), 300);
        }, 3000);
    }

    // Kiểm tra xem có alert message trong sessionStorage không
    const alertMessage = sessionStorage.getItem('alertMessage');
    const alertType = sessionStorage.getItem('alertType');

    if (alertMessage) {
        showAlert(alertType || 'success', alertMessage);
        // Xóa alert message sau khi đã hiển thị
        sessionStorage.removeItem('alertMessage');
        sessionStorage.removeItem('alertType');
    }
});

function showPopup(id) {
    const modal = document.getElementById('popup');
    const selectedProduct = document.getElementById('deletedProduct');
    selectedProduct.value = id;
    modal.style.display = 'flex';
    setTimeout(() => {
        modal.querySelector('.modal-content').classList.add('show');
    }, 10);
}

function hidePopup() {
    const modal = document.getElementById('popup');
    modal.querySelector('.modal-content').classList.remove('show');
    setTimeout(() => {
        modal.style.display = 'none';
    }, 300);
}

function confirmDelete() {
    const selectedProduct = document.getElementById('deletedProduct');
    const productId = selectedProduct.value;

    fetch(`/v1/api/delete/${productId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Lỗi khi xóa sản phẩm');
        }
        return response.json();
    })
    .then(data => {
        sessionStorage.setItem('alertMessage', data.message);
        sessionStorage.setItem('alertType', 'success');
        location.reload();
    })
    .catch(error => {
        console.error('Lỗi khi xóa sản phẩm:', error);
        sessionStorage.setItem('alertMessage', 'Đã xảy ra lỗi khi xóa sản phẩm.');
        sessionStorage.setItem('alertType', 'danger');
        location.reload();
    });

    hidePopup();
}

