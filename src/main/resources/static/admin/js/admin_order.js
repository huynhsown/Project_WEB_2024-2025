document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById("searchInput");
    const suggestionBox = document.getElementById("suggestionBox");

    // Xử lý tìm kiếm và hiển thị gợi ý
    if (searchInput) {
        searchInput.addEventListener("input", function() {
            const query = this.value;

            if (query.length >= 1) {
                fetch(`/v1/api/search-suggestions-order?query=${query}`)
                    .then(response => response.json())
                    .then(data => {
                        suggestionBox.innerHTML = "";
                        if (data.length > 0) {
                            suggestionBox.style.display = "block";
                            data.forEach(item => {
                                console.log(item);
                                const div = document.createElement("div");
                                div.className = "suggestion-item";
                                div.textContent=item[1];
                                div.onclick = () => {
                                    window.location.href = `/admin/order/detail?id=${item[0]}`;
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
//Hàm sử lí sự kiện save order
function handleFormSubmitSave() {
    // Chỉ lấy id và status
    const orderData = {
        id: document.getElementById('id').value,
        status: document.getElementById('status').value
    };

    // Tạo FormData để gửi lên server
    const formData = new FormData();
    formData.append('order', JSON.stringify(orderData));

    // Disable nút submit
    const submitButtonSave = document.getElementById('submitButtonSave');
    submitButtonSave.disabled = true;

    // Gửi request đến API
    fetch('/v1/api/order/save', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            // Lưu thông báo vào sessionStorage
            sessionStorage.setItem('alertMessage', 'Cập nhật đơn hàng thành công');
            sessionStorage.setItem('alertType', 'success');

            // Chuyển hướng về trang danh sách đơn hàng
            window.location.href = '/admin/orders';
        })
        .catch(error => {
            console.error("Lỗi: ", error);

            // Lưu thông báo lỗi vào sessionStorage
            sessionStorage.setItem('alertMessage', 'Đã có lỗi xảy ra khi cập nhật đơn hàng');
            sessionStorage.setItem('alertType', 'danger');

            // Chuyển hướng về trang danh sách đơn hàng
            window.location.href = '/admin/orders';
        });
}

// Thêm sự kiện click cho nút lưu đơn hàng
document.getElementById('submitButtonSave').addEventListener('click', handleFormSubmitSave);