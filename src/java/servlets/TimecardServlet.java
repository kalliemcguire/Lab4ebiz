package servlets;

import domain.PayrollSystem;
import domain.Employee;
import domain.Timecard;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TimecardServlet extends HttpServlet {
    
    private static DateFormat dateFormatShort = DateFormat.getDateInstance(DateFormat.SHORT);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String idString;
        int id;
        
        HttpSession session = request.getSession();
        PayrollSystem.initialize();
        
        String url = "/main.jsp";
        String option = request.getParameter("option");

        if (option.equals("add")){
            Timecard timecard = new Timecard();
            session.setAttribute("timecard", timecard);
            url = "/timecard.jsp";
        }
        
        if (option.equals("delete")){
            idString = request.getParameter("id");
            id = Integer.parseInt(idString);
            Timecard timecard = Timecard.find(id);
            timecard.delete();
            Employee employee = (Employee)session.getAttribute("employee");
            id = employee.getEmployeeID();
            ArrayList timecards = Timecard.getEmployeeTimecards(id);
            session.setAttribute("timecards", timecards);
            url = "/timecardList.jsp";
        }
        
        if (option.equals("list")){
            Employee employee = (Employee)session.getAttribute("employee");
            id = employee.getEmployeeID();

            if(employee.getClass().getSimpleName().equals("HourlyEmployee")) {
                ArrayList timecards = Timecard.getEmployeeTimecards(id);
                session.setAttribute("timecards", timecards);
                url = "/timecardList.jsp";
            }
            
            else {
                System.out.println("Salaried employeee");
                String message = "There are no timecards for salaried employees";
                session.setAttribute("message", message);
            }
        }
        
        if (option.equals("save")){
            Timecard timecard = (Timecard)session.getAttribute("timecard");
            
            Employee employee = (Employee)session.getAttribute("employee");
            id = employee.getEmployeeID();
            
            String dateString = request.getParameter("date");
            String hoursString = request.getParameter("hours");
            String overtimeString = request.getParameter("overtime");
            Date date = new Date();
            try {
                date = dateFormatShort.parse(dateString);
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
            double hours = Double.parseDouble(hoursString);
            double overtime = Double.parseDouble(overtimeString);
            
            timecard.setDate(date);
            timecard.setHoursWorked(hours);
            timecard.setOvertimeHours(overtime);
                
            if (timecard.getTimecardID() < 0) {
                timecard.setEmployeeID(id);
                timecard.add();
            }
            
            else {
                timecard.update();
            }
            
            ArrayList timecards = Timecard.getEmployeeTimecards(id);
            session.setAttribute("timecards", timecards);
            url = "/timecardList.jsp";
        }
        
        if (option.equals("update")){
            idString = request.getParameter("id");
            id = Integer.parseInt(idString);
            Timecard timecard = Timecard.find(id);
            session.setAttribute("timecard", timecard);
            url = "/timecard.jsp";
        }
        
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}