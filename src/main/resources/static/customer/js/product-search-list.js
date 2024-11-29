document.addEventListener("DOMContentLoaded", function () {
const supplierDropdown = document.getElementById("supplierDropdown");
const selectedSuppliersContainer = document.getElementById("selectedSuppliers");
const supplierIdsInput = document.getElementById("supplierIds");
// Clean and initialize selectedSupplierIds
let selectedSupplierIds = supplierIdsInput.value
    .split(',')
    .filter(id => id && id.trim() !== "" && id !== "null");

// Array to keep track of selected supplier IDs
// let selectedSupplierIds = supplierIdsInput.value.split(',');

// Handle supplier selection
supplierDropdown.addEventListener("change", function () {
    // Get the information of the selected supplier in the dropdown list
    const selectedOption = supplierDropdown.options[supplierDropdown.selectedIndex];
    const supplierId = selectedOption.value;
    const supplierName = selectedOption.text;

    // Check if supplier is already selected
    if (!selectedSupplierIds.includes(supplierId)) {
        // Add supplier ID to the array
        selectedSupplierIds.push(supplierId);

        // Update the hidden input field
        supplierIdsInput.value = selectedSupplierIds.join(",");

        // Create a new tag for the selected supplier
        const tag = document.createElement("div");
        tag.className = "tag";
        tag.setAttribute("data-id", supplierId);
        tag.innerHTML = `
            ${supplierName}
            <span class="remove-tag" data-id="${supplierId}">&times;</span>
        `;

        // Add the tag to the container
        selectedSuppliersContainer.appendChild(tag);
    }

    // Reset the dropdown to its placeholder
    supplierDropdown.value = "";
});

// Handle tag removal
selectedSuppliersContainer.addEventListener("click", function (e) {
    if (e.target.classList.contains("remove-tag")) {
        const supplierId = e.target.getAttribute("data-id");

        // Remove supplier ID from the array
        selectedSupplierIds = selectedSupplierIds.filter((id) => id !== supplierId);

        // Update the hidden input field
        supplierIdsInput.value = selectedSupplierIds.join(",");

        // Remove the tag element
        const tag = document.querySelector(`.tag[data-id="${supplierId}"]`);
        if (tag) {
            selectedSuppliersContainer.removeChild(tag);
        }
    }
});

// Handle showing selected suppliers when modal is opened
$('#myModal').on('show.bs.modal', function () {
    // Get selectedSuppliers from hidden input
    let selectedSupplierIds = supplierIdsInput.value.split(',');

    // Clear the existing supplier tags in the modal
    selectedSuppliersContainer.innerHTML = '';

    // Add each selected supplier as a tag
    selectedSupplierIds.forEach(function (supplierId) {
        // Skip if supplierId is empty or invalid
        if (!supplierId) return;

        const supplierName = $('#supplierDropdown option[value="' + supplierId + '"]').text();

        // Ensure supplierName is valid (in case of invalid IDs)
        if (supplierName) {
            const tag = document.createElement("div");
            tag.className = "tag";
            tag.setAttribute("data-id", supplierId);
            tag.innerHTML = `
                ${supplierName}
                <span class="remove-tag" data-id="${supplierId}">&times;</span>
            `;
            selectedSuppliersContainer.appendChild(tag);
        }
    });
});

});

document.addEventListener("DOMContentLoaded", function () {

// Colors
const colorDropdown = document.getElementById("colorDropdown");
const selectedColorsContainer = document.getElementById("selectedColors");
const colorIdsInput = document.getElementById("colorIds");
let selectedColorIds = [];

colorDropdown.addEventListener("change", function () {
    const selectedOption = colorDropdown.options[colorDropdown.selectedIndex];
    const colorId = selectedOption.value;
    const colorName = selectedOption.text;

    if (!selectedColorIds.includes(colorId)) {
        selectedColorIds.push(colorId);
        colorIdsInput.value = selectedColorIds.join(",");

        const tag = document.createElement("div");
        tag.className = "tag";
        tag.setAttribute("data-id", colorId);
        tag.innerHTML = `${colorName} <span class="remove-tag" data-id="${colorId}">&times;</span>`;

        selectedColorsContainer.appendChild(tag);
    }

    colorDropdown.value = "";
});

selectedColorsContainer.addEventListener("click", function (e) {
    if (e.target.classList.contains("remove-tag")) {
        const colorId = e.target.getAttribute("data-id");
        selectedColorIds = selectedColorIds.filter((id) => id !== colorId);
        colorIdsInput.value = selectedColorIds.join(",");

        const tag = document.querySelector(`.tag[data-id="${colorId}"]`);
        if (tag) {
            selectedColorsContainer.removeChild(tag);
        }
    }
});

// Sizes
const sizeDropdown = document.getElementById("sizeDropdown");
const selectedSizesContainer = document.getElementById("selectedSizes");
const sizeIdsInput = document.getElementById("sizeIds");
let selectedSizeIds = [];

sizeDropdown.addEventListener("change", function () {
    const selectedOption = sizeDropdown.options[sizeDropdown.selectedIndex];
    const sizeId = selectedOption.value;
    const sizeName = selectedOption.text;

    if (!selectedSizeIds.includes(sizeId)) {
        selectedSizeIds.push(sizeId);
        sizeIdsInput.value = selectedSizeIds.join(",");

        const tag = document.createElement("div");
        tag.className = "tag";
        tag.setAttribute("data-id", sizeId);
        tag.innerHTML = `${sizeName} <span class="remove-tag" data-id="${sizeId}">&times;</span>`;

        selectedSizesContainer.appendChild(tag);
    }

    sizeDropdown.value = "";
});

selectedSizesContainer.addEventListener("click", function (e) {
    if (e.target.classList.contains("remove-tag")) {
        const sizeId = e.target.getAttribute("data-id");
        selectedSizeIds = selectedSizeIds.filter((id) => id !== sizeId);
        sizeIdsInput.value = selectedSizeIds.join(",");

        const tag = document.querySelector(`.tag[data-id="${sizeId}"]`);
        if (tag) {
            selectedSizesContainer.removeChild(tag);
        }
    }
});
});