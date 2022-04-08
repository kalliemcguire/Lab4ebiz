package domain;

import database.PayrollDA;
import exceptions.RecordNotFoundException;
import java.io.Serializable;
import java.text.DateFormat;

import java.text.NumberFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Payroll implements Serializable{
    private Date date;
    private int employeeID;
    private double grossPay;
    private double totalDeductions;
    private double netPay;
    
    public Payroll(){}
    
    public void add() {
        PayrollDA.add(this);
    }
    
    public static void calculatePayroll(Date date) {
        ArrayList<Employee> employees = Employee.getEmployees();
        ArrayList<WithholdingType>withholdingTypes = WithholdingType.getWithholdingTypes(); 
        
        Payroll payroll;
        Employee emp;
        WithholdingType withholding;
        double grossPay;
        double totalDeductions;
        double netPay;
        
        for(int i = 0; i < employees.size(); i++) {
            emp = employees.get(i);
            System.out.println(emp);
            grossPay = emp.calculateGrossPay(date);
            totalDeductions = 0;
            for (int n = 0; n < withholdingTypes.size(); n++) {
                withholding = withholdingTypes.get(n);
                totalDeductions += grossPay * withholding.getRate() / 100 + withholding.getAmount();
            }
            netPay = grossPay - totalDeductions;
                        
            payroll = new Payroll();
            payroll.setDate(date);
            payroll.setEmployeeID(emp.getEmployeeID());
            payroll.setGrossPay(grossPay);
            payroll.setTotalDeductions(totalDeductions);
            payroll.setNetPay(netPay);
            payroll.add();
        }
    }

    public Date getDate() {
        return date;
    }
    
    public String getDateFormatted(){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        return dateFormat.format(date);
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public double getGrossPay() {
        return grossPay;
    }
    
    public String getGrossPayFormatted(){
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(grossPay);
    }

    public double getNetPay() {
        return netPay;
    }
    
    public String getNetPayFormatted(){
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(netPay);
    }
    
    public static ArrayList<Payroll> getPayrollRecords() {
        return PayrollDA.getPayrollRecords();
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }
    
    public String getTotalDeductionsFormatted(){
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(totalDeductions);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }
    
    public String toString(){
        Employee emp = null;
        try {
            emp = Employee.find(employeeID);
        } catch (RecordNotFoundException ex) {
            Logger.getLogger(Payroll.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getDateFormatted() + "  " + employeeID + "  " + emp.getLastName() + ",  "+ emp.getFirstName() + "  " + getGrossPayFormatted() + "  " + getTotalDeductionsFormatted() + "  " + getNetPayFormatted();
    }
}