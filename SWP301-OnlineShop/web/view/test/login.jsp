<%-- 
    Document   : login
    Created on : Feb 16, 2022, 11:16:29 AM
    Author     : SAP-LAP-FPT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="/test/login" method="POST">
            Username: <input type="text" name="email" /> <br/>
            Password: <input type="password" name="password" /> <br/>
            <input type="submit" value="Login"/>
        </form>
    </body>
</html>
