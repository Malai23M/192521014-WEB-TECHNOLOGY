# CO3 Experiments: Client-Side DOM, JavaScript Events, Window Object & Java Servlets

This repository contains the complete, production-ready source code, HTML/CSS/JS frontend interfaces, Java Servlets, deployment descriptors (`web.xml`), and laboratory manual solutions for all **10 experiments** in Course Outcome 3 (CO3).

---

## 📁 Repository Structure

```
CO/
├── README.md                                    # This master index and execution guide
├── CO3_Lab_Manual_Answers_and_Results.md        # Comprehensive Lab Manual (Aim, Algorithm, Code, Results, Viva)
│
├── Exp1_DOM_Profile/                            # Exp 1: DOM-Based Student Profile Manipulation
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── Exp2_Dynamic_Registration/                   # Exp 2: Dynamic Student Registration List Using DOM
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── Exp3_JavaScript_Events/                      # Exp 3: Interactive Event Registration Using JS Events
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── Exp4_Window_Dashboard/                       # Exp 4: Browser Information Dashboard Using Window Object
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── Exp5_Cross_Browser/                          # Exp 5: Cross-Browser Compatible Interactive Webpage
│   ├── index.html
│   ├── style.css
│   └── script.js
│
├── Exp6_Basic_Servlet/                          # Exp 6: Basic Java Servlet for Dynamic Content Generation
│   ├── WelcomeServlet.java
│   └── web.xml
│
├── Exp7_Registration_Servlet/                   # Exp 7: Student Registration Form Processing Using Servlet
│   ├── index.html
│   ├── RegisterServlet.java
│   └── web.xml
│
├── Exp8_Result_Processing/                      # Exp 8: Online Student Result Processing Using Servlet
│   ├── index.html
│   ├── ResultServlet.java
│   └── web.xml
│
├── Exp9_Servlet_Lifecycle/                      # Exp 9: Servlet Lifecycle Demonstration
│   ├── LifecycleServlet.java
│   └── web.xml
│
└── Exp10_ThreadSafe_Counter/                    # Exp 10: Thread-Safe Concurrent Visitor Counter Using Servlet
    ├── UnsafeCounterServlet.java
    ├── ThreadSafeCounterServlet.java
    ├── concurrency_analysis.md
    └── web.xml
```

---

## 🚀 How to Run the Experiments

### Frontend Experiments (Exp 1 to Exp 5)
These experiments are pure client-side HTML5, CSS3, and modern Vanilla JavaScript.
1. Navigate into any experiment folder (e.g. `Exp1_DOM_Profile`).
2. Double-click `index.html` or open it directly in any modern web browser (Google Chrome, Microsoft Edge, Mozilla Firefox, Safari).
3. Alternatively, serve via VS Code Live Server or python:
   ```bash
   python -m http.server 8080
   ```

---

### Backend Java Servlet Experiments (Exp 6 to Exp 10)
These experiments require a Java Development Kit (JDK 8, 11, 17, or 21) and a Servlet Container such as **Apache Tomcat 9.x / 10.x** (or Eclipse / IntelliJ IDEA / Apache NetBeans).

#### Compilation using `javac`:
Ensure `servlet-api.jar` (found in Tomcat's `lib/` directory) is on your classpath:
```bash
# Example compilation command on Windows:
javac -cp "C:\apache-tomcat-9.0.x\lib\servlet-api.jar;." -d ./WEB-INF/classes WelcomeServlet.java
```

#### Deployment in Apache Tomcat:
1. Create a folder named `CO3_App` inside Tomcat's `webapps/` directory.
2. Place the `index.html` files and `WEB-INF/` folder containing:
   - `WEB-INF/web.xml`
   - `WEB-INF/classes/com/lab/co3/*.class`
3. Start Apache Tomcat using `bin/startup.bat`.
4. Access via browser at:
   - Exp 6: `http://localhost:8080/CO3_App/welcome`
   - Exp 7: `http://localhost:8080/CO3_App/register` (or `index.html`)
   - Exp 8: `http://localhost:8080/CO3_App/result` (or `index.html`)
   - Exp 9: `http://localhost:8080/CO3_App/lifecycle`
   - Exp 10: `http://localhost:8080/CO3_App/threadsafe-counter` (and `/unsafe-counter`)

---

## 📖 Complete Solutions Document
For the complete lab manual with theoretical questions, code listings, execution steps, expected results, and Viva Voce answers, open:
`CO3_Lab_Manual_Answers_and_Results.md`
