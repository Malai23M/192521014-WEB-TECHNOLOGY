/**
 * Experiment 1: DOM-Based Student Profile Manipulation
 * Demonstrates: getElementById(), querySelector(), textContent, style, classList, setAttribute()
 */

document.addEventListener('DOMContentLoaded', () => {
    // 1. Selecting elements using document.getElementById()
    const mainHeading = document.getElementById('main-heading');
    const headingInput = document.getElementById('heading-input');
    const btnUpdateHeading = document.getElementById('btn-update-heading');
    
    const textColorPicker = document.getElementById('text-color-picker');
    const bgColorPicker = document.getElementById('bg-color-picker');
    const avatarSelect = document.getElementById('avatar-select');
    
    const btnToggleHighlight = document.getElementById('btn-toggle-highlight');
    const btnToggleVisibility = document.getElementById('btn-toggle-visibility');
    
    const profileContainer = document.getElementById('profile-container');
    const studentAvatar = document.getElementById('student-avatar');
    const domLog = document.getElementById('dom-log');

    // 2. Selecting elements using document.querySelector()
    const studentNameElement = document.querySelector('#student-name');
    const studentDeptElement = document.querySelector('#student-dept');
    const allInfoValues = document.querySelectorAll('.info-value');

    // Helper function to log DOM actions
    function logAction(message) {
        domLog.textContent = `DOM Log [${new Date().toLocaleTimeString()}]: ${message}`;
    }

    // A. Modify Heading using textContent
    btnUpdateHeading.addEventListener('click', () => {
        const newHeading = headingInput.value.trim();
        if (newHeading !== '') {
            mainHeading.textContent = newHeading;
            logAction(`Heading changed to "${newHeading}" via textContent.`);
            headingInput.value = '';
        } else {
            alert('Please enter a valid heading text.');
        }
    });

    // B. Modify Text Colour using style.color
    textColorPicker.addEventListener('input', (e) => {
        const selectedColor = e.target.value;
        // Applying inline style to name and all info values
        studentNameElement.style.color = selectedColor;
        allInfoValues.forEach(el => {
            el.style.color = selectedColor;
        });
        logAction(`Text color modified to ${selectedColor} via style.color.`);
    });

    // C. Modify Background Colour using style.backgroundColor
    bgColorPicker.addEventListener('input', (e) => {
        const selectedBg = e.target.value;
        profileContainer.style.backgroundColor = selectedBg;
        logAction(`Background color changed to ${selectedBg} via style.backgroundColor.`);
    });

    // D. Modify Profile Image using setAttribute()
    avatarSelect.addEventListener('change', (e) => {
        const selectedUrl = e.target.value;
        studentAvatar.setAttribute('src', selectedUrl);
        studentAvatar.setAttribute('alt', e.target.options[e.target.selectedIndex].text);
        logAction(`Avatar image updated via setAttribute('src', ...).`);
    });

    // E. Toggle Highlight Theme using classList (classList.toggle)
    btnToggleHighlight.addEventListener('click', () => {
        const isHighlighted = profileContainer.classList.toggle('highlight-mode');
        if (isHighlighted) {
            btnToggleHighlight.textContent = 'Remove Highlight Theme';
            logAction(`Added 'highlight-mode' class via classList.toggle().`);
        } else {
            btnToggleHighlight.textContent = 'Toggle Highlight Theme';
            logAction(`Removed 'highlight-mode' class via classList.toggle().`);
        }
    });

    // F. Show / Hide Profile using style.display
    btnToggleVisibility.addEventListener('click', () => {
        if (profileContainer.style.display === 'none') {
            profileContainer.style.display = 'block';
            btnToggleVisibility.textContent = 'Hide Profile';
            btnToggleVisibility.className = 'btn btn-secondary';
            logAction(`Profile displayed via style.display = 'block'.`);
        } else {
            profileContainer.style.display = 'none';
            btnToggleVisibility.textContent = 'Show Profile';
            btnToggleVisibility.className = 'btn btn-primary';
            logAction(`Profile hidden via style.display = 'none'.`);
        }
    });
});
