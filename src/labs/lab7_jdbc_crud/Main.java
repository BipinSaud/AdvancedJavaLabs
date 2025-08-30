package labs.lab7_jdbc_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {
        try {
            EmployeeCRUD crud = new EmployeeCRUD();

            // --- Insert Departments first ---
            Connection conn = crud.getConnection();

            String deptSQL = "INSERT IGNORE INTO Department(dept_id, dept_name) VALUES (?, ?)";
            PreparedStatement deptStmt = conn.prepareStatement(deptSQL);

            deptStmt.setString(1, "D01");
            deptStmt.setString(2, "HR");
            deptStmt.executeUpdate();

            deptStmt.setString(1, "D02");
            deptStmt.setString(2, "Finance");
            deptStmt.executeUpdate();

            System.out.println("Departments inserted successfully!");

            // --- Insert Employees ---
            Employee emp1 = new Employee("E101", "Alice", "D01", 50000);
            Employee emp2 = new Employee("E102", "Bob", "D02", 40000);
            crud.insertEmployee(emp1);
            crud.insertEmployee(emp2);

            // Update salary
            crud.updateSalary("E101", 60000);

            // View all employees
            crud.viewEmployees();

            // Fetch employees by department
            crud.getEmployeesByDept("D01");

            // Delete an employee
            crud.deleteEmployee("E102");

            // View again after deletion
            crud.viewEmployees();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
