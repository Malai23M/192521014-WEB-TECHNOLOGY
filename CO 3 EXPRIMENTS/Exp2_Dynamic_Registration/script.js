/**
 * Experiment 2: Dynamic Student Registration List Using DOM
 * Demonstrates: createElement(), appendChild(), remove(), parentElement, children
 */

document.addEventListener('DOMContentLoaded', () => {
    // Form and Table elements
    const studentForm = document.getElementById('student-form');
    const nameInput = document.getElementById('student-name');
    const regInput = document.getElementById('reg-number');
    const deptSelect = document.getElementById('department');
    
    const tbody = document.getElementById('student-list-body');
    const studentCountBadge = document.getElementById('student-count');
    const btnClearAll = document.getElementById('btn-clear-all');
    const statusBar = document.getElementById('status-bar');

    // In-memory counter for total students currently in DOM
    function updateUIState() {
        // Utilizing tbody.children to count actual student rows
        const currentRows = Array.from(tbody.children).filter(row => !row.classList.contains('empty-state'));
        const count = currentRows.length;
        studentCountBadge.textContent = count;
        btnClearAll.disabled = count === 0;

        if (count === 0) {
            // If empty, insert empty-state placeholder row
            tbody.innerHTML = `
                <tr id="empty-row" class="empty-state">
                    <td colspan="5">No students registered yet. Add a record using the form.</td>
                </tr>
            `;
        } else {
            // Re-index serial numbers using tbody.children
            currentRows.forEach((row, index) => {
                // Access the first child td of each row
                row.children[0].textContent = index + 1;
            });
        }
    }

    // Function to add a student record to the DOM
    studentForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const name = nameInput.value.trim();
        const regno = regInput.value.trim();
        const dept = deptSelect.value;

        if (!name || !regno || !dept) {
            alert('Please fill out all required fields.');
            return;
        }

        // Check if empty state row is currently displayed
        const emptyRow = document.getElementById('empty-row');
        if (emptyRow) {
            // Remove empty placeholder row using remove()
            emptyRow.remove();
        }

        // 1. Create <tr> using document.createElement()
        const tr = document.createElement('tr');

        // 2. Create <td> for Serial Number
        const tdIndex = document.createElement('td');
        tdIndex.textContent = tbody.children.length + 1;

        // 3. Create <td> for Student Name
        const tdName = document.createElement('td');
        tdName.textContent = name;
        tdName.style.fontWeight = '500';

        // 4. Create <td> for Register Number
        const tdReg = document.createElement('td');
        tdReg.textContent = regno;

        // 5. Create <td> for Department
        const tdDept = document.createElement('td');
        const deptBadge = document.createElement('span');
        deptBadge.className = 'dept-badge';
        deptBadge.textContent = dept;
        tdDept.appendChild(deptBadge);

        // 6. Create <td> for Actions (Remove Button)
        const tdAction = document.createElement('td');
        const btnRemove = document.createElement('button');
        btnRemove.type = 'button';
        btnRemove.className = 'btn btn-remove';
        btnRemove.textContent = 'Remove';

        // Event listener for dynamic removal using parentElement & remove()
        btnRemove.addEventListener('click', function() {
            // Method 1: Target the parent row using parentElement traversal
            const targetRow = this.parentElement.parentElement;
            const removedName = targetRow.children[1].textContent;
            
            // Remove row from DOM using remove()
            targetRow.remove();

            statusBar.textContent = `Removed record for: ${removedName} (DOM element destroyed via parentElement.parentElement.remove())`;
            updateUIState();
        });

        tdAction.appendChild(btnRemove);

        // 7. Append all <td> elements to <tr> using appendChild()
        tr.appendChild(tdIndex);
        tr.appendChild(tdName);
        tr.appendChild(tdReg);
        tr.appendChild(tdDept);
        tr.appendChild(tdAction);

        // 8. Append <tr> to <tbody> using appendChild()
        tbody.appendChild(tr);

        // Reset form inputs
        studentForm.reset();
        nameInput.focus();

        statusBar.textContent = `Successfully added student: ${name} (${regno}) via createElement() and appendChild().`;
        updateUIState();
    });

    // Clear All records
    btnClearAll.addEventListener('click', () => {
        if (confirm('Are you sure you want to remove all student records?')) {
            // Remove all children elements in tbody
            while (tbody.firstChild) {
                tbody.firstChild.remove();
            }
            statusBar.textContent = 'All student records cleared from DOM.';
            updateUIState();
        }
    });

    // Initial state setup
    updateUIState();
});
