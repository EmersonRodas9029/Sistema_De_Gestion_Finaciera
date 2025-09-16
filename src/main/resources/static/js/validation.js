function showValidationError(input, message) {
    const el = document.createElement('div');
    el.className = 'invalid-feedback';
    el.textContent = message;
    input.classList.add('is-invalid');
    if (!input.nextElementSibling || !input.nextElementSibling.classList.contains('invalid-feedback')) {
        input.parentNode.appendChild(el);
    }
}

function clearValidation(input) {
    input.classList.remove('is-invalid');
    if (input.nextElementSibling && input.nextElementSibling.classList.contains('invalid-feedback')) {
        input.nextElementSibling.remove();
    }
}
