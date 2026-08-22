/**
 * Experiment 3: Interactive Event Registration Using JavaScript Events
 * Demonstrates: onclick, change, input, focus, blur, mouseover, mouseout, submit, addEventListener()
 * Compares intrinsic handlers vs modern addEventListener()
 */

// Global Event Stream Logger Helper
function appendLog(type, eventName, message) {
    const stream = document.getElementById('event-stream');
    if (!stream) return;

    const time = new Date().toLocaleTimeString();
    const logItem = document.createElement('div');
    logItem.className = 'log-item';

    const tagClass = type === 'Intrinsic' ? 'tag-intrinsic' : 'tag-modern';

    logItem.innerHTML = `
        <span class="log-time">[${time}]</span>
        <span class="log-tag ${tagClass}">${type}</span>
        <span class="log-msg"><strong>${eventName}</strong>: ${message}</span>
    `;

    stream.insertBefore(logItem, stream.firstChild);
}

// -------------------------------------------------------------
// 1. INTRINSIC EVENT HANDLERS (Called directly from HTML inline attributes)
// -------------------------------------------------------------

function logIntrinsicEvent(eventName, element) {
    const elDesc = element.id ? `#${element.id}` : element.tagName.toLowerCase();
    appendLog('Intrinsic', eventName, `Fired on element: ${elDesc}`);
}

function handleIntrinsicInput(inputElement) {
    const charCount = inputElement.value.length;
    document.getElementById('char-count').textContent = charCount;
    appendLog('Intrinsic', 'input', `Current value length = ${charCount}`);
}

function handleIntrinsicSubmit(event) {
    event.preventDefault(); // Prevent standard page reload

    const name = document.getElementById('participant-name').value;
    const email = document.getElementById('participant-email').value;
    const category = document.getElementById('event-category').value;
    const attendance = document.querySelector('input[name="attendance"]:checked').value;

    appendLog('Intrinsic', 'submit', `Form validated and submitted for ${name}`);

    // Show summary box
    const summaryBox = document.getElementById('registration-summary');
    const summaryDetails = document.getElementById('summary-details');

    summaryDetails.innerHTML = `
        <p><strong>Candidate:</strong> ${name} (${email})</p>
        <p><strong>Event:</strong> ${category}</p>
        <p><strong>Mode:</strong> ${attendance}</p>
        <p><strong>Registered At:</strong> ${new Date().toLocaleString()}</p>
    `;
    summaryBox.classList.remove('hidden');

    return false;
}

// -------------------------------------------------------------
// 2. MODERN EVENT HANDLERS (Using addEventListener())
// -------------------------------------------------------------

document.addEventListener('DOMContentLoaded', () => {
    // Clear logs button
    const btnClearLogs = document.getElementById('btn-clear-logs');
    btnClearLogs.addEventListener('click', () => {
        document.getElementById('event-stream').innerHTML = '';
    });

    // Modern click listener on dedicated button
    const btnModernClick = document.getElementById('btn-modern-click');
    btnModernClick.addEventListener('click', (e) => {
        appendLog('Modern', 'click', `Captured via addEventListener('click') on #${e.target.id}`);
    });

    // Multiple listeners demo on the same element
    btnModernClick.addEventListener('click', () => {
        appendLog('Modern', 'click (2nd listener)', 'Multiple listeners successfully executed on single click!');
    });

    // Email Input Focus & Blur via addEventListener
    const emailInput = document.getElementById('participant-email');
    emailInput.addEventListener('focus', () => {
        emailInput.style.backgroundColor = '#f0fdf4';
        appendLog('Modern', 'focus', 'Email input focused via addEventListener()');
    });

    emailInput.addEventListener('blur', () => {
        emailInput.style.backgroundColor = '';
        appendLog('Modern', 'blur', 'Email input lost focus via addEventListener()');
    });

    // Attendance Radio buttons change event via addEventListener
    const attendanceRadios = document.querySelectorAll('input[name="attendance"]');
    attendanceRadios.forEach(radio => {
        radio.addEventListener('change', (e) => {
            appendLog('Modern', 'change', `Attendance mode switched to: ${e.target.value} via addEventListener()`);
        });
    });

    // Mouseover / Mouseout on Interactive Banner via addEventListener (Chaining secondary listener)
    const banner = document.getElementById('interactive-banner');
    banner.addEventListener('mouseover', () => {
        appendLog('Modern', 'mouseover', 'Hovering banner card detected by addEventListener()');
    });

    banner.addEventListener('mouseout', () => {
        appendLog('Modern', 'mouseout', 'Mouse left banner card detected by addEventListener()');
    });

    // Initial system log
    appendLog('Modern', 'DOMContentLoaded', 'Page DOM fully loaded and modern listeners attached.');
});
