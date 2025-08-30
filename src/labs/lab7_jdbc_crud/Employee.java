package labs.lab7_jdbc_crud;

public class Employee {
    private String empId;
    private String empName;
    private String deptId;
    private double salary;

    public Employee(String empId, String empName, String deptId, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.deptId = deptId;
        this.salary = salary;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getDeptId() {
        return deptId;
    }

    public double getSalary() {
        return salary;
    }
}
