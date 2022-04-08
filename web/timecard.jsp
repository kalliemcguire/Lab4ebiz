<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TimeCard</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>    
    </head>

    <body>
        <h1>Update Timecard Values</h1>
        <p><i>${message}</i></p>
        <form action="timecard" mehtod="post">
            <input type="hidden" name="option" value="save">
            <label class="pad_top">Date</label>
            <input type="text" name="date" value="${timecard.dateShort}" required>
            <label class="pad_top">Hours Worked</label>
            <input type="text" name="hours" value="${timecard.hoursWorkedFormatted}" required>
            <label>Overtime Hours</label>
            <input type="text" name="overtime" value="${timecard.overtimeHoursFormatted}">
            <br>
            <label>&nbsp;</label>
            <input type="submit" value="Save" class="margin_left">
        </form>
            <label>&nbsp;</label>
            <form action="timecardList.jsp">
            <input type="submit" value="Cancel">
        </form>
    </body>
</html>