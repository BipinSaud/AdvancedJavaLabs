package labs.lab5_oop;

public class FullTimeEmployee extends Employee {
    private double basicPay;
    private double bonus;

    public FullTimeEmployee(int id, String name, double basicPay, double bonus) {
        super(id, name);
        this.basicPay = basicPay;
        this.bonus = bonus;
    }

    @Override
    public void calculateSalary() {
        salary = basicPay + bonus;
    }
}
