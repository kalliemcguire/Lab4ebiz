<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Timecards</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>

        <h1>Your Timecards</h1>

        <table>
            <tr>
                <th>Date</th>
                <th>Hours Worked</th>
                <th>Overtime</th>
            </tr>

            <c:forEach var="card" items="${timecards}">
                <tr>
                    <td><c:out value='${card.dateShort}'/></td>
                    <td class="right"><c:out value='${card.hoursWorkedFormatted}'/></td>
                    <td class="right"><c:out value='${card.overtimeHoursFormatted}'/></td>
                    <td>
                        <form action="timecard" method="post">
                            <input type="hidden" name="option" value="update">
                            <input type="hidden" name="id" value="${card.timecardID}">
                            <input type="submit" value="Update">
                        </form>
                    </td>
                    <td>
                        <form action="timecard" method="post">
                            <input type="hidden" name="option" value="delete">
                            <input type="hidden" name="id" value="${card.timecardID}">
                            <input type="submit" value="Delete">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
  
        <form action="timecard" method="post">
            <input type="hidden" name="option" value="add">
            <input type="submit" value="Add Timecard">
        </form>

        <form action="main.jsp" method="post">
            <input type="hidden" name="option" value="return">
            <input type="submit" value="Return to Main Menu">
        </form>

    </body>
</html>