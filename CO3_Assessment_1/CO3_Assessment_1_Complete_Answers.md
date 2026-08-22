# CO3 – Assessment 1 – Experiments: Complete Solution & Documentation

---

# Experiment Question 1: DOM-Based Interactive Student Registration Preview

## Aim
To develop a responsive student registration webpage using HTML, CSS, and JavaScript that dynamically previews student details as a formatted profile card without reloading the page, allows dynamic profile removal, and handles DOM manipulation using core JavaScript methods and event listeners.

---

## Algorithm

1. **Start** and render the HTML form with input fields for *Name*, *Register Number*, *Department*, and *Year of Study*.
2. Attach an event listener (`addEventListener`) to the **Preview Profile** button listening for `click` events.
3. On button click, prevent default form submission via `event.preventDefault()`.
4. Select input elements using `document.getElementById()` and `document.querySelector()`.
5. Perform validation to ensure all fields are filled:
   - If empty, display error messages under corresponding fields and highlight inputs with `.input-error`.
   - If valid, proceed to DOM generation.
6. Clear any previous profile or empty placeholder using `profileContainer.textContent = ''`.
7. Create the main card container dynamically using `document.createElement('div')` and assign visual styling using `card.classList.add('student-profile-card')`.
8. Create avatar, header info, and details grid elements, setting their values safely using `.textContent`.
9. Create a dynamic **Remove Profile** button using `document.createElement('button')`.
10. Attach a `click` event listener to the Remove button that invokes `element.remove()`, clearing the profile and restoring the empty state placeholder.
11. Append the created elements into the DOM tree inside `#profileContainer`.
12. **End**.

---

## Source Code

### 1. `index.html`
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Experiment 1 - Interactive Student Registration Preview</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="app-container">
        <header class="header">
            <div class="badge">CO3 Assessment 1 &bull; Experiment 1</div>
            <h1>Department Student Registration</h1>
            <p class="subtitle">Enter student details below to generate an interactive real-time profile preview using DOM methods.</p>
        </header>

        <main class="main-grid">
            <!-- Form Section -->
            <section class="card form-card">
                <div class="card-header">
                    <h2>Student Registration Form</h2>
                    <p>Fill in all details and click "Preview Profile".</p>
                </div>

                <form id="studentForm" novalidate>
                    <div class="form-group">
                        <label for="studentName">Student Full Name <span class="required">*</span></label>
                        <input type="text" id="studentName" placeholder="e.g. John Doe" autocomplete="off">
                        <small class="error-msg" id="nameError"></small>
                    </div>

                    <div class="form-group">
                        <label for="regNo">Register Number <span class="required">*</span></label>
                        <input type="text" id="regNo" placeholder="e.g. 717822P101" autocomplete="off">
                        <small class="error-msg" id="regNoError"></small>
                    </div>

                    <div class="form-group">
                        <label for="department">Department <span class="required">*</span></label>
                        <select id="department">
                            <option value="" disabled selected>-- Select Department --</option>
                            <option value="Computer Science and Engineering">Computer Science &amp; Engineering</option>
                            <option value="Information Technology">Information Technology</option>
                            <option value="Artificial Intelligence and Data Science">AI &amp; Data Science</option>
                            <option value="Electronics and Communication Engineering">Electronics &amp; Communication</option>
                            <option value="Mechanical Engineering">Mechanical Engineering</option>
                            <option value="Civil Engineering">Civil Engineering</option>
                        </select>
                        <small class="error-msg" id="deptError"></small>
                    </div>

                    <div class="form-group">
                        <label for="yearOfStudy">Year of Study <span class="required">*</span></label>
                        <select id="yearOfStudy">
                            <option value="" disabled selected>-- Select Year --</option>
                            <option value="1st Year (Freshman)">1st Year (Freshman)</option>
                            <option value="2nd Year (Sophomore)">2nd Year (Sophomore)</option>
                            <option value="3rd Year (Junior)">3rd Year (Junior)</option>
                            <option value="4th Year (Senior)">4th Year (Senior)</option>
                        </select>
                        <small class="error-msg" id="yearError"></small>
                    </div>

                    <div class="btn-group">
                        <button type="button" id="previewBtn" class="btn btn-primary">
                            Preview Profile
                        </button>
                        <button type="reset" id="resetBtn" class="btn btn-secondary">
                            Reset Form
                        </button>
                    </div>
                </form>
            </section>

            <!-- Dynamic Profile Preview Container -->
            <section class="preview-section">
                <div class="section-title-row">
                    <h2>Live Profile Preview</h2>
                    <span class="status-indicator" id="previewStatus">No profile generated</span>
                </div>

                <div id="profileContainer" class="profile-container">
                    <div id="emptyPlaceholder" class="empty-state">
                        <h3>No Student Profile Generated</h3>
                        <p>Fill out the registration form on the left and click <strong>Preview Profile</strong> to see the DOM-generated card here.</p>
                    </div>
                </div>
            </section>
        </main>
    </div>

    <script src="script.js"></script>
</body>
</html>
```

### 2. `styles.css`
```css
:root {
    --primary: #4338ca;
    --primary-hover: #3730a3;
    --primary-light: #e0e7ff;
    --secondary: #64748b;
    --danger: #ef4444;
    --danger-light: #fee2e2;
    --bg-main: #f8fafc;
    --surface: #ffffff;
    --text-primary: #0f172a;
    --text-secondary: #475569;
    --border: #e2e8f0;
    --radius-lg: 16px;
    --radius-md: 10px;
    --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.08);
    --shadow-xl: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
}

* { margin: 0; padding: 0; box-sizing: border-box; }
body {
    font-family: 'Plus Jakarta Sans', sans-serif;
    background-color: var(--bg-main);
    color: var(--text-primary);
    padding: 30px 20px;
}
.app-container { max-width: 1100px; margin: 0 auto; }
.header { text-align: center; margin-bottom: 35px; }
.header .badge {
    background-color: var(--primary-light);
    color: var(--primary);
    font-size: 0.85rem;
    font-weight: 700;
    padding: 6px 14px;
    border-radius: 9999px;
    text-transform: uppercase;
}
.main-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; }
@media (max-width: 850px) { .main-grid { grid-template-columns: 1fr; } }
.card {
    background: var(--surface);
    border-radius: var(--radius-lg);
    padding: 30px;
    border: 1px solid var(--border);
    box-shadow: var(--shadow-md);
}
.form-group { margin-bottom: 20px; }
.form-group label { display: block; font-size: 0.9rem; font-weight: 600; margin-bottom: 8px; }
.form-group input, .form-group select {
    width: 100%;
    padding: 12px 14px;
    border: 1.5px solid var(--border);
    border-radius: var(--radius-md);
}
.form-group input.input-error, .form-group select.input-error {
    border-color: var(--danger);
    background-color: #fff8f8;
}
.error-msg { color: var(--danger); font-size: 0.8rem; min-height: 18px; display: block; margin-top: 5px; }
.btn-group { display: flex; gap: 12px; margin-top: 25px; }
.btn {
    padding: 12px 20px;
    font-weight: 600;
    border-radius: var(--radius-md);
    cursor: pointer;
    border: none;
}
.btn-primary { background-color: var(--primary); color: #fff; flex: 2; }
.btn-secondary { background-color: #f1f5f9; color: var(--secondary); flex: 1; }

/* Dynamic Profile Card styling manipulated by classList */
.student-profile-card {
    background: #ffffff;
    border: 2px solid var(--primary);
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-xl);
    padding: 28px;
    animation: fadeInScale 0.35s ease-out;
}
@keyframes fadeInScale {
    from { opacity: 0; transform: translateY(12px) scale(0.97); }
    to { opacity: 1; transform: translateY(0) scale(1); }
}
.profile-card-header { display: flex; align-items: center; gap: 16px; margin-bottom: 20px; border-bottom: 1px solid var(--border); padding-bottom: 16px; }
.student-avatar {
    width: 60px; height: 60px; border-radius: 50%;
    background: linear-gradient(135deg, #4338ca, #6366f1);
    color: #fff; display: flex; align-items: center; justify-content: center;
    font-size: 1.4rem; font-weight: 700;
}
.profile-details-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 24px; }
.detail-item { background: #f8fafc; border: 1px solid var(--border); padding: 12px 14px; border-radius: var(--radius-md); }
.detail-label { font-size: 0.75rem; text-transform: uppercase; color: #94a3b8; font-weight: 600; }
.detail-value { font-size: 0.95rem; font-weight: 700; color: var(--text-primary); }
.btn-remove {
    background-color: var(--danger-light);
    color: var(--danger);
    border: 1px solid rgba(239, 68, 68, 0.2);
    padding: 10px 18px;
    border-radius: var(--radius-md);
    cursor: pointer;
    font-weight: 600;
}
```

### 3. `script.js`
```javascript
document.addEventListener('DOMContentLoaded', () => {
    // 1. Element Selection using getElementById() and querySelector()
    const nameInput = document.getElementById('studentName');
    const regNoInput = document.getElementById('regNo');
    const deptSelect = document.getElementById('department');
    const yearSelect = document.getElementById('yearOfStudy');
    const previewBtn = document.getElementById('previewBtn');
    const resetBtn = document.getElementById('resetBtn');
    const profileContainer = document.getElementById('profileContainer');
    const previewStatus = document.getElementById('previewStatus');

    const nameError = document.querySelector('#nameError');
    const regNoError = document.querySelector('#regNoError');
    const deptError = document.querySelector('#deptError');
    const yearError = document.querySelector('#yearError');

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

    function validateForm() {
        clearErrors();
        let isValid = true;

        if (!nameInput.value.trim()) {
            nameError.textContent = 'Please enter the student full name.';
            nameInput.classList.add('input-error');
            isValid = false;
        }
        if (!regNoInput.value.trim()) {
            regNoError.textContent = 'Please enter the register number.';
            regNoInput.classList.add('input-error');
            isValid = false;
        }
        if (!deptSelect.value) {
            deptError.textContent = 'Please select a department.';
            deptSelect.classList.add('input-error');
            isValid = false;
        }
        if (!yearSelect.value) {
            yearError.textContent = 'Please select year of study.';
            yearSelect.classList.add('input-error');
            isValid = false;
        }
        return isValid;
    }

    // 2. Dynamic DOM Creation & Profile Generation
    function generateProfile() {
        if (!validateForm()) return;

        const name = nameInput.value.trim();
        const regNo = regNoInput.value.trim().toUpperCase();
        const department = deptSelect.value;
        const year = yearSelect.value;

        profileContainer.textContent = '';

        // Create main card
        const card = document.createElement('div');
        card.classList.add('student-profile-card');
        card.id = 'activeProfileCard';

        // Avatar and Header
        const cardHeader = document.createElement('div');
        cardHeader.classList.add('profile-card-header');

        const avatar = document.createElement('div');
        avatar.classList.add('student-avatar');
        avatar.textContent = name.substring(0, 2).toUpperCase();

        const headerMeta = document.createElement('div');
        const nameHeading = document.createElement('h3');
        nameHeading.textContent = name;
        const regSpan = document.createElement('span');
        regSpan.textContent = `Reg No: ${regNo}`;

        headerMeta.appendChild(nameHeading);
        headerMeta.appendChild(regSpan);
        cardHeader.appendChild(avatar);
        cardHeader.appendChild(headerMeta);
        card.appendChild(cardHeader);

        // Details Grid
        const detailsGrid = document.createElement('div');
        detailsGrid.classList.add('profile-details-grid');

        function createItem(label, val) {
            const item = document.createElement('div');
            item.classList.add('detail-item');
            const l = document.createElement('div');
            l.classList.add('detail-label');
            l.textContent = label;
            const v = document.createElement('div');
            v.classList.add('detail-value');
            v.textContent = val;
            item.appendChild(l);
            item.appendChild(v);
            return item;
        }

        detailsGrid.appendChild(createItem('Department', department));
        detailsGrid.appendChild(createItem('Year of Study', year));
        card.appendChild(detailsGrid);

        // Actions & Remove Button
        const actionsDiv = document.createElement('div');
        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.classList.add('btn-remove');
        removeBtn.textContent = 'Remove Profile';

        // 3. Dynamic Element Removal using addEventListener() and remove()
        removeBtn.addEventListener('click', removeProfile);

        actionsDiv.appendChild(removeBtn);
        card.appendChild(actionsDiv);

        profileContainer.appendChild(card);
        previewStatus.textContent = 'Profile Displayed';
        previewStatus.classList.add('active');
    }

    function removeProfile() {
        const activeCard = document.getElementById('activeProfileCard');
        if (activeCard) activeCard.remove();

        profileContainer.innerHTML = `
            <div id="emptyPlaceholder" class="empty-state">
                <h3>No Student Profile Generated</h3>
                <p>Profile was removed. Fill out the form and click <strong>Preview Profile</strong>.</p>
            </div>
        `;
        previewStatus.textContent = 'No profile generated';
        previewStatus.classList.remove('active');
    }

    // 4. Event Listeners
    previewBtn.addEventListener('click', (e) => {
        e.preventDefault();
        generateProfile();
    });

    resetBtn.addEventListener('click', () => {
        clearErrors();
        removeProfile();
    });
});
```

---

# Experiment Question 2: Servlet-Based Student Result Processing

## Aim
To create a server-side web application using an HTML form (with `POST` method) and a Java Servlet (`StudentResultServlet`) deployed on Apache Tomcat that extracts student details and 3 subject marks, performs strict validations and concurrency-safe calculations (Total, Average, Highest Mark, Pass/Fail), and generates a dynamic HTML result report using `PrintWriter`.

---

## Algorithm

1. **Client Form Submission**:
   - The user enters *Student Name*, *Register Number*, and marks for *Subject 1*, *Subject 2*, and *Subject 3* in the HTML form.
   - The form submits parameters via HTTP `POST` method to the servlet URL mapping `/StudentResultServlet`.
2. **Servlet Invocation & Concurrency Control**:
   - Apache Tomcat container allocates a worker thread from its thread pool and invokes `doPost(HttpServletRequest request, HttpServletResponse response)`.
   - **Thread Safety**: All state variables are declared strictly as **local variables inside `doPost()`** (stack memory) ensuring complete request isolation across concurrent users.
3. **Parameter Extraction**:
   - Read parameters using `request.getParameter("studentName")`, `request.getParameter("regNo")`, `request.getParameter("subject1")`, `request.getParameter("subject2")`, and `request.getParameter("subject3")`.
4. **Server-Side Validation**:
   - Check if Name or Register Number is `null` or empty.
   - Parse subject marks to numeric types (`Double.parseDouble`). Catch `NumberFormatException` if invalid.
   - Verify that marks fall in the range $0 \le \text{mark} \le 100$.
   - If errors exist, render an HTML error report with a "Go Back" action.
5. **Business Logic Calculations**:
   - $\text{Total} = \text{mark}_1 + \text{mark}_2 + \text{mark}_3$
   - $\text{Average} = \frac{\text{Total}}{3.0}$
   - $\text{Highest Mark} = \max(\text{mark}_1, \max(\text{mark}_2, \text{mark}_3))$
   - $\text{Pass/Fail Status} = (\text{mark}_1 \ge 50 \land \text{mark}_2 \ge 50 \land \text{mark}_3 \ge 50) \implies \text{"PASS"} : \text{"FAIL"}$
6. **Dynamic Response Generation**:
   - Set content type to `text/html;charset=UTF-8`.
   - Obtain `PrintWriter out = response.getWriter()`.
   - Output structured HTML with student credentials, individual subject breakdown, total, percentage average, highest mark, and pass/fail status badge.
7. **End**.

---

## Source Code

### 1. `StudentResultServlet.java`
```java
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentResultServlet")
public class StudentResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // All variables are local to guarantee thread safety
        String studentName = request.getParameter("studentName");
        String regNo = request.getParameter("regNo");
        String sub1Str = request.getParameter("subject1");
        String sub2Str = request.getParameter("subject2");
        String sub3Str = request.getParameter("subject3");

        StringBuilder errorMessages = new StringBuilder();

        if (studentName == null || studentName.trim().isEmpty()) {
            errorMessages.append("<li>Student Name is required.</li>");
        } else {
            studentName = studentName.trim();
        }

        if (regNo == null || regNo.trim().isEmpty()) {
            errorMessages.append("<li>Register Number is required.</li>");
        } else {
            regNo = regNo.trim().toUpperCase();
        }

        double mark1 = 0, mark2 = 0, mark3 = 0;

        try {
            mark1 = Double.parseDouble(sub1Str != null ? sub1Str.trim() : "");
            if (mark1 < 0 || mark1 > 100) errorMessages.append("<li>Subject 1 mark must be 0 to 100.</li>");
        } catch (Exception e) {
            errorMessages.append("<li>Subject 1 mark must be a valid number.</li>");
        }

        try {
            mark2 = Double.parseDouble(sub2Str != null ? sub2Str.trim() : "");
            if (mark2 < 0 || mark2 > 100) errorMessages.append("<li>Subject 2 mark must be 0 to 100.</li>");
        } catch (Exception e) {
            errorMessages.append("<li>Subject 2 mark must be a valid number.</li>");
        }

        try {
            mark3 = Double.parseDouble(sub3Str != null ? sub3Str.trim() : "");
            if (mark3 < 0 || mark3 > 100) errorMessages.append("<li>Subject 3 mark must be 0 to 100.</li>");
        } catch (Exception e) {
            errorMessages.append("<li>Subject 3 mark must be a valid number.</li>");
        }

        if (errorMessages.length() > 0) {
            out.println("<html><body><h2>Validation Errors</h2><ul>" + errorMessages + "</ul><a href='javascript:history.back()'>Go Back</a></body></html>");
            return;
        }

        double total = mark1 + mark2 + mark3;
        double average = total / 3.0;
        double highestMark = Math.max(mark1, Math.max(mark2, mark3));
        boolean isPass = (mark1 >= 50.0 && mark2 >= 50.0 && mark3 >= 50.0);
        String passStatus = isPass ? "PASS" : "FAIL";

        // Dynamic HTML Result Output
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Student Result</title>");
        out.println("<style>");
        out.println("body { font-family: sans-serif; background: #f8fafc; padding: 40px; }");
        out.println(".card { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }");
        out.println("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
        out.println("th, td { padding: 10px; border-bottom: 1px solid #e2e8f0; text-align: left; }");
        out.println(".status { font-weight: bold; padding: 4px 12px; border-radius: 20px; }");
        out.println(".pass { background: #dcfce7; color: #15803d; }");
        out.println(".fail { background: #fee2e2; color: #b91c1c; }");
        out.println("</style></head><body>");
        out.println("<div class='card'>");
        out.println("<h2>Student Examination Result</h2>");
        out.println("<p><strong>Name:</strong> " + studentName + " | <strong>Reg No:</strong> " + regNo + "</p>");
        out.println("<table>");
        out.println("<tr><th>Subject</th><th>Marks</th></tr>");
        out.println("<tr><td>Subject 1</td><td>" + mark1 + "</td></tr>");
        out.println("<tr><td>Subject 2</td><td>" + mark2 + "</td></tr>");
        out.println("<tr><td>Subject 3</td><td>" + mark3 + "</td></tr>");
        out.println("<tr><th>Total</th><th>" + total + " / 300</th></tr>");
        out.println("<tr><th>Average</th><th>" + String.format("%.2f", average) + "%</th></tr>");
        out.println("<tr><th>Highest Mark</th><th>" + highestMark + "</th></tr>");
        out.println("<tr><th>Status</th><th><span class='status " + (isPass ? "pass" : "fail") + "'>" + passStatus + "</span></th></tr>");
        out.println("</table>");
        out.println("<a href='index.html'>&larr; Calculate Another Result</a>");
        out.println("</div></body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}
```

### 2. `web.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
                             http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

    <display-name>StudentResultProcessing</display-name>

    <welcome-file-list>
        <welcome-file>index.html</welcome-file>
    </welcome-file-list>

    <servlet>
        <servlet-name>StudentResultServlet</servlet-name>
        <servlet-class>StudentResultServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>StudentResultServlet</servlet-name>
        <url-pattern>/StudentResultServlet</url-pattern>
    </servlet-mapping>
</web-app>
```

---

## Apache Tomcat Deployment Steps

1. **Compilation**:
   ```cmd
   javac -cp "C:\apache-tomcat-9.0.x\lib\servlet-api.jar" -d "WebContent\WEB-INF\classes" "src\StudentResultServlet.java"
   ```
2. **Copy to Tomcat**:
   Copy the `WebContent/` directory into `C:\apache-tomcat-9.0.x\webapps\StudentResultApp`.
3. **Start Server**:
   Execute `C:\apache-tomcat-9.0.x\bin\startup.bat`.
4. **Access in Browser**:
   Navigate to `http://localhost:8080/StudentResultApp/`.
