package com.lab.co3;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Experiment 9: Servlet Lifecycle Demonstration
 * 
 * Objectives:
 * - Instrument constructor, init(), service()/doGet(), and destroy().
 * - Track execution counts for each lifecycle stage.
 * - Demonstrate that constructor and init() execute ONCE upon servlet loading,
 *   while service()/doGet() executes ONCE PER REQUEST on separate threads.
 * - Demonstrate destroy() execution on application undeploy/server shutdown.
 */
@WebServlet(name = "LifecycleServlet", urlPatterns = {"/lifecycle"}, loadOnStartup = 1)
public class LifecycleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Static / Thread-Safe Lifecycle Execution Counters
    private static final AtomicInteger constructorCount = new AtomicInteger(0);
    private static final AtomicInteger initCount = new AtomicInteger(0);
    private static final AtomicInteger requestCount = new AtomicInteger(0);
    private static final AtomicInteger destroyCount = new AtomicInteger(0);

    private String initTimestamp;
    private String initParamExample;

    /**
     * 1. Servlet Constructor (Instantiation Phase)
     * Executed once when the servlet container creates an instance.
     */
    public LifecycleServlet() {
        super();
        int count = constructorCount.incrementAndGet();
        System.out.println("==================================================");
        System.out.println("[LIFECYCLE STAGE 1] Constructor executed. Total calls: " + count);
        System.out.println("==================================================");
    }

    /**
     * 2. init(ServletConfig config) Method (Initialization Phase)
     * Executed once after instantiation to initialize resources.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        int count = initCount.incrementAndGet();
        initTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        initParamExample = config.getInitParameter("appName");
        if (initParamExample == null) {
            initParamExample = "Default CO3 Lifecycle Application";
        }
        System.out.println("==================================================");
        System.out.println("[LIFECYCLE STAGE 2] init() method executed.");
        System.out.println("Initialized At: " + initTimestamp);
        System.out.println("Total init() calls: " + count);
        System.out.println("==================================================");
    }

    /**
     * 3. doGet() / service() Method (Request Servicing Phase)
     * Executed on every HTTP GET request dispatch.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int currentReq = requestCount.incrementAndGet();
        long threadId = Thread.currentThread().getId();
        String threadName = Thread.currentThread().getName();

        System.out.println("[LIFECYCLE STAGE 3] doGet() executed for Request #" + currentReq + " on Thread [" + threadName + " (ID: " + threadId + ")]");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("    <title>Exp 9: Java Servlet Lifecycle Dashboard</title>");
            out.println("    <style>");
            out.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #0f172a; color: #f8fafc; padding: 2rem 1rem; margin: 0; }");
            out.println("        .container { max-width: 900px; margin: 0 auto; }");
            out.println("        header { text-align: center; margin-bottom: 2rem; }");
            out.println("        h1 { color: #38bdf8; font-size: 2.2rem; margin-bottom: 0.35rem; }");
            out.println("        .subtitle { color: #94a3b8; font-size: 1rem; }");
            out.println("        .grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem; margin-bottom: 1.5rem; }");
            out.println("        .metric-card { background: #1e293b; border: 1px solid #334155; border-radius: 12px; padding: 1.25rem; text-align: center; }");
            out.println("        .metric-title { font-size: 0.85rem; color: #94a3b8; text-transform: uppercase; font-weight: 600; }");
            out.println("        .metric-num { font-size: 2.5rem; font-weight: bold; margin: 0.5rem 0; }");
            out.println("        .c-1 { color: #f59e0b; }");
            out.println("        .c-2 { color: #3b82f6; }");
            out.println("        .c-3 { color: #10b981; }");
            out.println("        .c-4 { color: #ef4444; }");
            out.println("        .panel { background: #1e293b; border: 1px solid #334155; border-radius: 12px; padding: 1.5rem; margin-bottom: 1.5rem; }");
            out.println("        .panel h3 { color: #38bdf8; margin-top: 0; font-size: 1.2rem; border-bottom: 1px solid #334155; padding-bottom: 0.5rem; }");
            out.println("        table { width: 100%; border-collapse: collapse; margin-top: 1rem; font-size: 0.9rem; }");
            out.println("        th, td { padding: 0.75rem 1rem; text-align: left; border-bottom: 1px solid #334155; }");
            out.println("        th { background: #0f172a; color: #94a3b8; }");
            out.println("        .btn-refresh { display: inline-block; background: #0284c7; color: #ffffff; padding: 0.75rem 1.5rem; border-radius: 8px; text-decoration: none; font-weight: bold; }");
            out.println("        .btn-refresh:hover { background: #0369a1; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class='container'>");
            out.println("        <header>");
            out.println("            <h1>Servlet Lifecycle Monitor</h1>");
            out.println("            <p class='subtitle'>Live Execution Metrics of Java Servlet Lifecycle Methods</p>");
            out.println("        </header>");
            out.println("        ");
            out.println("        <div class='grid'>");
            out.println("            <div class='metric-card'>");
            out.println("                <div class='metric-title'>1. Constructor Calls</div>");
            out.println("                <div class='metric-num c-1'>" + constructorCount.get() + "</div>");
            out.println("                <span style='font-size:0.75rem; color:#64748b;'>Invoked on class load</span>");
            out.println("            </div>");
            out.println("            <div class='metric-card'>");
            out.println("                <div class='metric-title'>2. init() Calls</div>");
            out.println("                <div class='metric-num c-2'>" + initCount.get() + "</div>");
            out.println("                <span style='font-size:0.75rem; color:#64748b;'>Executed once</span>");
            out.println("            </div>");
            out.println("            <div class='metric-card'>");
            out.println("                <div class='metric-title'>3. doGet() / Service Calls</div>");
            out.println("                <div class='metric-num c-3'>" + currentReq + "</div>");
            out.println("                <span style='font-size:0.75rem; color:#64748b;'>Increments on each refresh</span>");
            out.println("            </div>");
            out.println("            <div class='metric-card'>");
            out.println("                <div class='metric-title'>4. destroy() Calls</div>");
            out.println("                <div class='metric-num c-4'>" + destroyCount.get() + "</div>");
            out.println("                <span style='font-size:0.75rem; color:#64748b;'>Invoked on server shutdown</span>");
            out.println("            </div>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div class='panel'>");
            out.println("            <h3>Current Request Telemetry</h3>");
            out.println("            <p><strong>Servlet Initialization Time:</strong> " + initTimestamp + "</p>");
            out.println("            <p><strong>Executing Thread:</strong> <code>" + threadName + "</code> (Thread ID: " + threadId + ")</p>");
            out.println("            <p><strong>Current Server Time:</strong> " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "</p>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div class='panel'>");
            out.println("            <h3>Servlet Lifecycle Phase Summary Table</h3>");
            out.println("            <table>");
            out.println("                <thead>");
            out.println("                    <tr><th>Phase</th><th>Method Name</th><th>Invocation Frequency</th><th>Primary Purpose</th></tr>");
            out.println("                </thead>");
            out.println("                <tbody>");
            out.println("                    <tr><td><strong>1. Instantiation</strong></td><td><code>Constructor()</code></td><td>1 time (per servlet class)</td><td>Loads class and allocates heap memory.</td></tr>");
            out.println("                    <tr><td><strong>2. Initialization</strong></td><td><code>init(ServletConfig)</code></td><td>1 time (after constructor)</td><td>Allocates DB connections, reads config.</td></tr>");
            out.println("                    <tr><td><strong>3. Servicing</strong></td><td><code>service() / doGet() / doPost()</code></td><td>N times (1 per HTTP request)</td><td>Processes client requests and sends response.</td></tr>");
            out.println("                    <tr><td><strong>4. Destruction</strong></td><td><code>destroy()</code></td><td>1 time (before garbage collection)</td><td>Releases open connections, files, and threads.</td></tr>");
            out.println("                </tbody>");
            out.println("            </table>");
            out.println("        </div>");
            out.println("        ");
            out.println("        <div style='text-align: center;'>");
            out.println("            <a href='lifecycle' class='btn-refresh'>🔄 Refresh Page to Test doGet() Increment</a>");
            out.println("        </div>");
            out.println("    </div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    /**
     * 4. destroy() Method (Destruction Phase)
     * Executed once when the servlet container unloads the servlet or shuts down.
     */
    @Override
    public void destroy() {
        int count = destroyCount.incrementAndGet();
        System.out.println("==================================================");
        System.out.println("[LIFECYCLE STAGE 4] destroy() method executed.");
        System.out.println("Total destroy() calls: " + count);
        System.out.println("All allocated resources cleaned up successfully.");
        System.out.println("==================================================");
        super.destroy();
    }
}
