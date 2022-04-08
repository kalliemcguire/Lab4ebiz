package database;

import domain.Payroll;

import java.util.ArrayList;

public class PayrollDA {
    private static ArrayList<Payroll> payrollRecords = new ArrayList<Payroll>(5);
    
    public static void add(Payroll p) {
        payrollRecords.add(p);
    }

    public static ArrayList<Payroll> getPayrollRecords() {
        return payrollRecords;
    }
}