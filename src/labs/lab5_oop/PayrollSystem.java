package labs.lab5_oop;

public class PayrollSystem {
    public static void main(String[] args) {
        // Full-time employee
        FullTimeEmployee emp1 = new FullTimeEmployee(101, "Alice", 30000, 5000);
        emp1.calculateSalary();
        emp1.displayDetails();

        // Part-time employee
        PartTimeEmployee emp2 = new PartTimeEmployee(102, "Bob", 80, 200);
        emp2.calculateSalary();
        emp2.displayDetails();

        // Polymorphism (base class reference holding subclass object)
        Employee emp3 = new FullTimeEmployee(103, "Charlie", 25000, 3000);
        emp3.calculateSalary();
        emp3.displayDetails();
    }
}
