<%@ page import="java.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    // ---- JDBC config (MySQL local) ----
    String dbName  = "studentdb";
    String jdbcServerUrl = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    String jdbcUrl       = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    String jdbcUser = "root";      // per your request
    String jdbcPass = "root";      // per your request

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Ensure database exists (requires privileges; works with root/root)
        try (Connection srv = DriverManager.getConnection(jdbcServerUrl, jdbcUser, jdbcPass);
             Statement st = srv.createStatement()) {
            st.execute("CREATE DATABASE IF NOT EXISTS " + dbName + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
        }

        // Ensure table exists
        try (Connection c = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
             Statement st = c.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS students (" +
                       "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                       "roll_no VARCHAR(50) NOT NULL, " +
                       "name VARCHAR(200) NOT NULL, " +
                       "email VARCHAR(200) NOT NULL, " +
                       "program VARCHAR(200) NOT NULL" +
                       ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");
        }
    } catch (Exception e) {
        out.println("<pre style='color:red'>Init error: " + e.getMessage() + "</pre>");
    }

    // ---- Handle actions ----
    String action = request.getParameter("action");
    String msg = null, err = null;
    try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass)) {
        if ("add".equalsIgnoreCase(action)) {
            String rollNo = request.getParameter("roll_no");
            String name   = request.getParameter("name");
            String email  = request.getParameter("email");
            String prog   = request.getParameter("program");
            if (rollNo!=null && name!=null && email!=null && prog!=null
                && !rollNo.isBlank() && !name.isBlank() && !email.isBlank() && !prog.isBlank()) {
                try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO students(roll_no, name, email, program) VALUES (?,?,?,?)")) {
                    ps.setString(1, rollNo.trim());
                    ps.setString(2, name.trim());
                    ps.setString(3, email.trim());
                    ps.setString(4, prog.trim());
                    ps.executeUpdate();
                    msg = "Student added.";
                }
            } else err = "All fields are required to add.";
        } else if ("update".equalsIgnoreCase(action)) {
            String id     = request.getParameter("id");
            String rollNo = request.getParameter("roll_no");
            String name   = request.getParameter("name");
            String email  = request.getParameter("email");
            String prog   = request.getParameter("program");
            if (id!=null && rollNo!=null && name!=null && email!=null && prog!=null
                && !id.isBlank() && !rollNo.isBlank() && !name.isBlank() && !email.isBlank() && !prog.isBlank()) {
                try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE students SET roll_no=?, name=?, email=?, program=? WHERE id=?")) {
                    ps.setString(1, rollNo.trim());
                    ps.setString(2, name.trim());
                    ps.setString(3, email.trim());
                    ps.setString(4, prog.trim());
                    ps.setLong(5, Long.parseLong(id));
                    int n = ps.executeUpdate();
                    msg = (n > 0) ? "Student updated." : "No row updated.";
                }
            } else err = "All fields are required to update.";
        } else if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (id!=null && !id.isBlank()) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE id=?")) {
                    ps.setLong(1, Long.parseLong(id));
                    int n = ps.executeUpdate();
                    msg = (n > 0) ? "Student deleted." : "No row deleted.";
                }
            } else err = "Missing id to delete.";
        }
    } catch (Exception ex) { err = ex.getMessage(); }

    // ---- Fetch row for edit (if any) ----
    String editId = request.getParameter("editId");
    String eId=null, eRoll=null, eName=null, eEmail=null, eProg=null;
    if (editId!=null && !editId.isBlank()) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE id=?")) {
            ps.setLong(1, Long.parseLong(editId));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    eId   = String.valueOf(rs.getLong("id"));
                    eRoll = rs.getString("roll_no");
                    eName = rs.getString("name");
                    eEmail= rs.getString("email");
                    eProg = rs.getString("program");
                }
            }
        } catch (Exception ex) { err = (err==null?"":err+" | ")+ex.getMessage(); }
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Details CRUD (MySQL)</title>
<style>
 body{font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif;margin:2rem}
 form{margin-bottom:1.5rem;padding:1rem;border:1px solid #ddd;border-radius:12px;max-width:720px}
 label{display:inline-block;width:90px}
 input[type=text],input[type=email]{width:240px;padding:6px;margin:6px 0}
 table{border-collapse:collapse;width:100%;max-width:900px}
 th,td{border:1px solid #ddd;padding:8px} th{background:#f3f3f3}
 .msg{color:#0a7a00;margin-bottom:1rem}.err{color:#b00020;margin-bottom:1rem;white-space:pre-wrap}
 .actions a{margin-right:8px}.buttons{margin-top:8px}
</style>
</head>
<body>
<h1>Student Details CRUD (MySQL)</h1>
<% if (msg != null) { %><div class="msg"><%= msg %></div><% } %>
<% if (err != null) { %><div class="err"><%= err %></div><% } %>
<%
 boolean editing = (eId != null);
%>
<form method="post">
  <h3><%= editing ? "Edit Student" : "Add Student" %></h3>
  <% if (editing) { %>
    <input type="hidden" name="id" value="<%= eId %>"/>
  <% } %>
  <div>
    <label>Roll No</label>
    <input type="text" name="roll_no" value="<%= editing ? eRoll : "" %>" required />
  </div>
  <div>
    <label>Name</label>
    <input type="text" name="name" value="<%= editing ? eName : "" %>" required />
  </div>
  <div>
    <label>Email</label>
    <input type="email" name="email" value="<%= editing ? eEmail : "" %>" required />
  </div>
  <div>
    <label>Program</label>
    <input type="text" name="program" value="<%= editing ? eProg : "" %>" required />
  </div>
  <div class="buttons">
    <% if (editing) { %>
      <button type="submit" name="action" value="update">Update</button>
      <a href="<%= request.getContextPath() %>/">Cancel</a>
    <% } else { %>
      <button type="submit" name="action" value="add">Add</button>
    <% } %>
  </div>
</form>

<h3>All Students</h3>
<table>
  <tr>
    <th>Id</th><th>Roll No</th><th>Name</th><th>Email</th><th>Program</th><th>Actions</th>
  </tr>
  <%
    try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
         PreparedStatement ps = conn.prepareStatement("SELECT * FROM students ORDER BY id");
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        long id = rs.getLong("id");
  %>
    <tr>
      <td><%= id %></td>
      <td><%= rs.getString("roll_no") %></td>
      <td><%= rs.getString("name") %></td>
      <td><%= rs.getString("email") %></td>
      <td><%= rs.getString("program") %></td>
      <td class="actions">
        <a href="?editId=<%= id %>">Edit</a>
        <a href="?action=delete&id=<%= id %>" onclick="return confirm('Delete student #<%= id %>?');">Delete</a>
      </td>
    </tr>
  <%
      }
    } catch (Exception ex) {
      out.println("<tr><td colspan='6' class='err'>"+ ex.getMessage() +"</td></tr>");
    }
  %>
</table>
</body>
</html>
