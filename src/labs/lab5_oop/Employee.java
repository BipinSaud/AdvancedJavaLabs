package labs.lab5_oop;

public class Employee {
    private int id;
    private String name;
    protected double salary; // protected so subclasses can access

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    // method to be overridden
    public void calculateSalary() {
        salary = 0;
    }

    public void displayDetails() {
        System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
    }
}
