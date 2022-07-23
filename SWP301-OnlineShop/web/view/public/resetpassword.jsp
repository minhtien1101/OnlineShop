<%-- 
    Document   : resetpassword
    Created on : May 28, 2022, 9:55:51 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="assets/css/login/main.css">
        <link rel="stylesheet" type="text/css" href="assets/css/login/style.css">
        <!--        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">-->
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="assets/js/login/jquery-3.2.1.min.js"></script>
        <script src="assets/js/login/popper.min.js"></script>
        <script src="assets/js/login/bootstrap.min.js"></script>
        <script src="assets/js/login/main.js"></script>
        <script src="assets/js/login/pace.min.js"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js" type="text/javascript"></script>
        <script src="../../assets/js/login/validation.js" type="text/javascript"></script>
        <title>Reset Password</title>
    </head>
    <body>
        <section class="material-half-bg">
            <div class="cover"></div>
        </section>
           

        <section class="login-content">
               <c:if test="${messTrue != null}">
                    <div class="alert alert-success" role="alert" style="text-align: center">
                        ${messTrue}
                    </div>
                </c:if>
                <c:if test="${messFalse != null}">
                    <div class="alert alert-danger" role="alert" style="text-align: center">
                        ${messFalse}
                    </div>
                </c:if>
            <div class="logo">
                <h1>Online Shop</h1>
            </div>
            <div class="container" style="width: 600px; background-color: white">
   
                <form method="post" id="passwordForm" method="post" action="changePassword">
                    <h2 style="padding: 15px 185px">Reset Password</h2>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">New Password</label>
                        <div class="col-sm-12">
                            <input type="hidden" name="hdEmail" value="${email}"/>
                            <input type="password" class="input-lg form-control" name="txtPassword1" id="password1" placeholder="New Password" autocomplete="off" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Confirm Password</label>
                        <div class="col-sm-12">
                            <input type="password" class="input-lg form-control" name="txtPassword2" id="password2" placeholder="Repeat Password" autocomplete="off" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-12">
                            <button type="submit" class="btn btn-primary btn-block">Change Password</button>
                        </div>
                    </div>
                    <div class="form-group mt-3">
                        <p class="semibold-text mb-0"><a href="login"><i class="fa fa-angle-left fa-fw"></i> Back to Login</a></p>
                    </div>


                </form>
            </div>
        </section>
    </body>
    <script src="assets/js/login/scripts.js"></script>
</html>
