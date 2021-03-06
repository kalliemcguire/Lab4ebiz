package database;

import domain.Employee;
import domain.HourlyEmployee;
import domain.SalaryEmployee;
import exceptions.RecordNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class EmployeeDA {
    
    private static ArrayList<Employee> employees = new ArrayList<Employee>(5);
    
    public static void add(Employee emp) throws SQLException{
        int empID = emp.getEmployeeID();
        int empType = emp.getEmployeeType();
        String fn = emp.getFirstName();
        String ln = emp.getLastName();
        long SSN = emp.getSSN();
        String uID = emp.getUserID();
        String pw = emp.getPassword();
        
        String sqlInsert = "INSERT INTO EMPLOYEE " +
                "(Employee_ID, Employee_Type, First_Name, Last_Name, SSN, User_ID, Password) " +
                "VALUES (" + empID + ", " + empType + ", '" + fn + "' , '" + ln + "' , " + SSN +
                ", '" + uID + "', '" + pw + "')";
        System.out.println("sqlInsert employee add " + sqlInsert);
        
        Connection connection = PayrollSystemDA.getConnection();
        System.out.println(connection);
        Statement statement;
        
        try {
            statement = connection.createStatement();
            statement.execute(sqlInsert);  
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        
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
                + " Where Employee_ID = " + ID;
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
//dont know if i need this 
//            if(empID == ID && empType == Employee.HOURLY)
//                emp = new SalaryEmployee();
//            else if(empID == ID && empType == Employee.SALARY)
//                emp = new HourlyEmployee();
            
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
    
    public static Employee findByUserID(String userID) throws RecordNotFoundException{
        Employee employee = null;
        int empID;
        int empType;
        String empFirstName;
        String empLastName;
        long social;
        String u_ID;
        String pass;
        
        String sqlString = "SELECT *"
                + " FROM Employee"
                + " WHERE User_ID = '" + userID + "'";
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
    
    public static void delete(Employee emp) throws RecordNotFoundException{
        int empID = emp.getEmployeeID();
        
        String sqlDelete = "DELETE FROM Employee WHERE Employee_ID = " + empID;
        
        System.out.println("sqlDelete query " + sqlDelete);
        
        Connection connection = PayrollSystemDA.getConnection();
        System.out.println(connection);
        Statement statement;
        
        try {
            statement = connection.createStatement();
            statement.execute(sqlDelete);
        } catch(Exception e) {
           System.out.println("Exception " + e);
           RecordNotFoundException ex = new RecordNotFoundException("Employee " + empID + " not found.");
           throw ex; 
        }
    }
    
    public static void update(Employee emp) throws RecordNotFoundException{
        int empID = emp.getEmployeeID();
        int empType = emp.getEmployeeType();
        String fn = emp.getFirstName();
        String ln = emp.getLastName();
        long SSN = emp.getSSN();
        String uID = emp.getUserID();
        String pw = emp.getPassword();
        
        String sqlUpdate = "UPDATE Employee " +
                "SET Employee_ID = " + empID + ", Employee_Type = " + empType + ", First_Name = '" + fn + "', Last_Name = '" +
                ln + "', SSN = " + SSN + ", User_ID = '" + uID + "', Password = '" + pw + "'" +
                " WHERE Employee_ID = " + empID;
        
        System.out.println("sqlUpdate " + sqlUpdate);
        
        Connection connection = PayrollSystemDA.getConnection();
        System.out.println(connection);
        Statement statement;
        
        try {
            statement = connection.createStatement();
            statement.execute(sqlUpdate);
        } catch(Exception e) {
           System.out.println("Exception " + e);
           RecordNotFoundException ex = new RecordNotFoundException("Employee " + empID + " not found.");
           throw ex;
        }
    }
    
    public static void initialize(){
        
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }
    
}