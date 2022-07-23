<%-- 
    Document   : login
    Created on : May 23, 2022, 7:19:04 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="assets/css/login/main.css">
        <link rel="stylesheet" type="text/css" href="assets/css/login/style.css">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <title>Register</title>
    </head>
    <body>
        <section class="material-half-bg">
            <div class="cover"></div>
        </section>
        <section class="login-content">
            <div class="container" style="width: 600px; background-color: white">
                <c:if test="${messFalse != null}"> 
                    <div id="alert" class="alert alert-danger">
                        ${messFalse}
                    </div>
                </c:if>
                <c:if test="${messTrue != null}"> 
                    <div id="alert" class="alert alert-success">
                        ${messTrue}&nbsp;<c:if test="${time != null}"><b>${time}</b></c:if> 
                        </div>
                </c:if>
                <form class="form-horizontal" role="form" action="register" method="post" id="registerForm1">
                    <h2 style="padding: 10px 220px;">Registration</h2>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Full Name</label>
                        <div class="col-sm-12">
                            <input type="text" id="firstName" name="txtName" placeholder="Enter full name" class="form-control" autofocus required=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Email</label>
                        <div class="col-sm-12">
                            <input type="email" name="txtEmail" placeholder="Enter email address" class="form-control" autofocus>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Mobile </label>
                        <div class="col-sm-12">
                            <input type="text" id="email" name="txtMobile" placeholder="Enter your mobile" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Address </label>
                        <div class="col-sm-12">
                            <textarea class="form-control" rows="2" placeholder="Enter your address" name="txtAddress"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-3 control-label">Password*</label>
                        <div class="col-sm-12">
                            <input type="password" id="password" name="txtPassword" placeholder="Password" class="form-control" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-3 control-label">Confirm Password*</label>
                        <div class="col-sm-12">
                            <input type="password" name="txtPassword1" placeholder="Confirm Password" class="form-control" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Gender</label>
                        <div class="col-sm-6">
                            <div class="row">
                                <div class="col-sm-4">
                                    <label class="radio-inline">
                                        <input type="radio" name="rd" id="femaleRadio" value="0">Female
                                    </label>
                                </div>
                                <div class="col-sm-4">
                                    <label class="radio-inline">
                                        <input type="radio" name="rd" id="maleRadio" value="1" checked="">Male
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div> <!-- /.form-group -->
                    <p>
                    <div class="form-group">
                        <div class="col-sm-12">
                        <button type="submit" class="btn btn-primary btn-block">Verify by email</button>
                        </div>
                    </div>
                    <div class="form-group mt-3">
                        <p class="semibold-text mb-0"><a href="login"><i class="fa fa-angle-left fa-fw"></i> Back to Login</a></p>
                    </div>
                </form> <!-- /form -->
            </div>
        </section>><!-- ./container -->
    </body>
        <script src="assets/js/login/jquery-3.2.1.min.js"></script>
        <script src="assets/js/login/popper.min.js"></script>
        <script src="assets/js/login/bootstrap.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js" type="text/javascript"></script>
        <script src="../../assets/js/login/validation.js" type="text/javascript"></script>
    <script src="assets/js/login/scripts.js"></script>
</html>
