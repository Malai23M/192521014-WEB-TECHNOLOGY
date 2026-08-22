package com.lab.co3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Experiment 10 (Part B): Thread-Safe Concurrent Visitor Counter Servlet
 * 
 * Demonstrates:
 * - Thread-Safe shared state using AtomicInteger (Lock-Free Hardware CAS - Compare-And-Swap).
 * - Synchronized block alternative pattern.
 * - Why local variables allocated on thread stack are inherently thread-safe.
 */
@WebServlet(name = "ThreadSafeCounterServlet", urlPatterns = {"/threadsafe-counter"})
public class ThreadSafeCounterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Pattern 1: AtomicInteger (High-performance, lock-free thread-safe counter)
    private final AtomicInteger atomicVisitorCounter = new AtomicInteger(0);

    // Pattern 2: Synchronized Counter
    private int synchronizedCounter = 0;
    private final Object lock = new Object();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Thread-Safe Increment using AtomicInteger (incrementAndGet is atomic)
        int currentAtomicCount = atomicVisitorCounter.incrementAndGet();

        // 2. Thread-Safe Increment using synchronized block
        int currentSyncCount;
        synchronized (lock) {
            synchronizedCounter++;
            currentSyncCount = synchronizedCounter;
        }

        // 3. Local Variables (Allocated on the Thread Stack - private to each thread, 100% thread-safe)
        long currentThreadId = Thread.currentThread().getId();
        String currentThreadName = Thread.currentThread().getName();
        String clientIp = request.getRemoteAddr();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <title>Thread-Safe Concurrent Visitor Counter</title>");
            out.println("    <style>");
            out.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f0fdf4; color: #1e293b; padding: 2rem; display: flex; justify-content: center; align-items: center; min-height: 90vh; }");
            out.println("        .card { background: #ffffff; border: 2px solid #22c55e; border-radius: 12px; padding: 2rem; max-width: 650px; width: 100%; box-shadow: 0 10px 20px rgba(34,197,94,0.1); }");
            out.println("        .badge { background: #dcfce7; color: #15803d; padding: 0.25rem 0.75rem; border-radius: 9999px; font-weight: bold; font-size: 0.8rem; }");
            out.println("        .count-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; margin: 1.5rem 0; }");
            out.println("        .count-box { background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 8px; padding: 1.25rem; text-align: center; }");
            out.println("        .count-val { font-size: 2.2rem; font-weight: bold; color: #16a34a; margin-top: 0.25rem; }");
            out.println("        .info-panel { background: #f8fafc; border-left: 4px solid #16a34a; padding: 1rem; border-radius: 6px; font-size: 0.9rem; color: #334155; margin-top: 1rem; }");
            out.println("        .info-panel p { margin: 0.3rem 0; }");
            out.println("        .btn-refresh { display: inline-block; background-color: #16a34a; color: #ffffff; padding: 0.65rem 1.5rem; text-decoration: none; border-radius: 8px; font-weight: bold; margin-top: 1.25rem; }");
            out.println("        .btn-refresh:hover { background-color: #15803d; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class='card'>");
            out.println("        <span class='badge'>🛡️ Thread-Safe Production Standard</span>");
            out.println("        <h2 style='color: #166534; margin-top: 0.5rem;'>Thread-Safe Visitor Counter</h2>");
            out.println("        <p>Using <code>java.util.concurrent.atomic.AtomicInteger</code> and synchronized blocks to prevent race conditions.</p>");
            out.println("        ");
            out.println("        <div class='count-grid'>");
            out.println("            <div class='count-box'>");
            out.println("                <div style='font-size:0.8rem; color:#64748b; font-weight:bold;'>ATOMIC INTEGER COUNT</div>");
            out.println("                <div class='count-val'>" + currentAtomicCount + "</div>");
            out.println("            </div>");
            out.println("            <div class='count-box'>");
            out.println("                <div style='font-size:0.8rem; color:#64748b; font-weight:bold;'>SYNCHRONIZED COUNT</div>");
            out.println("                <div class='count-val'>" + currentSyncCount + "</div>");
            out.println("            </div>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div class='info-panel'>");
            out.println("            <p><strong>Thread Context:</strong> " + currentThreadName + " (ID: " + currentThreadId + ")</p>");
            out.println("            <p><strong>Client Remote IP:</strong> " + clientIp + "</p>");
            out.println("            <p><strong>Stack vs Heap:</strong> Thread metadata stored in method local variables (stack-isolated).</p>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div style='text-align: center;'>");
            out.println("            <a href='threadsafe-counter' class='btn-refresh'>🔄 Send Another Request</a>");
            out.println("        </div>");
            out.println("    </div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}
