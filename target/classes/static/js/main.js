// Main JavaScript file for Library Management System

document.addEventListener('DOMContentLoaded', function() {
    console.log('Library Management System initialized');
    
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Initialize popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
    
    // Handle alert dismissal
    document.querySelectorAll('.alert .btn-close').forEach(function(button) {
        button.addEventListener('click', function() {
            this.closest('.alert').classList.add('fade');
            setTimeout(() => {
                this.closest('.alert').style.display = 'none';
            }, 150);
        });
    });
    
    // Auto-hide alerts after 5 seconds
    setTimeout(function() {
        document.querySelectorAll('.alert:not(.alert-permanent)').forEach(function(alert) {
            if (alert) {
                alert.classList.add('fade');
                setTimeout(() => {
                    alert.style.display = 'none';
                }, 150);
            }
        });
    }, 5000);
    
    // Sample form validation
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
    
    // Confirm delete actions
    document.querySelectorAll('.confirm-delete').forEach(button => {
        button.addEventListener('click', event => {
            if (!confirm('Are you sure you want to delete this item? This action cannot be undone.')) {
                event.preventDefault();
            }
        });
    });
    
    // Search box enhancements
    const searchInput = document.querySelector('.search-input');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            if (this.value.length > 2) {
                // In a real application, this would trigger an AJAX search
                console.log('Searching for:', this.value);
            }
        });
    }
    
    // Sample borrowing functionality
    document.querySelectorAll('.borrow-button').forEach(button => {
        button.addEventListener('click', event => {
            event.preventDefault();
            const bookId = button.getAttribute('data-book-id');
            
            // In a real application, this would send an AJAX request to borrow the book
            console.log('Borrowing book ID:', bookId);
            
            // Display success message (for demo purposes)
            alert('Book borrowed successfully!');
            
            // Update button status (for demo purposes)
            button.textContent = 'Borrowed';
            button.classList.remove('btn-success');
            button.classList.add('btn-secondary');
            button.disabled = true;
        });
    });
    
    // Sample return functionality
    document.querySelectorAll('.return-button').forEach(button => {
        button.addEventListener('click', event => {
            event.preventDefault();
            const borrowingId = button.getAttribute('data-borrowing-id');
            
            // In a real application, this would send an AJAX request to return the book
            console.log('Returning borrowing ID:', borrowingId);
            
            // Display success message (for demo purposes)
            alert('Book returned successfully!');
            
            // Update button status (for demo purposes)
            button.textContent = 'Returned';
            button.classList.remove('btn-primary');
            button.classList.add('btn-success');
            button.disabled = true;
        });
    });
});

// Sample data for charts (can be replaced with actual data in a real application)
function createBooksByCategoryChart(ctx) {
    if (!ctx) return;
    
    return new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Fiction', 'Non-Fiction', 'Programming', 'Science', 'History', 'Biography'],
            datasets: [{
                data: [65, 40, 25, 15, 20, 10],
                backgroundColor: [
                    '#ffc107', '#17a2b8', '#6610f2', '#20c997', '#fd7e14', '#6c757d'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'right',
                },
                title: {
                    display: true,
                    text: 'Books by Category'
                }
            }
        }
    });
}

function createBorrowingsChart(ctx) {
    if (!ctx) return;
    
    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
            datasets: [{
                label: 'Borrowings',
                data: [18, 25, 30, 22, 28, 35],
                backgroundColor: '#0d6efd'
            }, {
                label: 'Returns',
                data: [15, 20, 25, 18, 24, 30],
                backgroundColor: '#198754'
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Borrowings vs Returns'
                }
            }
        }
    });
}

