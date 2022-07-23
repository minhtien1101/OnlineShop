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
        <script src="assets/js/login/jquery-3.2.1.min.js"></script>
        <script src="assets/js/login/popper.min.js"></script>
        <script src="assets/js/login/bootstrap.min.js"></script>
        <script src="assets/js/login/main.js"></script>
        <script src="assets/js/login/pace.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

        <title>Login</title>
    </head>
    <body>
        <section class="material-half-bg">
            <div class="cover"></div>
        </section>
        <section class="login-content">
            <div>
                <c:if test="${messFalse != null}"> 
                    <div id="alert" class="alert alert-danger">
                        ${messFalse}
                    </div>
                </c:if>
                <c:if test="${messTrue != null}"> 
                    <div id="alert" class="alert alert-success">
                        ${messTrue}&nbsp;<c:if test="${time != null}">(Existing until <b>${time}</b>)</c:if>
                        </div>
                </c:if>
            </div>
            <div class="logo" style="cursor: pointer;">
                <h1 id="backHome">Online Shop</h1>
            </div>
            <div class="login-box">

                <!-- login form -->
                <form class="login-form" action="login" method="post">
                    <h3 class="login-head" id="sign"><i class="fa fa-lg fa-fw fa-user"></i>SIGN IN</h3>
                    <div class="form-group">
                        <label class="control-label">EMAIL</label>
                        <input class="form-control" type="email" placeholder="Email" id="email" required name="txtEmail" autofocus value="${email}">
                    </div>
                    <div class="form-group">
                        <label class="control-label">PASSWORD</label>
                        <input class="form-control" type="password" placeholder="Password" name="txtPassword" id="password" required value="${password}">
                    </div>
                    <div class="link-box">
                        <div class="e">
                            <input type="checkbox" name="cboSigned" ${checkbox.equals("checked")? "checked":""}/>&nbsp;<span class="label-text a">Remember</span>
                        </div>
                        <p class="semibold-text mb-2 a e"><a href="register" >Sign up</a></p>
                        <p class="semibold-text mb-2 a e"><a href="send" data-toggle="flip">Forgot Password</a></p>
                    </div>
                    <div class="form-group btn-container">
                        <button class="btn btn-primary btn-block"><i class="fa fa-sign-in fa-lg fa-fw"></i>SIGN IN</button>

                    </div>
                </form>

                <!-- forget form -->  
                <form class="forget-form" action="send" method="post">
                    <h3 class="login-head"><i class="fa fa-lg fa-fw fa-lock"></i>Forgot Password ?</h3>
                    <div class="form-group">
                        <label class="control-label">EMAIL</label>
                        <input class="form-control" type="email" placeholder="Email" name="txtEmail" required>
                    </div>
                    <div class="form-group btn-container">
                        <button class="btn btn-primary btn-block"><i class="fa fa-unlock fa-lg fa-fw"></i>SEND</button>
                    </div>
                    <div class="form-group mt-3">
                        <p class="semibold-text mb-0"><a href="login" data-toggle="flip"><i class="fa fa-angle-left fa-fw"></i> Back to Login</a></p>
                    </div>
                </form>
            </div>
        </section>
    </body>
    <script src="assets/js/login/scripts.js">
    </script>
    <script>
        $('#backHome').on('click', function() {
            window.location.href = "home";
        });
    </script>
</html>
