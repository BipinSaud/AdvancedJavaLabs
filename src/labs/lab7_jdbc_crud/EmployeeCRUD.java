package labs.lab7_jdbc_crud;

import java.sql.*;

public class EmployeeCRUD {

    private Connection conn;

    // Constructor – establish connection
    public EmployeeCRUD() {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/companydb";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the connection
    public Connection getConnection() {
        return conn;
    }

    // Insert Employee
    public void insertEmployee(Employee emp) {
        try {
            String sql = "INSERT INTO Employee(emp_id, emp_name, dept_id, salary) VALUES(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.getEmpId());
            stmt.setString(2, emp.getEmpName());
            stmt.setString(3, emp.getDeptId());
            stmt.setDouble(4, emp.getSalary());
            stmt.executeUpdate();
            System.out.println("Employee inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update Employee Salary
    public void updateSalary(String empId, double newSalary) {
        try {
            String sql = "UPDATE Employee SET salary=? WHERE emp_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newSalary);
            stmt.setString(2, empId);
            stmt.executeUpdate();
            System.out.println("Salary updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Employee
    public void deleteEmployee(String empId) {
        try {
            String sql = "DELETE FROM Employee WHERE emp_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, empId);
            stmt.executeUpdate();
            System.out.println("Employee deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View all Employees
    public void viewEmployees() {
        try {
            String sql = "SELECT * FROM Employee";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Employee Records:");
            while (rs.next()) {
                System.out.println(rs.getString("emp_id") + " | " +
                        rs.getString("emp_name") + " | " +
                        rs.getString("dept_id") + " | " +
                        rs.getDouble("salary"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch employees by department
    public void getEmployeesByDept(String deptId) {
        try {
            String sql = "SELECT * FROM Employee WHERE dept_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, deptId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Employees in Department " + deptId + ":");
            while (rs.next()) {
                System.out.println(rs.getString("emp_id") + " | " +
                        rs.getString("emp_name") + " | " +
                        rs.getDouble("salary"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
