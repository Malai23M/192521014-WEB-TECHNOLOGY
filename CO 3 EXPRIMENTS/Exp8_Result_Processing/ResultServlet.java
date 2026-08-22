package com.lab.co3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Experiment 8: Online Student Result Processing Using Servlet
 * 
 * Objectives:
 * - Accept student information and 5 subject marks.
 * - Perform robust validation (missing, non-numeric, out-of-range: 0-100).
 * - Calculate Total, Average, Highest Mark, Lowest Mark, Grade, and Pass/Fail Status.
 * - Render dynamic HTML table with color-coded grade badges.
 */
@WebServlet(name = "ResultServlet", urlPatterns = {"/result"})
public class ResultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String[] SUBJECT_NAMES = {
        "Web Technologies",
        "Data Structures & Algorithms",
        "Database Management Systems",
        "Operating Systems",
        "Computer Networks"
    };

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String studentName = request.getParameter("studentName");
        String regNumber = request.getParameter("regNumber");
        String department = request.getParameter("department");
        String semester = request.getParameter("semester");

        List<String> errors = new ArrayList<>();

        if (studentName == null || studentName.trim().isEmpty()) {
            errors.add("Student Name is required.");
        }
        if (regNumber == null || regNumber.trim().isEmpty()) {
            errors.add("Register Number is required.");
        }

        // Validate and parse 5 subject marks
        double[] marks = new double[5];
        for (int i = 0; i < 5; i++) {
            String paramName = "m" + (i + 1);
            String rawMark = request.getParameter(paramName);

            if (rawMark == null || rawMark.trim().isEmpty()) {
                errors.add("Mark for Subject " + (i + 1) + " (" + SUBJECT_NAMES[i] + ") is missing.");
            } else {
                try {
                    double val = Double.parseDouble(rawMark.trim());
                    if (val < 0 || val > 100) {
                        errors.add("Mark for Subject " + (i + 1) + " (" + SUBJECT_NAMES[i] + ") must be between 0 and 100. Entered: " + val);
                    } else {
                        marks[i] = val;
                    }
                } catch (NumberFormatException nfe) {
                    errors.add("Mark for Subject " + (i + 1) + " (" + SUBJECT_NAMES[i] + ") must be a valid number. Entered: " + rawMark);
                }
            }
        }

        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("    <title>Student Academic Performance Report</title>");
            out.println("    <style>");
            out.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f1f5f9; color: #1e293b; padding: 2rem 1rem; margin: 0; }");
            out.println("        .container { max-width: 850px; margin: 0 auto; background: #ffffff; padding: 2rem; border-radius: 14px; box-shadow: 0 10px 25px -5px rgba(0,0,0,0.08); border: 1px solid #e2e8f0; }");
            out.println("        h1 { color: #0f172a; margin-top: 0; font-size: 1.8rem; border-bottom: 2px solid #e2e8f0; padding-bottom: 0.5rem; }");
            out.println("        .error-card { background: #fee2e2; border-left: 4px solid #ef4444; padding: 1.5rem; border-radius: 8px; margin-bottom: 1.5rem; }");
            out.println("        .error-card h2 { color: #991b1b; margin-top: 0; font-size: 1.3rem; }");
            out.println("        .error-card ul { color: #b91c1c; margin-bottom: 0; padding-left: 1.25rem; }");
            out.println("        .student-header { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; background: #f8fafc; padding: 1.25rem; border-radius: 8px; margin-bottom: 1.5rem; border: 1px solid #e2e8f0; }");
            out.println("        .info-item { font-size: 0.95rem; }");
            out.println("        .info-item strong { color: #64748b; font-size: 0.85rem; display: block; text-transform: uppercase; }");
            out.println("        table { width: 100%; border-collapse: collapse; margin-bottom: 1.5rem; font-size: 0.95rem; }");
            out.println("        th, td { padding: 0.85rem 1rem; text-align: left; border-bottom: 1px solid #e2e8f0; }");
            out.println("        th { background-color: #f8fafc; color: #475569; font-weight: 600; }");
            out.println("        .metrics-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 1rem; margin-bottom: 1.5rem; }");
            out.println("        .metric-box { background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 8px; padding: 1rem; text-align: center; }");
            out.println("        .metric-title { font-size: 0.8rem; color: #64748b; text-transform: uppercase; font-weight: 600; }");
            out.println("        .metric-val { font-size: 1.5rem; font-weight: bold; margin-top: 0.25rem; color: #0f172a; }");
            out.println("        .badge-pass { background: #dcfce7; color: #15803d; padding: 0.35rem 0.8rem; border-radius: 9999px; font-weight: bold; font-size: 0.85rem; }");
            out.println("        .badge-fail { background: #fee2e2; color: #b91c1c; padding: 0.35rem 0.8rem; border-radius: 9999px; font-weight: bold; font-size: 0.85rem; }");
            out.println("        .btn { display: inline-block; padding: 0.65rem 1.25rem; background-color: #2563eb; color: #ffffff; text-decoration: none; border-radius: 8px; font-weight: 600; font-size: 0.9rem; }");
            out.println("        .btn:hover { background-color: #1d4ed8; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class='container'>");

            if (!errors.isEmpty()) {
                out.println("        <div class='error-card'>");
                out.println("            <h2>⚠️ Marks Processing Validation Error</h2>");
                out.println("            <ul>");
                for (String err : errors) {
                    out.println("                <li>" + escapeHtml(err) + "</li>");
                }
                out.println("            </ul>");
                out.println("        </div>");
                out.println("        <a href='index.html' class='btn'>&larr; Return to Input Form</a>");
            } else {
                // Compute Results
                double total = 0;
                double highest = marks[0];
                double lowest = marks[0];
                boolean isPassed = true;

                for (double m : marks) {
                    total += m;
                    if (m > highest) highest = m;
                    if (m < lowest) lowest = m;
                    if (m < 40.0) isPassed = false; // Subject pass mark is 40
                }

                double average = total / 5.0;
                String grade;
                if (!isPassed) {
                    grade = "F (Re-appear)";
                } else if (average >= 90) {
                    grade = "O (Outstanding)";
                } else if (average >= 80) {
                    grade = "A+ (Excellent)";
                } else if (average >= 70) {
                    grade = "A (Very Good)";
                } else if (average >= 60) {
                    grade = "B+ (Good)";
                } else if (average >= 50) {
                    grade = "B (Above Average)";
                } else {
                    grade = "C (Pass)";
                }

                out.println("        <h1>📊 Student Grade Card & Mark Analysis</h1>");
                out.println("        <div class='student-header'>");
                out.println("            <div class='info-item'><strong>Student Name</strong> " + escapeHtml(studentName) + "</div>");
                out.println("            <div class='info-item'><strong>Register Number</strong> " + escapeHtml(regNumber) + "</div>");
                out.println("            <div class='info-item'><strong>Department</strong> " + escapeHtml(department) + "</div>");
                out.println("            <div class='info-item'><strong>Semester</strong> " + escapeHtml(semester) + "</div>");
                out.println("        </div>");

                out.println("        <h3>Subject-Wise Marks Breakdown</h3>");
                out.println("        <table>");
                out.println("            <thead><tr><th>#</th><th>Subject Name</th><th>Max Marks</th><th>Marks Obtained</th><th>Status</th></tr></thead>");
                out.println("            <tbody>");
                for (int i = 0; i < 5; i++) {
                    boolean subPass = marks[i] >= 40.0;
                    out.println("                <tr>");
                    out.println("                    <td>" + (i + 1) + "</td>");
                    out.println("                    <td><strong>" + SUBJECT_NAMES[i] + "</strong></td>");
                    out.println("                    <td>100</td>");
                    out.println("                    <td>" + String.format("%.2f", marks[i]) + "</td>");
                    out.println("                    <td>" + (subPass ? "<span class='badge-pass'>PASS</span>" : "<span class='badge-fail'>FAIL</span>") + "</td>");
                    out.println("                </tr>");
                }
                out.println("            </tbody>");
                out.println("        </table>");

                out.println("        <h3>Summary Performance Metrics</h3>");
                out.println("        <div class='metrics-grid'>");
                out.println("            <div class='metric-box'><div class='metric-title'>Total Marks</div><div class='metric-val'>" + String.format("%.1f", total) + " / 500</div></div>");
                out.println("            <div class='metric-box'><div class='metric-title'>Average Percentage</div><div class='metric-val'>" + String.format("%.2f", average) + "%</div></div>");
                out.println("            <div class='metric-box'><div class='metric-title'>Highest Mark</div><div class='metric-val' style='color: #15803d;'>" + String.format("%.1f", highest) + "</div></div>");
                out.println("            <div class='metric-box'><div class='metric-title'>Lowest Mark</div><div class='metric-val' style='color: #b91c1c;'>" + String.format("%.1f", lowest) + "</div></div>");
                out.println("            <div class='metric-box'><div class='metric-title'>Overall Status</div><div class='metric-val'>" + (isPassed ? "<span class='badge-pass'>PASS</span>" : "<span class='badge-fail'>FAIL</span>") + "</div></div>");
                out.println("            <div class='metric-box'><div class='metric-title'>Final Grade</div><div class='metric-val' style='color: #2563eb; font-size: 1.1rem;'>" + grade + "</div></div>");
                out.println("        </div>");

                out.println("        <div style='text-align: center; margin-top: 2rem;'>");
                out.println("            <a href='index.html' class='btn'>Process Another Result</a>");
                out.println("        </div>");
            }

            out.println("    </div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
