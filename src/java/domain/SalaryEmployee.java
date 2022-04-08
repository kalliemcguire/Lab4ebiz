package domain;

import java.util.Date;

public class SalaryEmployee extends Employee{
    private double salary;
    
    public double calculateGrossPay(Date date) {
        return salary/52.0;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double s) {
        this.salary = s;
    }
    
    public String toString(){
        return super.toString() + "  " + salary;
    }
}