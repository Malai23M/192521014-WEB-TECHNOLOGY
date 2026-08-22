package com.lab.co3;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Experiment 6: Basic Java Servlet for Dynamic Content Generation
 * 
 * Objectives:
 * - Extend HttpServlet and override doGet() method.
 * - Accept parameters or use dynamic defaults (Student Name, Course Name).
 * - Render dynamic HTML response with current server timestamp using PrintWriter.
 * - Configure servlet mapping in web.xml / @WebServlet.
 */
@WebServlet(name = "WelcomeServlet", urlPatterns = {"/welcome"})
public class WelcomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Set response MIME content type and character encoding
        response.setContentType("text/html;charset=UTF-8");

        // 2. Extract query parameters (with fallback defaults if omitted)
        String studentName = request.getParameter("studentName");
        if (studentName == null || studentName.trim().isEmpty()) {
            studentName = "Alex Morgan (Reg No: 2024CS108)";
        }

        String courseName = request.getParameter("courseName");
        if (courseName == null || courseName.trim().isEmpty()) {
            courseName = "CS8661 - Web Technology Laboratory (CO3: Client & Server Technologies)";
        }

        // 3. Compute dynamic timestamp using Java 8+ Time API
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy - hh:mm:ss a");
        String formattedDateTime = currentDateTime.format(formatter);

        // 4. Generate dynamic HTML page output via PrintWriter
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("    <title>Exp 6: Dynamic Content Generation Servlet</title>");
            out.println("    <style>");
            out.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f1f5f9; color: #1e293b; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }");
            out.println("        .card { background: #ffffff; padding: 2.5rem; border-radius: 16px; box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1); max-width: 600px; width: 90%; border-top: 5px solid #2563eb; }");
            out.println("        h1 { color: #1e3a8a; margin-top: 0; font-size: 1.8rem; }");
            out.println("        .badge { display: inline-block; background-color: #dbeafe; color: #1d4ed8; padding: 0.25rem 0.75rem; border-radius: 9999px; font-weight: 600; font-size: 0.85rem; margin-bottom: 1.5rem; }");
            out.println("        .info-group { margin: 1rem 0; padding-bottom: 0.75rem; border-bottom: 1px dashed #e2e8f0; }");
            out.println("        .label { font-size: 0.85rem; color: #64748b; font-weight: 600; text-transform: uppercase; letter-spacing: 0.5px; }");
            out.println("        .value { font-size: 1.1rem; color: #0f172a; font-weight: 500; margin-top: 0.25rem; }");
            out.println("        .timestamp { color: #059669; font-weight: bold; }");
            out.println("        .footer { margin-top: 1.5rem; font-size: 0.8rem; color: #94a3b8; text-align: center; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class='card'>");
            out.println("        <span class='badge'>Java Servlet Technology (CO3)</span>");
            out.println("        <h1>Welcome to Dynamic Web Services</h1>");
            out.println("        <p style='color: #475569;'>This dynamic webpage was generated in real-time by <code>WelcomeServlet</code> executing on Apache Tomcat.</p>");
            out.println("        ");
            out.println("        <div class='info-group'>");
            out.println("            <div class='label'>Student Name</div>");
            out.println("            <div class='value'>" + escapeHtml(studentName) + "</div>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div class='info-group'>");
            out.println("            <div class='label'>Course Title</div>");
            out.println("            <div class='value'>" + escapeHtml(courseName) + "</div>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div class='info-group'>");
            out.println("            <div class='label'>Current Server Date & Time</div>");
            out.println("            <div class='value timestamp'>" + formattedDateTime + "</div>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div class='info-group'>");
            out.println("            <div class='label'>Client Request Protocol / IP</div>");
            out.println("            <div class='value' style='font-size: 0.95rem; font-family: monospace;'>" + request.getProtocol() + " | IP: " + request.getRemoteAddr() + "</div>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div class='footer'>");
            out.println("            Servlet: <code>WelcomeServlet.java</code> | Apache Tomcat / Jakarta Servlet 4.0/5.0");
            out.println("        </div>");
            out.println("    </div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    /**
     * Sanitizes user input to prevent Cross-Site Scripting (XSS)
     */
    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
