document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById("searchInput");
    const suggestionBox = document.getElementById("suggestionBox");

    // Xử lý tìm kiếm và hiển thị gợi ý
    if (searchInput) {
        searchInput.addEventListener("input", function() {
            const query = this.value;

            if (query.length >= 2) {
                fetch(`/v1/api/search-suggestions-customer?query=${query}`)
                    .then(response => response.json())
                    .then(data => {
                        suggestionBox.innerHTML = "";
                        if (data.length > 0) {
                            suggestionBox.style.display = "block";
                            data.forEach(item => {
                                console.log(item);
                                const div = document.createElement("div");
                                div.className = "suggestion-item";
                                const phoneRegex = /^\d+$/;  // Biểu thức chính quy kiểm tra chuỗi chỉ chứa số
                                if (phoneRegex.test(query)) {  // Sử dụng test() thay vì matches()
                                    div.textContent = `${item.telephone}`;
                                } else {
                                    div.textContent = `${item.firstName} ${item.lastName}`;
                                }
                                div.onclick = () => {
                                    window.location.href = `/admin/customer?id=${item.id}`;
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
function showPopup(id) {
    const modal = document.getElementById('popup');
    const selectedCustomer = document.getElementById('deletedCustomer');
    selectedCustomer.value = id;
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
    const selectedCustomer = document.getElementById('deletedCustomer');
    const customerId = selectedCustomer.value;

    fetch(`/v1/api/customer/delete/${customerId}`, {
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

function handleFormSubmitSave() {
    const formData = new FormData();

    // Thêm JSON vào formData với key là 'customer' để phù hợp với API Java
    formData.append('customer', JSON.stringify({
        id: document.getElementById('customerId').value,
        roleId: document.getElementById('role_id').value  // Chỉnh lại key là 'roleId' cho phù hợp
    }));
    console.log(formData)

    // Disable submit button
    const submitButtonSave = document.getElementById('submitButtonSave');
    submitButtonSave.disabled = true;

    fetch(`/v1/api/user/save`, {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            // Lưu thông tin alert vào sessionStorage
            sessionStorage.setItem('alertMessage', data.message);
            sessionStorage.setItem('alertType', 'success');
            console.log(data.message);

            // Redirect về trang customers
            //window.location.href = '/admin/customers';
        })
        .catch(error => {
            console.error("Lỗi: ", error);
            // Lưu thông tin alert lỗi vào sessionStorage
            sessionStorage.setItem('alertMessage', 'Đã có lỗi xảy ra khi lưu khách hàng.');
            sessionStorage.setItem('alertType', 'danger');

            // Redirect về trang customers
            //window.location.href = '/admin/customers';
        });
}

// Thêm sự kiện click cho nút lưu khách hàng
document.getElementById('submitButtonSave').addEventListener('click', handleFormSubmitSave);
