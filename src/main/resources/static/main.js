// POS System - Shared Utilities

document.addEventListener('DOMContentLoaded', function () {
    // Bootstrap tooltips
    document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(el => {
        new bootstrap.Tooltip(el);
    });
});

// Format currency (VND)
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
}

// Format datetime
function formatDate(date) {
    return new Intl.DateTimeFormat('vi-VN', {
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit'
    }).format(new Date(date));
}

// Show flash notification
function showNotification(message, type = 'info') {
    const div = document.createElement('div');
    div.className = `alert alert-${type} alert-dismissible fade show`;
    div.role = 'alert';
    div.innerHTML = `${message}<button type="button" class="btn-close" data-bs-dismiss="alert"></button>`;
    const target = document.querySelector('main') || document.body;
    target.insertBefore(div, target.firstChild);
}

// Print current page
function printReceipt() {
    window.print();
}

// Local storage helpers
const Storage = {
    set: (key, value) => localStorage.setItem(key, JSON.stringify(value)),
    get: (key) => { const v = localStorage.getItem(key); return v ? JSON.parse(v) : null; },
    remove: (key) => localStorage.removeItem(key)
};

window.POS = { formatCurrency, formatDate, showNotification, printReceipt, Storage };
