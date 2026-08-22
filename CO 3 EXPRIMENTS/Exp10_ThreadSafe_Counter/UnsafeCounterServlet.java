package com.lab.co3;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Experiment 10 (Part A): Unsafe Visitor Counter Servlet
 * 
 * Demonstrates:
 * - Shared instance variable in a multi-threaded servlet environment.
 * - Non-atomic read-modify-write operation (counter++ is 3 CPU instructions: Read, Modify, Write).
 * - Deliberate sleep / yield to expose Race Conditions under concurrent requests.
 * 
 * WARNING: THIS IMPLEMENTATION IS INTENTIONALLY NOT THREAD-SAFE FOR EDUCATIONAL ANALYSIS.
 */
@WebServlet(name = "UnsafeCounterServlet", urlPatterns = {"/unsafe-counter"})
public class UnsafeCounterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Shared Instance Variable (Stored in Heap, shared across ALL concurrent threads/requests)
    private int unsafeVisitorCount = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Race Condition Demonstration:
        // 1. Read current value
        int temp = unsafeVisitorCount;

        // 2. Artificial delay to simulate processing or context switch
        try {
            Thread.sleep(25); // Gives window for another thread to read stale temp
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        // 3. Write back modified value
        unsafeVisitorCount = temp + 1;

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <title>Unsafe Visitor Counter (Race Condition Demo)</title>");
            out.println("    <style>");
            out.println("        body { font-family: 'Segoe UI', sans-serif; background-color: #fef2f2; color: #1e293b; padding: 2rem; display: flex; justify-content: center; align-items: center; min-height: 90vh; }");
            out.println("        .card { background: #ffffff; border: 2px solid #ef4444; border-radius: 12px; padding: 2rem; max-width: 600px; width: 100%; box-shadow: 0 10px 15px rgba(239,68,68,0.1); }");
            out.println("        .badge { background: #fee2e2; color: #b91c1c; padding: 0.25rem 0.75rem; border-radius: 9999px; font-weight: bold; font-size: 0.8rem; }");
            out.println("        .count { font-size: 3rem; font-weight: bold; color: #dc2626; margin: 1rem 0; text-align: center; }");
            out.println("        .alert { background: #fff1f2; border-left: 4px solid #e11d48; padding: 1rem; border-radius: 6px; font-size: 0.9rem; color: #9f1239; margin-top: 1rem; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class='card'>");
            out.println("        <span class='badge'>⚠️ Non-Thread-Safe Implementation</span>");
            out.println("        <h2 style='color: #991b1b; margin-top: 0.5rem;'>Unsafe Visitor Counter</h2>");
            out.println("        <p>Using primitive shared instance variable <code>int unsafeVisitorCount</code> without synchronization.</p>");
            out.println("        ");
            out.println("        <div class='count'>" + unsafeVisitorCount + "</div>");
            out.println("        ");
            out.println("        <p><strong>Executing Thread:</strong> " + Thread.currentThread().getName() + " (ID: " + Thread.currentThread().getId() + ")</p>");
            out.println("        ");
            out.println("        <div class='alert'>");
            out.println("            <strong>Vulnerability Analysis:</strong><br>");
            out.println("            Under 50+ concurrent requests, multiple threads read the same stale integer value simultaneously before either can write back, resulting in <em>lost updates</em>.");
            out.println("        </div>");
            out.println("        <div style='margin-top: 1.5rem; text-align: center;'>");
            out.println("            <a href='threadsafe-counter' style='color: #2563eb; font-weight: bold;'>Switch to Thread-Safe Counter Servlet &rarr;</a>");
            out.println("        </div>");
            out.println("    </div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}
