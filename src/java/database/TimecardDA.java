package database;

import domain.Timecard;
import exceptions.RecordNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;

public class TimecardDA {
    private static ArrayList<Timecard> employeeTimecards = new ArrayList<Timecard>();
    
    
    public static void add(Timecard tc) throws SQLException{
        int tcID = tc.getTimecardID();
        //when I used the getFormattedDate method from Timecard, I got the error "The syntax of the string representation of a date/time value is incorrect"
        //println shows that getDateFormatted only shows last two digits of year and does not have leading zeroes for months/days below 10, I think that was the issue
        //googled around and found that I could create my own date format using SimpleDateFormat, did mm/dd/yyyy to match the way I have inserted my dates in my SQL file
        String date = tc.getSimpleDate();
        String date2 = tc.getDateFormatted();
        System.out.println("date check " + date);
        System.out.println("date2 check " + date2);
        int empID = tc.getEmployeeID();
        double hoursW = tc.getHoursWorked();
        double overtime = tc.getOvertimeHours();
        
        String sqlInsert = "INSERT INTO Timecard "
                + "(Timecard_Date, Employee_ID, Hours_Worked, Overtime_Hours) "
                + "VALUES ('"
                + date + "', " + empID + ", "
                + hoursW + ", " + overtime + ")";
        System.out.println("sqlInsert from TimecardDA.add " + sqlInsert);
        
        Connection connection = PayrollSystemDA.getConnection();
        System.out.println(connection);
        Statement statement;
        
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlInsert);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
    
    public static void delete(Timecard tc) throws RecordNotFoundException{
        int tcID = tc.getTimecardID();
        
        String sqlDelete = "DELETE FROM Timecard WHERE Timecard_ID = " + tcID;
        
        System.out.println("sqlDelete query from TimecardDA.delete " + sqlDelete);
        
        Connection connection = PayrollSystemDA.getConnection();
        Statement statement;
        
        try {
            statement = connection.createStatement();
            statement.execute(sqlDelete);
        }catch(Exception e) {
           System.out.println("Exception " + e);
           RecordNotFoundException ex = new RecordNotFoundException("Timecard " + tcID + " not found.");
           throw ex;
        }
    }
    
    public static Timecard find(int id) throws RecordNotFoundException{
        Timecard tc;
        int tcID;
        Date date;
        int empID;
        double hoursWorked;
        double overtimeHours;
        
        String sqlString = "Select * "
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
            
            tcID = rs.getInt(1);
            date = rs.getDate(2);
            empID = rs.getInt(3);
            hoursWorked = rs.getDouble(4);
            overtimeHours = rs.getDouble(5);
            
            tc = new Timecard();
            
            tc.setTimecardID(tcID);
            tc.setDate(date);
            tc.setEmployeeID(empID);
            tc.setHoursWorked(hoursWorked);
            tc.setOvertimeHours(overtimeHours);
            
        } catch(Exception e) {
           System.out.println("Exception " + e);
           RecordNotFoundException ex = new RecordNotFoundException("Timecard not found.");
           throw ex;
        }
        return tc;
    }
    
    public static void initialize(){
    }

    public static ArrayList<Timecard> getEmployeeTimecards(int ID) throws SQLException {
        employeeTimecards.clear();
        
        Timecard tc;
        int tcID;
        Date date;
        int empID;
        double hoursW;
        double overtimeH;
        
        String sqlQuery = "SELECT * " +
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
                date = rs.getDate(2);
                empID = rs.getInt(3);
                hoursW = rs.getDouble(4);
                overtimeH = rs.getDouble(5);
                
                tc = new Timecard();
                
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
        Timecard tc;
        int tcID;
        Date date;
        int empID;
        double hoursW;
        double overtimeH;
        
        String sqlQuery = "SELECT * " +
                "FROM Timecard " +
                "WHERE Employee_ID = " + ID + "AND Date BETWEEN '" +
                begDate + "' and '" + endDate + "'";
        
        System.out.println("SQL query from TimecardDA.getEmployeeTimecards(int ID, Date begDate, Date endDate) ");
        
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
                
                tc = new Timecard();
                
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
         
        System.out.println(employeeTimecards.toString());
        return employeeTimecards;
    }
    
    public static void update(Timecard tc) throws SQLException {
        //same as with add, using my own dateformat
        String date = tc.getSimpleDate();
        System.out.println("date test " + date);
        Double hoursW = tc.getHoursWorked();
        Double overtimeH = tc.getOvertimeHours();
        
        String sqlUpdate = "UPDATE Timecard " +
                "SET Timecard_Date = '" + date + "', Hours_Worked = " + hoursW + ", Overtime_Hours = " + overtimeH +
                " WHERE Timecard_ID = " + tc.getTimecardID();
        
        System.out.println("sqlUpdate query from TimecardDA.update " + sqlUpdate);
        
        Connection connection = PayrollSystemDA.getConnection();
        Statement statement;
        
        try {
            statement = connection.createStatement();
            statement.execute(sqlUpdate);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}