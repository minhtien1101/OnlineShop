<%-- 
    Document   : addNewUser
    Created on : May 26, 2022, 8:50:53 AM
    Author     : Admin
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>  
        <meta charset="UTF-8">
        <title>Admin | Add new user</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <!-- bootstrap 3.0.2 -->
        <link href="../../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="../../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Theme style -->
        <link href="../../assets/css/style.css" rel="stylesheet" type="text/css" />
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!--css-->
        <link href="../../assets/css/admin/addNewUser.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="skin-black">
        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../admin-layout/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../admin-layout/sideBar.jsp"></jsp:include>
                <!-- Right side. contains main content -->
                <aside class="right-side">
                    <!-- Main content -->
                    <section class="content">
                        <div id="content">
                            <h2 class="text-center">Add new user</h2>
                            <form id="basic-form" action="addNewUser" method="post" class="d-flex justify-content-center" >
                                <p>
                                    <label for="fullname">Full name<span class="text-danger">*</span></label>
                                    <input type="text" id="fullname" name="fullname" value="${requestScope.fullname}" required>
                            </p>
                            <p>
                                <label for="gender">Gender<span class="text-danger">*</span></label>
                                <input type="radio" name="gender" value="male" checked="checked" ${requestScope.gender == "male" ? "checked='checked'": ""} /> Male
                                <input type="radio" name="gender" value="female" ${requestScope.gender == "female" ? "checked='checked'": ""}/>Female
                            </p>
                            <p>
                                <label for="email">Email<span class="text-danger">*</span></label>
                                <input id="email" name="email" value="${requestScope.email}">
                            </p>
                            <p>
                                <label for="mobile">Mobile<span class="text-danger">*</span></label>
                                <input type="text" id="mobile" name="mobile" value="${requestScope.mobile}" onchange="validateMobile();" required>
                                <span id="mobile-errors" class="error" for="mobile" style="display: none;"></span>
                            </p>
                            <p>
                                <label for="role">Role<span class="text-danger">*</span></label>
                                <select name="role" id="role">
                                    <c:forEach items="${requestScope.roles}" var="r">
                                        <c:if test="${!r.name.equalsIgnoreCase('customer') && r.name != 'Super Admin'}">
                                            <option value="${r.id}"  ${requestScope.roleId == r.id ? "selected='selected'":""}>${r.name}</option>
                                        </c:if>

                                    </c:forEach>
                                </select>
                            </p>
                            <p>
                                <label for="address">Address<span class="text-danger">*</span></label>
                                <input id="address" name="address" value="${requestScope.address}" required>
                            </p>
                            <p>
                                <label for="status">Status<span class="text-danger">*</span></label>
                                <input type="radio" name="status" value="active" ${requestScope.status == "active" ? "checked='checked'": ""}>Active
                                <input type="radio" name="status" value="unactive" checked="checked"} ${requestScope.status == "unactive" ? "checked='checked'": ""}>Unactive
                            </p>

                            <c:if test="${message != null}">
                                <div class="alert alert-danger fade in" id="message">
                                    ${requestScope.message}
                                </div>
                            </c:if>
                            <input class="submit" type="submit" value="SUBMIT" class="">
                        </form>
                    </div>
                </section>
            </aside>
        </div>

        <!--javascrip-->
        <script src="../../assets/js/admin/addNewUser.js"></script>
        <script src="../../assets/js/admin/addNewUser.js"></script>
        <script src="../../assets/js/admin/main.js" type="text/javascript"></script>
        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="../../js/jquery.min.js" type="text/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
        <!-- jQuery UI 1.10.3 -->
        <script src="../../assets/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../../assets/js/Director/app.js" type="text/javascript"></script>
    </body>
</html>
