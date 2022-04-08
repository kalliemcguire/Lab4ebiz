<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <h1>ABC Corporation Payroll System</h1>
        <h2>Enter User Id and Password</h2>
        <p>${message}</p>
        <form action="login" method="post">
            <label class="pad_top">User ID:</label>
            <input type="text" name="userID" required><br>
            <label class="pad_top">Password:</label>
            <input type="password" name="password" required><br>
            <input type="submit" value="Login">
        </form>
    </body>
</html>