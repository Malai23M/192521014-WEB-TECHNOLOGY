# CO3 – Assessment 1: Experiments Lab Manual & Solutions

This repository contains the complete, tested solutions for both questions in **CO3 – Assessment 1 – Experiments**.

---

## Table of Contents
1. [Experiment 1: DOM-Based Interactive Student Registration Preview](#experiment-1-dom-based-interactive-student-registration-preview)
2. [Experiment 2: Servlet-Based Student Result Processing](#experiment-2-servlet-based-student-result-processing)
3. [Summary of Concepts & Viva Reference](#summary-of-concepts--viva-reference)

---

# Experiment 1: DOM-Based Interactive Student Registration Preview

### Problem Statement
A department wants a simple student registration webpage where students can enter their **Name**, **Register Number**, **Department**, and **Year of Study**. Before submitting the details, the webpage should immediately display the entered information as a formatted student profile without reloading the page. The user should also be able to remove the displayed profile using a button. The application must use DOM methods and suitable JavaScript events to perform these operations dynamically.

### File Structure
- `Experiment_1_DOM_Student_Registration/index.html`: Form UI and dynamic preview mount point.
- `Experiment_1_DOM_Student_Registration/styles.css`: Visual styling, card animations, and dynamic class styles.
- `Experiment_1_DOM_Student_Registration/script.js`: DOM manipulation, element selection, validation, event handling, dynamic creation, and removal.

### Key DOM Methods & Properties Demonstrated
- **Element Selection**: `document.getElementById('studentName')`, `document.querySelector('#nameError')`.
- **Dynamic Element Creation**: `document.createElement('div')`, `document.createElement('button')`, `document.createElement('span')`.
- **Dynamic Content Setting**: `element.textContent = name;`.
- **Event Handling**: `previewBtn.addEventListener('click', generateProfile)` and `removeBtn.addEventListener('click', removeProfile)`.
- **Class Manipulation**: `card.classList.add('student-profile-card')`, `previewStatus.classList.add('active')`.
- **DOM Removal**: `activeCard.remove()` dynamically removing the element from the document tree.

### How to Run Experiment 1
Simply double-click `Experiment_1_DOM_Student_Registration/index.html` or open it in any web browser (Chrome, Edge, Firefox, Safari).

---

# Experiment 2: Servlet-Based Student Result Processing

### Problem Statement
A faculty member wants a server-side application that accepts a student's **Name**, **Register Number**, and **marks in three subjects** through an HTML form. The submitted data must be processed by a Java Servlet, which should calculate the **Total**, **Average**, **Highest Mark**, and **Pass/Fail status** and generate the result dynamically in the browser. The servlet must validate missing values and marks outside the range of 0 to 100. Request-specific student data must be maintained using local variables so that the servlet remains safe when multiple requests are processed.

### File Structure
- `Experiment_2_Servlet_Student_Result/src/StudentResultServlet.java`: Java Servlet class.
- `Experiment_2_Servlet_Student_Result/WebContent/index.html`: Marks submission HTML form (`method="POST"`).
- `Experiment_2_Servlet_Student_Result/WebContent/style.css`: Stylesheet for the input form.
- `Experiment_2_Servlet_Student_Result/WebContent/WEB-INF/web.xml`: Deployment descriptor.
- `Experiment_2_Servlet_Student_Result/README.md`: Compilation and Tomcat deployment guide.

### Key Servlet Concepts Demonstrated
- **Servlet Architecture & `doPost()`**: Handles HTTP POST requests securely.
- **Request Parameter Handling**: `request.getParameter(...)` to extract form values.
- **Servlet Concurrency & Thread Safety**: All fields are declared as local variables in `doPost()` to ensure isolation across concurrent client requests.
- **Data Validation**: Checks for missing fields and enforces $0 \le \text{mark} \le 100$.
- **Calculations**: Total, Average, Maximum mark, and Pass/Fail conditions ($\ge 50$ in all subjects).
- **Dynamic HTML Output**: `PrintWriter out = response.getWriter()` dynamically writing styled HTML.

---

## Summary of Concepts & Viva Reference

| Concept | Experiment 1 | Experiment 2 |
| :--- | :--- | :--- |
| **Execution Environment** | Client-Side (Browser JavaScript Engine) | Server-Side (JVM / Apache Tomcat Container) |
| **Data Flow** | Captured via DOM events and rendered directly | Sent via HTTP POST to Servlet and returned as HTML |
| **Concurrency Management** | Single-threaded JavaScript event loop | Multi-threaded Servlet container using local stack variables |
| **Validation Mechanism** | Real-time JavaScript validation before DOM insertion | Server-side validation with error response |
