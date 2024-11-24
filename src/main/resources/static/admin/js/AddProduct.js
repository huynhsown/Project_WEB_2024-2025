    let colors = [];
    let sizes = [];

    // Hàm khởi tạo Quill Editor
    function initializeQuillEditor() {
        const quill = new Quill('#productDescription', {
            theme: 'snow',
            modules: {
                toolbar: [
                    [{
                        'font': []
                    }],
                    [{
                        'list': 'ordered'
                    }, {
                        'list': 'bullet'
                    }],
                    [{
                        'align': []
                    }],
                    ['bold', 'italic', 'underline']
                ]
            },
            placeholder: 'Nhập mô tả sản phẩm...',
        });

        quill.on('text-change', () => {
            document.getElementById('productDescriptionInput').value = quill.root.innerHTML;
        });
    }

    // Hàm khởi tạo quản lý màu sắc
    function initializeColorManagement() {
        const colorInput = document.getElementById('colorInput');
        colorInput.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                const color = this.value.trim();
                if (color !== '') {
                    addColor(color);
                    this.value = '';
                }
            }
        });
    }

    // Hàm thêm màu sắc
    function addColor(color) {
        const formattedColor = color.charAt(0).toUpperCase() + color.slice(1).toLowerCase();
        if (colors.includes(formattedColor)) {
            alert('Màu này đã được thêm!');
            return;
        }
        colors.push(formattedColor);
        createColorTag(formattedColor);
        updateColorInput();
    }

    // Hàm tạo thẻ màu sắc
    function createColorTag(color) {
        const tag = document.createElement('div');
        tag.className = 'tag';
        tag.innerHTML = `
        ${color}
        <button type="button" class="remove-tag" onclick="removeColor(this, '${color}')">&times;</button>
    `;
        const container = document.getElementById('colorTagContainer');
        container.insertBefore(tag, container.querySelector('input'));
    }

    // Hàm xóa màu sắc
    function removeColor(button, color) {
        colors = colors.filter(c => c !== color);
        button.parentElement.remove();
        updateColorInput();
    }

    // Cập nhật giá trị input màu sắc
    function updateColorInput() {
        document.getElementById('productColors').value = colors.join(',');
    }

    // Hàm khởi tạo quản lý kích thước
    function initializeSizeManagement() {
        const sizeInput = document.getElementById('sizeInput');
        const quantityInput = document.getElementById('quantityInput');

        function handleSizeInputs(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                const size = sizeInput.value.trim();
                const quantity = quantityInput.value.trim();

                if (size && quantity) {
                    addSizeAndQuantity(size, quantity);
                    sizeInput.value = '';
                    quantityInput.value = '';
                    sizeInput.focus();
                }
            }
        }

        sizeInput.addEventListener('keydown', handleSizeInputs);
        quantityInput.addEventListener('keydown', handleSizeInputs);
    }

    // Hàm thêm kích thước và số lượng
    function addSizeAndQuantity(size, quantity) {
        if (sizes.some(s => s.size === size)) {
            alert('Kích thước này đã được thêm!');
            return;
        }
        sizes.push({
            size,
            quantity
        });
        createSizeTag(size, quantity);
        updateSizeInput();
    }

    // Hàm tạo thẻ kích thước và số lượng
    function createSizeTag(size, quantity) {
        const tag = document.createElement('div');
        tag.className = 'tag';
        tag.innerHTML = `
        Kích thước ${size}: ${quantity}
        <button type="button" class="remove-tag" onclick="removeSizeAndQuantity(this, '${size}')">&times;</button>
    `;
        const container = document.getElementById('sizeTagContainer');
        container.insertBefore(tag, container.querySelector('.size-inputs'));
    }

    // Hàm xóa kích thước và số lượng
    function removeSizeAndQuantity(button, size) {
        sizes = sizes.filter(s => s.size !== size);
        button.parentElement.remove();
        updateSizeInput();
    }

    // Cập nhật giá trị input kích thước
    function updateSizeInput() {
        document.getElementById('productSizes').value = sizes.map(s => `${s.size}:${s.quantity}`).join(',');
    }

    function initializeFileUpload() {
        const dropArea = document.getElementById('dropArea');
        const fileInput = document.getElementById('fileInput');

        // Prevent default drag behaviors
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            dropArea.addEventListener(eventName, preventDefaults, false);
            document.body.addEventListener(eventName, preventDefaults, false);
        });

        // Highlight drop zone when dragging
        ['dragenter', 'dragover'].forEach(eventName => {
            dropArea.addEventListener(eventName, () => dropArea.classList.add('highlight'));
        });

        ['dragleave', 'drop'].forEach(eventName => {
            dropArea.addEventListener(eventName, () => dropArea.classList.remove('highlight'));
        });

        // Handle dropped files
        dropArea.addEventListener('drop', (e) => {
            const files = e.dataTransfer.files;
            handleFiles(files);
        });

        fileInput.addEventListener('change', (e) => {
            handleFiles(e.target.files);
        });
    }

    function preventDefaults(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    function handleFiles(files) {
        Array.from(files).forEach(file => {
            if (file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = (e) => createImagePreview(e.target.result);
                reader.readAsDataURL(file);
            }
        });
    }

    function createImagePreview(imageSrc) {
        const colDiv = document.createElement('div');
        colDiv.className = 'col-md-3 preview-image-container';

        const img = document.createElement('img');
        img.src = imageSrc;
        img.className = 'preview-image img-fluid';

        const deleteBtn = document.createElement('button');
        deleteBtn.type = 'button';
        deleteBtn.className = 'delete-image-btn';
        deleteBtn.innerHTML = '<i class="fas fa-times"></i>';
        deleteBtn.onclick = () => colDiv.remove();

        colDiv.appendChild(img);
        colDiv.appendChild(deleteBtn);
        document.getElementById('previewContainer').appendChild(colDiv);
    }

    function handleFormSubmit() {
    const form = document.getElementById('productForm');
    const formData = new FormData();

    formData.append('product', JSON.stringify({
        name: document.getElementById('productName').value,
        price: document.getElementById('productPrice').value,
        description: document.getElementById('productDescriptionInput').value,
        categoryId: document.getElementById('category').value,
        colors: colors,
        sizes: sizes,
        supplierId: document.getElementById('supplier').value
    }));

    const files = document.getElementById('fileInput').files;
    for (let i = 0; i < files.length; i++) {
        formData.append('images', files[i]);
    }

    // Disable submit button
    const submitButton = document.getElementById('submitButton');
    submitButton.disabled = true;

    fetch('/v1/api/save', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        // Lưu thông tin alert vào sessionStorage
        sessionStorage.setItem('alertMessage', data.message);
        sessionStorage.setItem('alertType', 'success');

        // Redirect về trang products
        window.location.href = '/admin/products';
    })
    .catch(error => {
        console.error("Lỗi: ", error);
        // Lưu thông tin alert lỗi vào sessionStorage
        sessionStorage.setItem('alertMessage', 'Đã có lỗi xảy ra khi lưu sản phẩm.');
        sessionStorage.setItem('alertType', 'danger');

        // Redirect về trang products
        window.location.href = '/admin/products';
    });
}


    // Khởi tạo trực tiếp các hàm
    initializeFileUpload();
    initializeQuillEditor();
    initializeColorManagement();
    initializeSizeManagement();

    // Thêm sự kiện click cho nút lưu sản phẩm
    document.getElementById('submitButton').addEventListener('click', handleFormSubmit);