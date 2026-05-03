// POS System - Main JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new bootstrap.Tooltip(tooltipTriggerEl)
    })

    // Initialize popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
      return new bootstrap.Popover(popoverTriggerEl)
    })
});

// Format currency
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}

// Format date
function formatDate(date) {
    return new Intl.DateTimeFormat('vi-VN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    }).format(new Date(date));
}

// Show notification
function showNotification(message, type = 'info') {
    const alertClass = `alert-${type}`;
    const alertHtml = `
        <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;

    const container = document.querySelector('main') || document.body;
    const alertDiv = document.createElement('div');
    alertDiv.innerHTML = alertHtml;
    container.insertBefore(alertDiv.firstElementChild, container.firstChild);
}

// Confirm delete action
function confirmDelete(message = 'Bạn chắc chắn muốn xóa?') {
    return confirm(message);
}

// Calculate order total
function calculateOrderTotal(items, vaT = 0, discount = 0) {
    let subtotal = 0;
    items.forEach(item => {
        subtotal += item.price * item.quantity;
    });

    const afterDiscount = subtotal - discount;
    const vatAmount = afterDiscount * (vaT / 100);
    const total = afterDiscount + vatAmount;

    return {
        subtotal: subtotal,
        discount: discount,
        vat: vatAmount,
        total: total
    };
}

// Filter products by category
function filterProducts(categoryId) {
    const products = document.querySelectorAll('[data-category]');

    if (categoryId === 'all') {
        products.forEach(p => p.style.display = 'block');
    } else {
        products.forEach(p => {
            if (p.getAttribute('data-category') == categoryId) {
                p.style.display = 'block';
            } else {
                p.style.display = 'none';
            }
        });
    }
}

// Add to cart (for future AJAX implementation)
function addToCart(productId, productName, quantity, size, note) {
    console.log(`Added to cart: ${productName} (${quantity}) - Size: ${size}, Note: ${note}`);
    // Implementation would go here
}

// Remove from cart
function removeFromCart(itemId) {
    console.log(`Removed item ${itemId} from cart`);
    // Implementation would go here
}

// Update item quantity
function updateItemQuantity(itemId, quantity) {
    console.log(`Updated item ${itemId} quantity to ${quantity}`);
    // Implementation would go here
}

// Search products
function searchProducts(query) {
    const products = document.querySelectorAll('[data-product]');
    const lowerQuery = query.toLowerCase();

    products.forEach(p => {
        const name = p.getAttribute('data-product').toLowerCase();
        if (name.includes(lowerQuery)) {
            p.style.display = 'block';
        } else {
            p.style.display = 'none';
        }
    });
}

// Print receipt
function printReceipt() {
    window.print();
}

// Export to PDF (requires additional library like jspdf)
function exportToPDF(filename = 'receipt.pdf') {
    showNotification('PDF export feature coming soon', 'info');
}

// Validate email
function isValidEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Validate phone number (Vietnamese format)
function isValidPhone(phone) {
    const re = /^(0|\+84)[1-9]\d{8,9}$/;
    return re.test(phone);
}

// Get today's date in YYYY-MM-DD format
function getTodayDate() {
    const today = new Date();
    return today.toISOString().split('T')[0];
}

// Debounce function for search
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Table status badge color
function getTableStatusColor(status) {
    const colors = {
        'EMPTY': '#28a745',      // Green
        'OCCUPIED': '#dc3545',   // Red
        'RESERVED': '#ffc107'    // Yellow
    };
    return colors[status] || '#6c757d';
}

// Order status badge color
function getOrderStatusColor(status) {
    const colors = {
        'PENDING': '#ffc107',    // Yellow
        'PAID': '#28a745',       // Green
        'CANCELLED': '#dc3545'   // Red
    };
    return colors[status] || '#6c757d';
}

// Format large numbers with commas
function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// Real-time clock
function updateClock() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    const clockElements = document.querySelectorAll('[data-clock]');
    clockElements.forEach(el => {
        el.textContent = `${hours}:${minutes}:${seconds}`;
    });
}

// Start clock update every second
if (document.querySelector('[data-clock]')) {
    setInterval(updateClock, 1000);
    updateClock(); // Initial call
}

// AJAX helper for API calls
function apiCall(url, method = 'GET', data = null, callback = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        }
    };

    if (data) {
        options.body = JSON.stringify(data);
    }

    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            if (callback) {
                callback(result);
            }
        })
        .catch(error => {
            console.error('API Error:', error);
            showNotification('An error occurred. Please try again.', 'danger');
        });
}

// Local storage helpers
const Storage = {
    set: function(key, value) {
        localStorage.setItem(key, JSON.stringify(value));
    },
    get: function(key) {
        const item = localStorage.getItem(key);
        return item ? JSON.parse(item) : null;
    },
    remove: function(key) {
        localStorage.removeItem(key);
    },
    clear: function() {
        localStorage.clear();
    }
};

// Session management
const Session = {
    setOrderData: function(orderId, data) {
        Storage.set(`order_${orderId}`, data);
    },
    getOrderData: function(orderId) {
        return Storage.get(`order_${orderId}`);
    },
    clearOrderData: function(orderId) {
        Storage.remove(`order_${orderId}`);
    }
};

// Analytics helper (for future integration)
function trackEvent(category, action, label = null, value = null) {
    console.log(`Event: ${category} > ${action}` + (label ? ` (${label})` : '') + (value ? ` = ${value}` : ''));
    // Google Analytics or other tracking service would go here
}

// Print styles handler
function setupPrintStyles() {
    if (window.location.pathname.includes('/receipt')) {
        // Auto-hide unnecessary elements on receipt page
        document.querySelectorAll('.navbar, .sidebar, .btn').forEach(el => {
            el.setAttribute('data-print-hide', 'true');
        });
    }
}

setupPrintStyles();

// Export utilities for use in other scripts
window.POS = {
    formatCurrency,
    formatDate,
    showNotification,
    calculateOrderTotal,
    filterProducts,
    printReceipt,
    exportToPDF,
    Storage,
    Session,
    trackEvent
};

console.log('POS System utilities loaded successfully');

