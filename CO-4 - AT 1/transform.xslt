<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:output method="html" encoding="UTF-8" indent="yes"/>

  <xsl:template match="/">
    <html lang="en">
      <head>
        <title>University Course Enrollment Analysis</title>
        <style>
          body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background-color: #f8f9fa;
          }
          h2 {
            color: #1a365d;
            text-align: center;
          }
          table {
            width: 100%;
            border-collapse: collapse;
            background: #ffffff;
            margin-top: 20px;
          }
          th, td {
            padding: 10px 14px;
            text-align: left;
            border: 1px solid #cbd5e1;
          }
          th {
            background-color: #2563eb;
            color: white;
          }
          tr:nth-child(even) {
            background-color: #f1f5f9;
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
              <th>Students</th>
              <th>Credits</th>
              <th>Type</th>
            </tr>
          </thead>
          <tbody>
            <!-- Requirement a & d: Display only courses having more than 40 students using XPath condition -->
            <xsl:for-each select="courses/course[students &gt; 40]">
              <!-- Requirement b: Sort in descending order of student enrollment -->
              <xsl:sort select="students" data-type="number" order="descending"/>
              <tr>
                <td><xsl:value-of select="code"/></td>
                <td><xsl:value-of select="name"/></td>
                <td><xsl:value-of select="faculty"/></td>
                <td><xsl:value-of select="students"/></td>
                <td><xsl:value-of select="credits"/></td>
                <td><xsl:value-of select="type"/></td>
              </tr>
            </xsl:for-each>
          </tbody>
        </table>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
