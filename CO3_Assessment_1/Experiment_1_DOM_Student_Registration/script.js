/**
 * CO3 Assessment 1 - Experiment 1
 * DOM-Based Interactive Student Registration Preview
 * 
 * Concepts Demonstrated:
 * 1. Element Selection: getElementById(), querySelector()
 * 2. Event Handling: addEventListener()
 * 3. Dynamic DOM Creation: createElement(), appendChild()
 * 4. Text and Content Assignment: textContent
 * 5. Dynamic Styling & Visual Distinction: classList.add(), classList.remove()
 * 6. Dynamic DOM Removal: remove()
 */

// Wait for DOM content to be fully loaded
document.addEventListener('DOMContentLoaded', () => {

    // ==========================================
    // 1. DOM Element Selection using getElementById() and querySelector()
    // ==========================================
    const studentForm = document.getElementById('studentForm');
    const nameInput = document.getElementById('studentName');
    const regNoInput = document.getElementById('regNo');
    const deptSelect = document.getElementById('department');
    const yearSelect = document.getElementById('yearOfStudy');

    const previewBtn = document.getElementById('previewBtn');
    const resetBtn = document.getElementById('resetBtn');
    const profileContainer = document.getElementById('profileContainer');
    const emptyPlaceholder = document.getElementById('emptyPlaceholder');
    const previewStatus = document.getElementById('previewStatus');

    // Error message elements using querySelector
    const nameError = document.querySelector('#nameError');
    const regNoError = document.querySelector('#regNoError');
    const deptError = document.querySelector('#deptError');
    const yearError = document.querySelector('#yearError');

    /**
     * Clear validation errors on inputs
     */
    function clearErrors() {
        nameError.textContent = '';
        regNoError.textContent = '';
        deptError.textContent = '';
        yearError.textContent = '';

        nameInput.classList.remove('input-error');
        regNoInput.classList.remove('input-error');
        deptSelect.classList.remove('input-error');
        yearSelect.classList.remove('input-error');
    }

    /**
     * Validate all form inputs
     * @returns {boolean} true if valid, false otherwise
     */
    function validateForm() {
        clearErrors();
        let isValid = true;

        const nameVal = nameInput.value.trim();
        const regNoVal = regNoInput.value.trim();
        const deptVal = deptSelect.value;
        const yearVal = yearSelect.value;

        // Name Validation
        if (!nameVal) {
            nameError.textContent = 'Please enter the student full name.';
            nameInput.classList.add('input-error');
            isValid = false;
        } else if (nameVal.length < 2) {
            nameError.textContent = 'Name must be at least 2 characters.';
            nameInput.classList.add('input-error');
            isValid = false;
        }

        // Register Number Validation
        if (!regNoVal) {
            regNoError.textContent = 'Please enter the register number.';
            regNoInput.classList.add('input-error');
            isValid = false;
        } else if (!/^[A-Za-z0-9]+$/.test(regNoVal)) {
            regNoError.textContent = 'Register number should be alphanumeric.';
            regNoInput.classList.add('input-error');
            isValid = false;
        }

        // Department Selection Validation
        if (!deptVal) {
            deptError.textContent = 'Please select a department.';
            deptSelect.classList.add('input-error');
            isValid = false;
        }

        // Year of Study Validation
        if (!yearVal) {
            yearError.textContent = 'Please select year of study.';
            yearSelect.classList.add('input-error');
            isValid = false;
        }

        return isValid;
    }

    /**
     * Extracts student initials for the avatar
     * @param {string} fullName 
     * @returns {string} initials
     */
    function getInitials(fullName) {
        const parts = fullName.trim().split(' ').filter(Boolean);
        if (parts.length >= 2) {
            return (parts[0][0] + parts[1][0]).toUpperCase();
        } else if (parts.length === 1) {
            return parts[0].substring(0, 2).toUpperCase();
        }
        return 'ST';
    }

    // ==========================================
    // 2. Dynamic DOM Creation & Profile Generation
    // ==========================================
    function generateProfile() {
        if (!validateForm()) {
            return;
        }

        // Read values using DOM properties
        const name = nameInput.value.trim();
        const regNo = regNoInput.value.trim().toUpperCase();
        const department = deptSelect.value;
        const year = yearSelect.value;

        // Clear existing profile or placeholder
        profileContainer.textContent = '';

        // 1. Create main Profile Card container using createElement()
        const card = document.createElement('div');
        // Visual distinction using classList
        card.classList.add('student-profile-card');
        card.id = 'activeProfileCard';

        // 2. Create Ribbon Badge
        const ribbon = document.createElement('div');
        ribbon.classList.add('profile-badge-ribbon');
        ribbon.textContent = 'Registered Student Profile';
        card.appendChild(ribbon);

        // 3. Create Card Header (Avatar + Name & RegNo)
        const cardHeader = document.createElement('div');
        cardHeader.classList.add('profile-card-header');

        // Avatar Element
        const avatar = document.createElement('div');
        avatar.classList.add('student-avatar');
        avatar.textContent = getInitials(name);

        // Header Meta Info
        const headerMeta = document.createElement('div');
        headerMeta.classList.add('student-header-meta');

        const nameHeading = document.createElement('h3');
        nameHeading.textContent = name;

        const regSpan = document.createElement('span');
        regSpan.classList.add('reg-pill');
        regSpan.textContent = `Reg No: ${regNo}`;

        headerMeta.appendChild(nameHeading);
        headerMeta.appendChild(regSpan);

        cardHeader.appendChild(avatar);
        cardHeader.appendChild(headerMeta);
        card.appendChild(cardHeader);

        // 4. Create Details Grid
        const detailsGrid = document.createElement('div');
        detailsGrid.classList.add('profile-details-grid');

        // Helper to build individual detail items
        function createDetailItem(label, value) {
            const item = document.createElement('div');
            item.classList.add('detail-item');

            const labelElem = document.createElement('div');
            labelElem.classList.add('detail-label');
            labelElem.textContent = label;

            const valueElem = document.createElement('div');
            valueElem.classList.add('detail-value');
            valueElem.textContent = value;

            item.appendChild(labelElem);
            item.appendChild(valueElem);
            return item;
        }

        detailsGrid.appendChild(createDetailItem('Department', department));
        detailsGrid.appendChild(createDetailItem('Year of Study', year));
        detailsGrid.appendChild(createDetailItem('Generated At', new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })));
        detailsGrid.appendChild(createDetailItem('Status', 'Active & Verified'));

        card.appendChild(detailsGrid);

        // 5. Create Profile Actions (Remove Button)
        const actionsDiv = document.createElement('div');
        actionsDiv.classList.add('profile-actions');

        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.classList.add('btn-remove');
        removeBtn.id = 'removeProfileBtn';
        
        // Remove button icon & text
        removeBtn.innerHTML = `
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/><line x1="10" x2="10" y1="11" y2="17"/><line x1="14" x2="14" y1="11" y2="17"/></svg>
            Remove Profile
        `;

        // ==========================================
        // 3. Dynamic Element Removal using addEventListener() and remove()
        // ==========================================
        removeBtn.addEventListener('click', () => {
            removeProfile();
        });

        actionsDiv.appendChild(removeBtn);
        card.appendChild(actionsDiv);

        // Append generated card into container
        profileContainer.appendChild(card);

        // Update status indicator using classList
        previewStatus.textContent = 'Profile Displayed';
        previewStatus.classList.add('active');
    }

    /**
     * Restores empty placeholder state after profile removal
     */
    function removeProfile() {
        const activeCard = document.getElementById('activeProfileCard');
        if (activeCard) {
            // Remove the DOM element
            activeCard.remove();
        }

        // Restore placeholder UI
        profileContainer.textContent = '';
        const placeholder = document.createElement('div');
        placeholder.id = 'emptyPlaceholder';
        placeholder.classList.add('empty-state');
        placeholder.innerHTML = `
            <div class="icon-circle">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="8" r="5"/><path d="M20 21a8 8 0 0 0-16 0"/></svg>
            </div>
            <h3>No Student Profile Generated</h3>
            <p>Profile was removed. Fill out the registration form on the left and click <strong>Preview Profile</strong> to generate again.</p>
        `;
        profileContainer.appendChild(placeholder);

        // Update status indicator
        previewStatus.textContent = 'No profile generated';
        previewStatus.classList.remove('active');
    }

    // ==========================================
    // 4. Event Listeners Registration
    // ==========================================

    // Handle Preview Button Click
    previewBtn.addEventListener('click', (event) => {
        event.preventDefault(); // Prevent default submission
        generateProfile();
    });

    // Handle Form Reset
    resetBtn.addEventListener('click', () => {
        clearErrors();
        removeProfile();
    });

    // Optional: Real-time clearing of error states when typing/changing
    [nameInput, regNoInput].forEach(input => {
        input.addEventListener('input', () => {
            if (input.value.trim()) {
                input.classList.remove('input-error');
                const errElem = input.parentElement.querySelector('.error-msg');
                if (errElem) errElem.textContent = '';
            }
        });
    });

    [deptSelect, yearSelect].forEach(select => {
        select.addEventListener('change', () => {
            if (select.value) {
                select.classList.remove('input-error');
                const errElem = select.parentElement.querySelector('.error-msg');
                if (errElem) errElem.textContent = '';
            }
        });
    });

});
