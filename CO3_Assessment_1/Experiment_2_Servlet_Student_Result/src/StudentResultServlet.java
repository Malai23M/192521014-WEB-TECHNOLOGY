import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CO3 Assessment 1 - Experiment 2
 * Servlet-Based Student Result Processing
 * 
 * Concepts Demonstrated:
 * 1. Servlet Architecture: Extends HttpServlet, handles POST requests via doPost()
 * 2. Parameter Handling: request.getParameter() to extract form fields
 * 3. Thread Safety / Concurrency: ALL request-specific data is kept in local variables
 *    inside doPost() ensuring safe concurrent multi-threaded execution.
 * 4. Server-Side Validation: Validates missing values and marks range (0 - 100).
 * 5. Business Logic: Calculates Total, Average, Highest Mark, and Pass/Fail Status.
 * 6. Dynamic Content Generation: Outputs responsive, styled HTML via PrintWriter.
 */
@WebServlet("/StudentResultServlet")
public class StudentResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles HTTP POST requests sent from the HTML form.
     * Note: No instance variables are declared in this class to maintain thread safety.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Set response content type and character encoding
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 2. Read form parameters using local variables (Request-specific thread safety)
        String studentName = request.getParameter("studentName");
        String regNo = request.getParameter("regNo");
        String subject1Str = request.getParameter("subject1");
        String subject2Str = request.getParameter("subject2");
        String subject3Str = request.getParameter("subject3");

        // 3. Perform Server-side Validation
        StringBuilder errorMessages = new StringBuilder();

        // Validate Student Name
        if (studentName == null || studentName.trim().isEmpty()) {
            errorMessages.append("<li>Student Name is required and cannot be empty.</li>");
        } else {
            studentName = studentName.trim();
        }

        // Validate Register Number
        if (regNo == null || regNo.trim().isEmpty()) {
            errorMessages.append("<li>Register Number is required and cannot be empty.</li>");
        } else {
            regNo = regNo.trim().toUpperCase();
        }

        double mark1 = 0;
        double mark2 = 0;
        double mark3 = 0;

        // Validate Subject 1 Mark
        if (subject1Str == null || subject1Str.trim().isEmpty()) {
            errorMessages.append("<li>Subject 1 mark is missing.</li>");
        } else {
            try {
                mark1 = Double.parseDouble(subject1Str.trim());
                if (mark1 < 0 || mark1 > 100) {
                    errorMessages.append("<li>Subject 1 mark (").append(mark1).append(") is out of range. Must be between 0 and 100.</li>");
                }
            } catch (NumberFormatException e) {
                errorMessages.append("<li>Subject 1 mark must be a valid numeric value.</li>");
            }
        }

        // Validate Subject 2 Mark
        if (subject2Str == null || subject2Str.trim().isEmpty()) {
            errorMessages.append("<li>Subject 2 mark is missing.</li>");
        } else {
            try {
                mark2 = Double.parseDouble(subject2Str.trim());
                if (mark2 < 0 || mark2 > 100) {
                    errorMessages.append("<li>Subject 2 mark (").append(mark2).append(") is out of range. Must be between 0 and 100.</li>");
                }
            } catch (NumberFormatException e) {
                errorMessages.append("<li>Subject 2 mark must be a valid numeric value.</li>");
            }
        }

        // Validate Subject 3 Mark
        if (subject3Str == null || subject3Str.trim().isEmpty()) {
            errorMessages.append("<li>Subject 3 mark is missing.</li>");
        } else {
            try {
                mark3 = Double.parseDouble(subject3Str.trim());
                if (mark3 < 0 || mark3 > 100) {
                    errorMessages.append("<li>Subject 3 mark (").append(mark3).append(") is out of range. Must be between 0 and 100.</li>");
                }
            } catch (NumberFormatException e) {
                errorMessages.append("<li>Subject 3 mark must be a valid numeric value.</li>");
            }
        }

        // 4. If validation errors exist, render Error Page
        if (errorMessages.length() > 0) {
            renderErrorPage(out, errorMessages.toString());
            return;
        }

        // 5. Perform Business Logic Calculations (Local Variables)
        double total = mark1 + mark2 + mark3;
        double average = total / 3.0;
        double highestMark = Math.max(mark1, Math.max(mark2, mark3));

        // Pass threshold: Student must score >= 50 in each subject to pass
        boolean isPass = (mark1 >= 50.0 && mark2 >= 50.0 && mark3 >= 50.0);
        String passStatus = isPass ? "PASS" : "FAIL";

        // Determine Performance Grade
        String grade;
        if (!isPass) {
            grade = "F (Fail)";
        } else if (average >= 90) {
            grade = "O (Outstanding)";
        } else if (average >= 80) {
            grade = "A+ (Excellent)";
        } else if (average >= 70) {
            grade = "A (Very Good)";
        } else if (average >= 60) {
            grade = "B+ (Good)";
        } else {
            grade = "B (Average)";
        }

        // 6. Dynamic Result Generation using PrintWriter
        renderResultPage(out, studentName, regNo, mark1, mark2, mark3, total, average, highestMark, passStatus, isPass, grade);
    }

    /**
     * Renders the dynamic Success Result HTML page via PrintWriter.
     */
    private void renderResultPage(PrintWriter out, String studentName, String regNo,
                                  double mark1, double mark2, double mark3,
                                  double total, double average, double highestMark,
                                  String passStatus, boolean isPass, String grade) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Student Result Report - Servlet Generated</title>");
        out.println("    <style>");
        out.println("        :root {");
        out.println("            --primary: #4338ca;");
        out.println("            --success: #16a34a;");
        out.println("            --danger: #dc2626;");
        out.println("            --bg: #f8fafc;");
        out.println("            --card-bg: #ffffff;");
        out.println("            --text: #0f172a;");
        out.println("            --border: #e2e8f0;");
        out.println("        }");
        out.println("        * { box-sizing: border-box; margin: 0; padding: 0; }");
        out.println("        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; background-color: var(--bg); color: var(--text); padding: 40px 20px; line-height: 1.6; }");
        out.println("        .container { max-width: 750px; margin: 0 auto; }");
        out.println("        .result-card { background: var(--card-bg); border-radius: 16px; box-shadow: 0 10px 25px rgba(0,0,0,0.08); border: 1px solid var(--border); overflow: hidden; }");
        out.println("        .header { background: linear-gradient(135deg, #3730a3, #4f46e5); color: #fff; padding: 28px 32px; text-align: center; }");
        out.println("        .header h1 { font-size: 1.8rem; margin-bottom: 6px; }");
        out.println("        .header p { opacity: 0.9; font-size: 0.95rem; }");
        out.println("        .body { padding: 32px; }");
        out.println("        .meta-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 28px; background: #f1f5f9; padding: 18px 20px; border-radius: 12px; }");
        out.println("        .meta-item .label { font-size: 0.8rem; text-transform: uppercase; color: #64748b; font-weight: 700; }");
        out.println("        .meta-item .value { font-size: 1.1rem; font-weight: 700; color: #1e293b; margin-top: 2px; }");
        out.println("        table { width: 100%; border-collapse: collapse; margin-bottom: 28px; }");
        out.println("        th, td { padding: 14px 16px; text-align: left; border-bottom: 1px solid var(--border); }");
        out.println("        th { background-color: #f8fafc; font-size: 0.85rem; text-transform: uppercase; color: #475569; letter-spacing: 0.05em; }");
        out.println("        td { font-size: 0.95rem; }");
        out.println("        .score { font-weight: 700; text-align: right; }");
        out.println("        th.score-th { text-align: right; }");
        out.println("        .summary-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 12px; margin-bottom: 30px; }");
        out.println("        @media(max-width: 600px) { .summary-grid { grid-template-columns: 1fr 1fr; } .meta-grid { grid-template-columns: 1fr; } }");
        out.println("        .stat-box { background: #f8fafc; border: 1.5px solid var(--border); border-radius: 10px; padding: 16px; text-align: center; }");
        out.println("        .stat-box .num { font-size: 1.45rem; font-weight: 800; color: var(--primary); margin-top: 4px; }");
        out.println("        .stat-box .title { font-size: 0.75rem; text-transform: uppercase; font-weight: 700; color: #64748b; }");
        out.println("        .status-badge { display: inline-block; padding: 6px 16px; border-radius: 9999px; font-weight: 800; font-size: 0.9rem; text-transform: uppercase; }");
        out.println("        .status-pass { background-color: #dcfce7; color: #15803d; border: 1px solid #86efac; }");
        out.println("        .status-fail { background-color: #fee2e2; color: #b91c1c; border: 1px solid #fca5a5; }");
        out.println("        .action-bar { text-align: center; border-top: 1px solid var(--border); padding-top: 24px; }");
        out.println("        .btn { display: inline-block; background-color: var(--primary); color: #ffffff; text-decoration: none; padding: 12px 28px; border-radius: 8px; font-weight: 600; transition: 0.2s; }");
        out.println("        .btn:hover { background-color: #3730a3; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='container'>");
        out.println("        <div class='result-card'>");
        out.println("            <div class='header'>");
        out.println("                <h1>Department Student Result Portal</h1>");
        out.println("                <p>Calculated and Generated by StudentResultServlet (Apache Tomcat)</p>");
        out.println("            </div>");
        out.println("            <div class='body'>");
        out.println("                <div class='meta-grid'>");
        out.println("                    <div class='meta-item'>");
        out.println("                        <div class='label'>Student Name</div>");
        out.println("                        <div class='value'>" + escapeHtml(studentName) + "</div>");
        out.println("                    </div>");
        out.println("                    <div class='meta-item'>");
        out.println("                        <div class='label'>Register Number</div>");
        out.println("                        <div class='value'>" + escapeHtml(regNo) + "</div>");
        out.println("                    </div>");
        out.println("                </div>");
        out.println("                <table>");
        out.println("                    <thead>");
        out.println("                        <tr>");
        out.println("                            <th>#</th>");
        out.println("                            <th>Subject Description</th>");
        out.println("                            <th class='score-th'>Marks Scored (Max: 100)</th>");
        out.println("                            <th class='score-th'>Subject Status</th>");
        out.println("                        </tr>");
        out.println("                    </thead>");
        out.println("                    <tbody>");
        out.println("                        <tr>");
        out.println("                            <td>01</td>");
        out.println("                            <td>Subject 1</td>");
        out.println("                            <td class='score'>" + String.format("%.2f", mark1) + "</td>");
        out.println("                            <td class='score'>" + (mark1 >= 50 ? "<span style='color:#16a34a; font-weight:bold;'>Pass</span>" : "<span style='color:#dc2626; font-weight:bold;'>Fail</span>") + "</td>");
        out.println("                        </tr>");
        out.println("                        <tr>");
        out.println("                            <td>02</td>");
        out.println("                            <td>Subject 2</td>");
        out.println("                            <td class='score'>" + String.format("%.2f", mark2) + "</td>");
        out.println("                            <td class='score'>" + (mark2 >= 50 ? "<span style='color:#16a34a; font-weight:bold;'>Pass</span>" : "<span style='color:#dc2626; font-weight:bold;'>Fail</span>") + "</td>");
        out.println("                        </tr>");
        out.println("                        <tr>");
        out.println("                            <td>03</td>");
        out.println("                            <td>Subject 3</td>");
        out.println("                            <td class='score'>" + String.format("%.2f", mark3) + "</td>");
        out.println("                            <td class='score'>" + (mark3 >= 50 ? "<span style='color:#16a34a; font-weight:bold;'>Pass</span>" : "<span style='color:#dc2626; font-weight:bold;'>Fail</span>") + "</td>");
        out.println("                        </tr>");
        out.println("                    </tbody>");
        out.println("                </table>");
        out.println("                <div class='summary-grid'>");
        out.println("                    <div class='stat-box'>");
        out.println("                        <div class='title'>Total Marks</div>");
        out.println("                        <div class='num'>" + String.format("%.2f", total) + " <span style='font-size:0.8rem; color:#64748b;'>/ 300</span></div>");
        out.println("                    </div>");
        out.println("                    <div class='stat-box'>");
        out.println("                        <div class='title'>Average</div>");
        out.println("                        <div class='num'>" + String.format("%.2f", average) + "%</div>");
        out.println("                    </div>");
        out.println("                    <div class='stat-box'>");
        out.println("                        <div class='title'>Highest Mark</div>");
        out.println("                        <div class='num' style='color:#059669;'>" + String.format("%.2f", highestMark) + "</div>");
        out.println("                    </div>");
        out.println("                    <div class='stat-box'>");
        out.println("                        <div class='title'>Result Status</div>");
        out.println("                        <div style='margin-top: 8px;'><span class='status-badge " + (isPass ? "status-pass" : "status-fail") + "'>" + passStatus + "</span></div>");
        out.println("                    </div>");
        out.println("                </div>");
        out.println("                <div style='text-align: center; margin-bottom: 25px;'>");
        out.println("                    <strong>Final Grade:</strong> <span style='font-size:1.1rem; color:#4338ca; font-weight:bold;'>" + grade + "</span>");
        out.println("                </div>");
        out.println("                <div class='action-bar'>");
        out.println("                    <a href='index.html' class='btn'>&larr; Calculate Another Result</a>");
        out.println("                </div>");
        out.println("            </div>");
        out.println("        </div>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Renders a styled validation error message page.
     */
    private void renderErrorPage(PrintWriter out, String errorsListHtml) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Validation Error - Student Result Processing</title>");
        out.println("    <style>");
        out.println("        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background-color: #f8fafc; color: #0f172a; padding: 50px 20px; }");
        out.println("        .error-card { max-width: 600px; margin: 0 auto; background: #ffffff; border-radius: 16px; border: 1.5px solid #fecaca; box-shadow: 0 10px 25px rgba(220, 38, 38, 0.08); overflow: hidden; }");
        out.println("        .error-header { background-color: #fee2e2; color: #991b1b; padding: 20px 24px; font-size: 1.25rem; font-weight: bold; display: flex; align-items: center; gap: 10px; }");
        out.println("        .error-body { padding: 24px; }");
        out.println("        .error-list { margin-left: 20px; color: #b91c1c; line-height: 1.8; margin-bottom: 24px; font-size: 0.95rem; }");
        out.println("        .btn-back { display: inline-block; background-color: #4338ca; color: white; text-decoration: none; padding: 10px 22px; border-radius: 8px; font-weight: 600; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='error-card'>");
        out.println("        <div class='error-header'>");
        out.println("            <span>&#9888; Form Validation Errors</span>");
        out.println("        </div>");
        out.println("        <div class='error-body'>");
        out.println("            <p style='margin-bottom: 12px; color: #475569;'>Please correct the following errors before submitting:</p>");
        out.println("            <ul class='error-list'>");
        out.println(errorsListHtml);
        out.println("            </ul>");
        out.println("            <div style='text-align: right;'>");
        out.println("                <a href='javascript:history.back()' class='btn-back'>&larr; Go Back &amp; Correct</a>");
        out.println("            </div>");
        out.println("        </div>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Basic HTML sanitization helper
     */
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#x27;");
    }

    /**
     * Redirect GET requests to form
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}
