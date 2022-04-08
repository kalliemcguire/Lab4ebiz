package domain;

import database.EmployeeDA;
import exceptions.RecordNotFoundException;
import java.io.Serializable;

import java.util.Date;

import java.util.ArrayList;

public abstract class Employee implements Serializable{
    public static final int HOURLY = 1;
    public static final int SALARY = 2;
    private int employeeID;
    private int employeeType;
    private String firstName;
    private String lastName;
    private long SSN;
    private String userID;
    private String password;
    
    public Employee(){}
    
    public void add() {
        EmployeeDA.add(this);
    }
    
    public double calculateGrossPay(Date date) {
        return 0.0;
    }
    
    public static Employee find(int ID) throws RecordNotFoundException{
        return EmployeeDA.find(ID);
    }
    
    public static Employee findByUserID(String userID)throws RecordNotFoundException{
        return EmployeeDA.findByUserID(userID);
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public int getEmployeeType() {
        return employeeType;
    }
    
    public static ArrayList<Employee> getEmployees() {
        return EmployeeDA.getEmployees();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public long getSSN() {
        return SSN;
    }

    public String getUserID() {
        return userID;
    }
    
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setEmployeeType(int employeeType) {
        this.employeeType = employeeType;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setHourlyRate(double hR) {}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setOvertimeRate(double or) {}

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setSalary(double s) {}

    public void setSSN(long SSN) {
        this.SSN = SSN;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public String toString(){
        return employeeID + "  " + lastName + ", " + firstName + "  " + SSN ;
    }
}