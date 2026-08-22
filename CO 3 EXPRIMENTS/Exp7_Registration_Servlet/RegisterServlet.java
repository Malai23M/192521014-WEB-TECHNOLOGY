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
 * Experiment 7: Student Registration Form Processing Using Servlet
 * 
 * Objectives:
 * - Handle HTTP POST requests using doPost().
 * - Extract form parameters using request.getParameter().
 * - Perform server-side validation on empty or invalid fields.
 * - Dynamically render confirmation or error feedback in HTML.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Set response content type & character encoding
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // 2. Extract parameters from the HTTP POST payload
        String studentName = request.getParameter("studentName");
        String regNumber = request.getParameter("regNumber");
        String email = request.getParameter("email");
        String department = request.getParameter("department");
        String semester = request.getParameter("semester");

        // 3. Server-Side Validation
        List<String> errorMessages = new ArrayList<>();

        if (studentName == null || studentName.trim().isEmpty()) {
            errorMessages.add("Student Name is required and cannot be blank.");
        }
        if (regNumber == null || regNumber.trim().isEmpty()) {
            errorMessages.add("Register Number is required.");
        }
        if (email == null || email.trim().isEmpty()) {
            errorMessages.add("Email address is required.");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errorMessages.add("Please provide a valid email format (e.g. name@domain.com).");
        }
        if (department == null || department.trim().isEmpty()) {
            errorMessages.add("Department must be selected.");
        }
        if (semester == null || semester.trim().isEmpty()) {
            errorMessages.add("Semester must be selected.");
        }

        // 4. Render HTML Response
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("    <title>Registration Result | Servlet Processing</title>");
            out.println("    <style>");
            out.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8fafc; color: #1e293b; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; padding: 1.5rem; }");
            out.println("        .card { background: #ffffff; padding: 2.5rem; border-radius: 16px; box-shadow: 0 10px 25px -5px rgba(0,0,0,0.08); max-width: 600px; width: 100%; border: 1px solid #e2e8f0; }");
            out.println("        .header-success { color: #15803d; border-bottom: 2px solid #bbf7d0; padding-bottom: 0.75rem; margin-bottom: 1.5rem; font-size: 1.6rem; }");
            out.println("        .header-error { color: #b91c1c; border-bottom: 2px solid #fecaca; padding-bottom: 0.75rem; margin-bottom: 1.5rem; font-size: 1.6rem; }");
            out.println("        .error-list { background-color: #fee2e2; border-left: 4px solid #ef4444; padding: 1rem 1.5rem; border-radius: 6px; margin-bottom: 1.5rem; }");
            out.println("        .error-list li { color: #991b1b; margin-bottom: 0.35rem; font-size: 0.9rem; }");
            out.println("        .details-table { width: 100%; border-collapse: collapse; margin-bottom: 1.5rem; }");
            out.println("        .details-table th, .details-table td { padding: 0.75rem 1rem; text-align: left; border-bottom: 1px solid #f1f5f9; font-size: 0.95rem; }");
            out.println("        .details-table th { color: #64748b; font-weight: 600; width: 40%; background: #f8fafc; }");
            out.println("        .details-table td { color: #0f172a; font-weight: 500; }");
            out.println("        .badge { display: inline-block; background: #e0f2fe; color: #0369a1; padding: 0.2rem 0.6rem; border-radius: 4px; font-size: 0.85rem; font-weight: bold; }");
            out.println("        .btn { display: inline-block; padding: 0.65rem 1.25rem; background-color: #2563eb; color: #ffffff; text-decoration: none; border-radius: 8px; font-weight: 600; font-size: 0.9rem; text-align: center; }");
            out.println("        .btn:hover { background-color: #1d4ed8; }");
            out.println("        .btn-retry { background-color: #dc2626; }");
            out.println("        .btn-retry:hover { background-color: #b91c1c; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class='card'>");

            if (!errorMessages.isEmpty()) {
                // If validation failed
                out.println("        <h2 class='header-error'>❌ Validation Failed</h2>");
                out.println("        <p>The form could not be processed due to the following missing or invalid fields:</p>");
                out.println("        <ul class='error-list'>");
                for (String err : errorMessages) {
                    out.println("            <li>" + escapeHtml(err) + "</li>");
                }
                out.println("        </ul>");
                out.println("        <a href='index.html' class='btn btn-retry'>&larr; Back to Registration Form</a>");
            } else {
                // If validation passed
                out.println("        <h2 class='header-success'>✅ Registration Successful!</h2>");
                out.println("        <p style='color: #475569; margin-bottom: 1.25rem;'>Student record has been successfully validated and processed by <code>RegisterServlet</code>.</p>");
                out.println("        <table class='details-table'>");
                out.println("            <tr><th>Student Name</th><td>" + escapeHtml(studentName) + "</td></tr>");
                out.println("            <tr><th>Register Number</th><td><span class='badge'>" + escapeHtml(regNumber) + "</span></td></tr>");
                out.println("            <tr><th>Email Address</th><td>" + escapeHtml(email) + "</td></tr>");
                out.println("            <tr><th>Department</th><td>" + escapeHtml(department) + "</td></tr>");
                out.println("            <tr><th>Semester</th><td>" + escapeHtml(semester) + "</td></tr>");
                out.println("            <tr><th>Processing Method</th><td><code>HTTP POST (doPost)</code></td></tr>");
                out.println("        </table>");
                out.println("        <div style='text-align: center; margin-top: 1rem;'>");
                out.println("            <a href='index.html' class='btn'>Register Another Student</a>");
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
        // Redirect direct GET access to the registration form
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
