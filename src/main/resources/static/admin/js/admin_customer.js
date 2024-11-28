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
                                    window.location.href = `/admin/product/id=${item.id}`;
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