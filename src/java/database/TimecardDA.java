package database;

import domain.Timecard;
import exceptions.RecordNotFoundException;
import exceptions.DuplicateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimecardDA {
    private static ArrayList<Timecard> timecards = new ArrayList<Timecard>(5);
    private static ArrayList<Timecard> employeeTimecards = new ArrayList<Timecard>();
    
    public static void add(Timecard tc) throws DuplicateException, SQLException{
        Date date = tc.getDate();
        int empID = tc.getEmployeeID();
        double hoursW = tc.getHoursWorked();
        double overtime = tc.getOvertimeHours();
        
        String sqlInsert = "INSERT INTO Timecard "
                + "(Timecard_Date, Employee_ID, Hours_Worked, Overtime_Hours) "
                + "VALUES ('"
                + date + "', '" + empID + "', '"
                + hoursW + "' + '" + overtime + "')";
        System.out.println("sqlInsert from TimecardDA.add" + sqlInsert);
        
        Connection connection = PayrollSystemDA.getConnection();
        System.out.println(connection);
        Statement statement;
        
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlInsert);
        } catch(Exception e) {
            e.getMessage();
        }
    }
    
    public static void delete(Timecard t){
        timecards.remove(t);
    }
    
    public static Timecard find(int id) throws RecordNotFoundException{
        Timecard timecard = null;
        Date date;
        int empID;
        double hoursWorked;
        double overtimeHours;
        
        String sqlString = "Select Timecard_ID, Timecard_Date, Employee_ID, Hours_Worked, Overtime_Hours"
                + " from Timecard"
                + " where Timecard_ID = " + id;
        System.out.println("SQL query from TimecardDA.add " + sqlString);
        
        Connection connection = PayrollSystemDA.getConnection();
        System.out.println(connection);
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs;            
            rs = statement.executeQuery(sqlString);
            rs.next();
            
            date = rs.getDate(1);
            empID = rs.getInt(2);
            hoursWorked = rs.getDouble(3);
            overtimeHours = rs.getDouble(4);
            
            timecard.setDate(date);
            timecard.setEmployeeID(empID);
            timecard.setHoursWorked(hoursWorked);
            timecard.setOvertimeHours(overtimeHours);
            
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
            RecordNotFoundException ex = new RecordNotFoundException("Timecard " + id + " not found.");
            throw ex;
        }
        return timecard;
    }
    
    public static void initialize(){
    }

    public static ArrayList<Timecard> getEmployeeTimecards(int ID) throws SQLException {
        employeeTimecards.clear();
        
        Timecard tc = new Timecard();
        int tcID;
        Date date;
        int empID;
        double hoursW;
        double overtimeH;
        
        String sqlQuery = "SELECT Timecard_ID, Timecard_Date, Employee_ID, Hours_Worked, Overtime_Hours " +
                "FROM Timecard " +
                "WHERE Employee_ID = " + ID;
        
        System.out.println("SQL query from TimecardDA.getEmployeeTimecards(int ID) " + sqlQuery);
        
        Connection connection = PayrollSystemDA.getConnection();
        Statement statement;
        
        try {
            statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(sqlQuery);
            
            while(rs.next()) {
                tcID = rs.getInt(1);
                System.out.println("tcID " + tcID);
                date = rs.getDate(2);
                System.out.println("date " + date);
                empID = rs.getInt(3);
                System.out.println("empID " + empID);
                hoursW = rs.getDouble(4);
                System.out.println("hoursWorked " + hoursW);
                overtimeH = rs.getDouble(5);
                System.out.println("overtimeHours " + overtimeH);
                
                tc.setTimecardID(tcID);
                tc.setDate(date);
                tc.setEmployeeID(empID);
                tc.setHoursWorked(hoursW);
                tc.setOvertimeHours(overtimeH);
                
                employeeTimecards.add(tc);  
            }
            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return employeeTimecards;
    }
    
    public static ArrayList<Timecard> getEmployeeTimecards(int ID, Date begDate, Date endDate) throws SQLException {
        employeeTimecards.clear();
        Timecard tc = new Timecard();
        int tcID;
        Date date;
        int empID;
        double hoursW;
        double overtimeH;
        
        String sqlQuery = "SELECT * " +
                "FROM Timecard " +
                "WHERE Employee_ID = '" + ID + "'" + "AND Date BETWEEN '" +
                begDate + "' and '" + endDate + "'";
        
        System.out.println("SQL query from TimecardDA.getEmployeeTimecards(int ID, Date begDate, Date endDate");
        
        Connection connection = PayrollSystemDA.getConnection();
        Statement statement;
        
        try {
            statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(sqlQuery);
            
            while(rs.next()) {
                tcID = rs.getInt(1);
                date = rs.getDate(2);
                empID = rs.getInt(3);
                hoursW = rs.getDouble(4);
                overtimeH = rs.getDouble(5);
                
                tc.setTimecardID(tcID);
                tc.setDate(date);
                tc.setEmployeeID(empID);
                tc.setHoursWorked(hoursW);
                tc.setOvertimeHours(overtimeH);
                
                employeeTimecards.add(tc);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
                        
        return employeeTimecards;
    }
    
    public static void update(Timecard tc) throws RecordNotFoundException {
        Timecard timecard = find(tc.getTimecardID());
        timecard.setDate(tc.getDate());
        timecard.setHoursWorked(tc.getHoursWorked());
        timecard.setOvertimeHours(tc.getOvertimeHours());
    }
}