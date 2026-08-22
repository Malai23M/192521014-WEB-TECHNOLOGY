# WEB TECHNOLOGY — UNIT IV: REPRESENTING WEB DATA
## ASSESSMENT I: DATA INTERPRETATION — COMPLETE ANSWER KEY

- **Subject:** Web Technology (Unit IV - Representing Web Data)
- **Assessment:** Assessment I: Data Interpretation
- **Scenario:** University Course Enrollment Analysis
- **Maximum Marks:** 30
- **Duration:** 60 Minutes

---

## Given XML Dataset Reference

```xml
<?xml version="1.0" encoding="UTF-8"?>
<courses>
  <course id="C101">
    <code>WEB301</code>
    <name>Web Technology</name>
    <faculty>Dr. Arun</faculty>
    <students>58</students>
    <credits>4</credits>
    <type>Theory</type>
  </course>
  <course id="C102">
    <code>AI302</code>
    <name>Artificial Intelligence</name>
    <faculty>Dr. Meena</faculty>
    <students>72</students>
    <credits>4</credits>
    <type>Theory</type>
  </course>
  <course id="C103">
    <code>WEB303</code>
    <name>Web Technology Laboratory</name>
    <faculty>Dr. Ravi</faculty>
    <students>36</students>
    <credits>2</credits>
    <type>Practical</type>
  </course>
  <course id="C104">
    <code>ML304</code>
    <name>Machine Learning</name>
    <faculty>Dr. Priya</faculty>
    <students>64</students>
    <credits>4</credits>
    <type>Theory</type>
  </course>
  <course id="C105">
    <code>DB305</code>
    <name>Database Systems</name>
    <faculty>Dr. Kumar</faculty>
    <students>42</students>
    <credits>3</credits>
    <type>Theory</type>
  </course>
</courses>
```

---

## Question 1: Interpret the XML Structure (5 Marks)

### a. Identify the root element.
- **Answer:** `<courses>`
- **Explanation:** It is the top-level parent element that encloses all other elements in the XML document.

### b. Identify the repeating record element.
- **Answer:** `<course>`
- **Explanation:** The `<course>` element repeats for each course entity defined inside `<courses>`.

### c. Identify the attribute used to uniquely identify each course.
- **Answer:** `id` (e.g., `id="C101"`, `id="C102"`, etc., within the `<course>` element).

### d. Identify the elements that represent numeric information.
- **Answer:** `<students>` and `<credits>`
- **Explanation:** `<students>` stores numeric enrollment count (e.g., 58, 72) and `<credits>` stores numeric course credits (e.g., 4, 2, 3).

### e. State whether the XML document is structurally well-formed and justify your answer.
- **Answer:** **Yes, the XML document is structurally well-formed.**
- **Justification:**
  1. **Single Root Element:** It contains exactly one root element (`<courses>`) that wraps all other elements.
  2. **Proper Nesting:** All opening tags have matching closing tags properly nested with no overlapping (e.g., `<course>` ... `</course>`).
  3. **Case Sensitivity:** Element tag names are case-consistent between opening and closing tags.
  4. **Quoted Attribute Values:** All attribute values are enclosed in valid quotation marks (e.g., `id="C101"`).
  5. **Valid XML Declaration:** It begins with a standard XML declaration (`<?xml version="1.0" encoding="UTF-8"?>`).

---

## Question 2: Apply XPath for Data Selection (10 Marks)

| Q# | Data Selection Requirement | XPath Expression | Extracted Value / Matching Nodes |
|---|---|---|---|
| **a** | All course records | `/courses/course` *(or `//course`)* | Returns all 5 `<course>` nodes (`C101` to `C105`) |
| **b** | Names of all courses | `/courses/course/name` *(or `//course/name`)* | `Web Technology`, `Artificial Intelligence`, `Web Technology Laboratory`, `Machine Learning`, `Database Systems` |
| **c** | Courses having more than 50 students | `/courses/course[students > 50]` | `<course id="C101">`, `<course id="C102">`, `<course id="C104">` |
| **d** | Courses carrying 4 credits | `/courses/course[credits = 4]` | `<course id="C101">`, `<course id="C102">`, `<course id="C104">` |
| **e** | Courses whose type is Theory | `/courses/course[type = 'Theory']` | `<course id="C101">`, `<course id="C102">`, `<course id="C104">`, `<course id="C105">` |
| **f** | Names of Theory courses having more than 50 students | `/courses/course[type = 'Theory' and students > 50]/name` | `Web Technology`, `Artificial Intelligence`, `Machine Learning` |
| **g** | Faculty members handling courses with at least 4 credits | `/courses/course[credits >= 4]/faculty` | `Dr. Arun`, `Dr. Meena`, `Dr. Priya` |
| **h** | The course whose id is C104 | `/courses/course[@id = 'C104']` | Node for `Machine Learning` (`ML304`) |
| **i** | The first course available in the XML document | `/courses/course[1]` *(or `(//course)[1]`)* | Node for `Web Technology` (`C101`) |
| **j** | The last course available in the XML document | `/courses/course[last()]` *(or `(//course)[last()]`)* | Node for `Database Systems` (`C105`) |

---

## Question 3: Apply XSLT for Data Presentation (10 Marks)

### Requirements Checklist:
- [x] HTML Table with columns: **Course Code**, **Course Name**, **Faculty**, **Students**, **Credits**, **Type**
- [x] Display only courses having **more than 40 students** (`students > 40`)
- [x] Sort displayed courses in **descending order of student enrollment**
- [x] Display heading: **"High Enrollment Courses"**
- [x] Use XPath condition inside XSLT (`select="courses/course[students &gt; 40]"`)
- [x] Produce valid HTML output

### XSLT Stylesheet (`transform.xslt`):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:output method="html" encoding="UTF-8" indent="yes"/>

  <xsl:template match="/">
    <html lang="en">
      <head>
        <title>University Course Enrollment Analysis</title>
        <style>
          body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 30px;
            background-color: #f8f9fa;
          }
          h2 {
            color: #1a365d;
            text-align: center;
            margin-bottom: 20px;
          }
          table {
            width: 100%;
            border-collapse: collapse;
            background: #ffffff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
          }
          th, td {
            padding: 12px 16px;
            text-align: left;
            border-bottom: 1px solid #e2e8f0;
          }
          th {
            background-color: #2b6cb0;
            color: #ffffff;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 13px;
            letter-spacing: 0.5px;
          }
          tr:nth-child(even) {
            background-color: #f7fafc;
          }
          tr:hover {
            background-color: #edf2f7;
          }
          .number-cell {
            text-align: center;
          }
        </style>
      </head>
      <body>
        <h2>High Enrollment Courses</h2>
        <table>
          <thead>
            <tr>
              <th>Course Code</th>
              <th>Course Name</th>
              <th>Faculty</th>
              <th class="number-cell">Students</th>
              <th class="number-cell">Credits</th>
              <th>Type</th>
            </tr>
          </thead>
          <tbody>
            <!-- Filter: Only courses with students > 40 -->
            <xsl:for-each select="courses/course[students &gt; 40]">
              <!-- Sort: Descending order by number of students -->
              <xsl:sort select="students" data-type="number" order="descending"/>
              <tr>
                <td><xsl:value-of select="code"/></td>
                <td><xsl:value-of select="name"/></td>
                <td><xsl:value-of select="faculty"/></td>
                <td class="number-cell"><xsl:value-of select="students"/></td>
                <td class="number-cell"><xsl:value-of select="credits"/></td>
                <td><xsl:value-of select="type"/></td>
              </tr>
            </xsl:for-each>
          </tbody>
        </table>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
```

### Resulting Transformed HTML Table Output:

| Course Code | Course Name | Faculty | Students | Credits | Type |
|---|---|---|:---:|:---:|---|
| **AI302** | Artificial Intelligence | Dr. Meena | **72** | 4 | Theory |
| **ML304** | Machine Learning | Dr. Priya | **64** | 4 | Theory |
| **WEB301** | Web Technology | Dr. Arun | **58** | 4 | Theory |
| **DB305** | Database Systems | Dr. Kumar | **42** | 3 | Theory |

*(Note: Course `WEB303` - Web Technology Laboratory with 36 students is filtered out because \(36 \le 40\).)*

---

## Question 4: Interpret the Extracted Data (5 Marks)

### a. Identify the course with the highest enrollment.
- **Answer:** **Artificial Intelligence** (`AI302` / Course ID: `C102`)
- **Enrollment Details:** **72 students** (handled by Dr. Meena).

### b. Identify the course with the lowest enrollment.
- **Answer:** **Web Technology Laboratory** (`WEB303` / Course ID: `C103`)
- **Enrollment Details:** **36 students** (handled by Dr. Ravi).

### c. Determine the number of Theory courses.
- **Answer:** **4 Theory courses**
- **Course List:**
  1. `WEB301` — Web Technology
  2. `AI302` — Artificial Intelligence
  3. `ML304` — Machine Learning
  4. `DB305` — Database Systems  
  *(Only `WEB303` is Practical, making 4 out of 5 courses Theory).*

### d. Identify all courses having exactly 4 credits.
- **Answer:**
  1. **Web Technology** (`WEB301` / `C101`) — 4 credits
  2. **Artificial Intelligence** (`AI302` / `C102`) — 4 credits
  3. **Machine Learning** (`ML304` / `C104`) — 4 credits

### e. If an additional teaching assistant is assigned to every course with more than 60 students, identify the courses that require additional support.
- **Answer:** The courses that require additional support (Teaching Assistant) are:
  1. **Artificial Intelligence (`AI302` / `C102`)** — **72 students** (\(72 > 60\))
  2. **Machine Learning (`ML304` / `C104`)** — **64 students** (\(64 > 60\))
- **Summary:** Only these **2 courses** have enrollments strictly greater than 60 students. (`WEB301` has 58 students, `DB305` has 42 students, and `WEB303` has 36 students, so none of those qualify).

---

## Summary of Total Marks Distribution

| Criterion | Questions | Marks | Status |
|---|---|:---:|:---:|
| Correct interpretation of XML structure | Q1 (a - e) | 5 / 5 | Completed |
| Correct XPath expressions | Q2 (a - j) | 10 / 10 | Completed |
| Correct XSLT transformation and sorting | Q3 (a - e) | 10 / 10 | Completed |
| Correct interpretation of results | Q4 (a - e) | 5 / 5 | Completed |
| **Total** | | **30 / 30** | **100% Complete** |
