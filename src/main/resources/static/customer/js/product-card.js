
document.addEventListener('DOMContentLoaded', () => {
    const productCards = document.querySelectorAll('.product-card');

    productCards.forEach(card => {
        const imageContainer = card.querySelector('.product-images');
        const images = imageContainer.querySelectorAll('img');
        const prevBtn = card.querySelector('.prev-btn');
        const nextBtn = card.querySelector('.next-btn');
        let currentIndex = 0;

        // Hide all images except the first
        images.forEach((img, index) => {
            img.style.display = index === 0 ? 'block' : 'none';
        });

        // Stop propagation for navigation buttons
        prevBtn.addEventListener('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
            if (currentIndex > 0) {
                images[currentIndex].style.display = 'none';
                currentIndex--;
                images[currentIndex].style.display = 'block';
            }
        });

        nextBtn.addEventListener('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
            if (currentIndex < images.length - 1) {
                images[currentIndex].style.display = 'none';
                currentIndex++;
                images[currentIndex].style.display = 'block';
            }
        });
    });
});
