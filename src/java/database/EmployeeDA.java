package database;

import domain.Employee;
import domain.HourlyEmployee;
import domain.SalaryEmployee;
import exceptions.RecordNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

public class EmployeeDA {
    
    private static ArrayList<Employee> employees = new ArrayList<Employee>(5);
    
    public static void add(Employee emp) {
        employees.add(emp);
    }
    
    public static Employee find(int ID) throws RecordNotFoundException{
        Employee emp = null;
        int empID;
        int empType;
        String empFN;
        String empLN;
        long SSN;
        String user;
        String pw;
        
        String sqlString = "Select Employee_ID, Employee_Type, First_Name, Last_Name, SSN, User_ID, Password"
                + " From Employee"
                + " Where Employee_ID = '" + ID + "'";
        System.out.println("sqlString = " + sqlString);
        
        Connection connection = PayrollSystemDA.getConnection();
        System.out.println(connection);
        Statement statement;
        
        try {
            statement = connection.createStatement();
            ResultSet rs;            
            rs = statement.executeQuery(sqlString);
            rs.next();
            
            empID = rs.getInt(1);
            empType = rs.getInt(2);
            empFN = rs.getString(3);
            empLN = rs.getString(4);
            SSN = rs.getLong(5);
            user = rs.getString(6);
            pw = rs.getString(7);
            
            if(empID == ID && empType == Employee.HOURLY)
                emp = new SalaryEmployee();
            else if(empID == ID && empType == Employee.SALARY)
                emp = new HourlyEmployee();
            
            emp.setEmployeeID(empID);
            emp.setEmployeeType(empType);
            emp.setFirstName(empFN);
            emp.setLastName(empLN);
            emp.setSSN(SSN);
            emp.setUserID(user);
            emp.setPassword(pw);
            
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
            RecordNotFoundException ex = new RecordNotFoundException("Employee " + ID + " not found.");
            throw ex;
        }
        
        return emp;
    }
    
    
    //todo: set up private static methods for hourly and salary tables
    public static Employee findByUserID(String userID) throws RecordNotFoundException{
        Employee employee = null;
        int empID;
        int empType;
        String empFirstName;
        String empLastName;
        long social;
        String u_ID;
        String pass;
        
        String sqlString = "Select Employee_ID, Employee_Type, First_Name, Last_Name, SSN, User_ID, Password"
                + " From Employee"
                + " Where User_ID = '" + userID + "'";
        System.out.println("sqlString = " + sqlString);
        
        Connection connection = PayrollSystemDA.getConnection();
        System.out.println(connection);
        Statement statement;
        
        try {
            statement = connection.createStatement();
            ResultSet rs;            
            rs = statement.executeQuery(sqlString);
            rs.next();
            
            empID = rs.getInt(1);
            empType = rs.getInt(2);
            empFirstName = rs.getString(3);
            empLastName = rs.getString(4);
            social = rs.getLong(5);
            u_ID = rs.getString(6);
            pass = rs.getString(7);
            
            if(empType == Employee.HOURLY)
                employee = new HourlyEmployee();
            else
                employee = new SalaryEmployee();
            
            employee.setEmployeeID(empID);
            employee.setEmployeeType(empType);
            employee.setFirstName(empFirstName);
            employee.setLastName(empLastName);
            employee.setSSN(social);
            employee.setUserID(u_ID);
            employee.setPassword(pass);
            System.out.println("Employee info: " + employee);
            
        }
        catch(Exception e) {
            System.out.println("Exception " + e);
            RecordNotFoundException ex = new RecordNotFoundException("Employee " + userID + " not found.");
            throw ex;
        }
        
        return employee;
    }
    
    public static void initialize(){
        
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }
    
}