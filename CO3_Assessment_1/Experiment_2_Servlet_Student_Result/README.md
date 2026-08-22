# Experiment 2: Servlet-Based Student Result Processing

## Overview
This application processes student examination results dynamically on the server side using a **Java Servlet (`HttpServlet`)** deployed on **Apache Tomcat**.

---

## Assessed Concepts & Implementation Details

1. **Servlet Architecture & `doPost()`**:
   - `StudentResultServlet` extends `HttpServlet` and overrides `doPost(HttpServletRequest request, HttpServletResponse response)`.
   - The HTML form sends data using `method="POST"` to `/StudentResultServlet`.

2. **Parameter Extraction**:
   - Read parameters safely using `request.getParameter("studentName")`, `request.getParameter("regNo")`, `request.getParameter("subject1")`, `request.getParameter("subject2")`, and `request.getParameter("subject3")`.

3. **Thread Safety & Basic Servlet Concurrency Awareness**:
   - In a Servlet container, a single servlet instance handles multiple concurrent requests across multiple threads.
   - To prevent race conditions and data corruption across student submissions, **no instance variables** are used.
   - All student-specific information (Name, Register Number, Subject Marks, Total, Average, Highest Mark, Pass/Fail status) is stored strictly in **local variables** allocated on the thread's call stack within `doPost()`.

4. **Input Validation**:
   - Ensures Name and Register Number are not empty or whitespace.
   - Validates that marks for all three subjects are valid numbers between `0` and `100`.
   - If invalid, dynamically generates a helpful error page with a "Go Back" action.

5. **Business Logic & Calculations**:
   - $\text{Total} = \text{Subject}_1 + \text{Subject}_2 + \text{Subject}_3$
   - $\text{Average} = \frac{\text{Total}}{3.0}$
   - $\text{Highest Mark} = \max(\text{Subject}_1, \text{Subject}_2, \text{Subject}_3)$
   - $\text{Pass/Fail Status} = (\text{Subject}_1 \ge 50 \land \text{Subject}_2 \ge 50 \land \text{Subject}_3 \ge 50) \implies \text{PASS} \text{ else } \text{FAIL}$

6. **Dynamic Content Generation via `PrintWriter`**:
   - `response.setContentType("text/html;charset=UTF-8")`
   - `PrintWriter out = response.getWriter()` dynamically writes the formatted result report table and performance badges.

---

## Project & Tomcat Directory Layout

```
<Tomcat_Directory>/webapps/StudentResultApp/
├── index.html                   # HTML Form for marks submission (POST)
├── style.css                    # Form Styling
└── WEB-INF/
    ├── web.xml                  # Servlet Mapping Descriptor
    └── classes/
        └── StudentResultServlet.class  # Compiled bytecode
```

---

## Step-by-Step Compilation & Apache Tomcat Deployment

### Step 1: Compile the Java Servlet
Open Command Prompt / PowerShell in the project directory:

```bash
cd "C:\Users\Malaiarasan M\.gemini\antigravity\scratch\CO3_Assessment_1\Experiment_2_Servlet_Student_Result"
```

Compile `StudentResultServlet.java` by referencing Tomcat's `servlet-api.jar`:

```bash
# Replace C:\apache-tomcat-9.0.x with your actual Tomcat installation path
javac -cp "C:\apache-tomcat-9.0.x\lib\servlet-api.jar" -d "WebContent\WEB-INF\classes" "src\StudentResultServlet.java"
```

> **Note**: For Tomcat 10+, replace `javax.servlet` with `jakarta.servlet` and use `jakarta.servlet-api.jar`.

### Step 2: Deploy to Apache Tomcat
Copy the `WebContent` folder to your Tomcat `webapps/` directory and rename it to `StudentResultApp`:

```bash
# Example deployment command (Windows):
xcopy /E /I "WebContent" "C:\apache-tomcat-9.0.x\webapps\StudentResultApp"
```

### Step 3: Start Apache Tomcat
Run Tomcat's startup script:
- Windows: `C:\apache-tomcat-9.0.x\bin\startup.bat`
- Linux/Mac: `./catalina.sh run`

### Step 4: Access the Application in Browser
Open your browser and navigate to:
```
http://localhost:8080/StudentResultApp/
```

---

## Test Cases & Verification

| Test Case | Inputs | Expected Output |
| :--- | :--- | :--- |
| **Normal Pass** | Name: Alice, Reg: `717822P101`, Marks: 85, 92, 78 | Total: `255.00`, Avg: `85.00%`, Highest: `92.00`, Status: **PASS**, Grade: `A+` |
| **Normal Fail** | Name: Bob, Reg: `717822P102`, Marks: 45, 80, 75 | Total: `200.00`, Avg: `66.67%`, Highest: `80.00`, Status: **FAIL**, Grade: `F (Fail)` (Subject 1 < 50) |
| **Validation: Out of Range** | Marks: 105, 90, 80 | Validation Error: *Subject 1 mark (105.0) is out of range. Must be between 0 and 100.* |
| **Validation: Empty Field** | Name: `""`, Reg: `""` | Validation Error: *Student Name and Register Number are required.* |
