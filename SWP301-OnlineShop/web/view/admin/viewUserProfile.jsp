<%-- 
    Document   : userProfile
    Created on : May 25, 2022, 11:51:56 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>  
        <meta charset="UTF-8">
        <title>Admin | User Profile</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <!-- bootstrap 3.0.2 -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Theme style -->
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        
        <!--css-->
        <link href="../assets/css/admin/userProfile.css" rel="stylesheet" type="text/css"/>
        <!--javascrip-->
        <script src="../assets/js/admin/addNewUser.js"></script>
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
                <section class="content" style="font-size: 20px;">
                    <div id="content">
                        <h3>User details</h3>
                        <div class="row">
                            <c:if test="${requestScope.user.avatar != null}">
                                <img src="${user.avatar}"  class="img-circle user-img" alt="User img"> 
                            </c:if>
                            <c:if test="${requestScope.user.avatar == null}">
                                <img src="../assets/img/defaultUserAvatar.png"  class="img-circle user-img" alt="User image"> 
                            </c:if>
                        </div>
                        <div class="row mb-3">
                            <label for="name" class="col-sm-2 col-form-label">Full name</label>
                             ${requestScope.user.fullname}
                        </div>
                        
                        <div class="row mb-3">
                            <label for="gender" class="col-sm-2 col-form-label">Gender</label>
                            ${requestScope.user.gender?"Male":"Female"}
                        </div>
                        
                        <div class="row mb-3">
                                <label for="email" class="col-sm-2 col-form-label">Email</label>
                                ${requestScope.user.email}
                        </div>
                                
                        <div class="row mb-3">
                                <label for="mobile" class="col-sm-2 col-form-label">Mobile</label>
                                ${requestScope.user.mobile}
                        </div>
                        
                        <div class="row mb-3">
                                <label for="address" class="col-sm-2 col-form-label">Address</label>
                                ${requestScope.user.address}
                        </div>
                                
                            <input type="hidden" name="id" value="${requestScope.user.id}">
                            <div class="row mb-3">
                                <label for="role" class="col-sm-2 col-form-label">Role</label>
                                 ${user.role.name}
                            </div>

                            <div class="row mb-3">
                                <label for="status" class="col-sm-2 col-form-label">Status</label>
                                ${requestScope.user.status?"Activate":"Deactivate"} 
                            </div> 

                    </div>

                </section>
            </aside>
            <!--Modal-->             
            <div class="modal fade" id="myModal" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-body">
                            <p>Are you sure to change status!</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" onclick="document.getElementById('changeStatus').submit();">Yes</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                        </div>
                    </div>
      
                </div>
            </div>
            <!--/Modal-->
        </div>
                               
                               
         <script src="../../assets/js/admin/main.js" type="text/javascript"></script>                      
        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="../js/jquery.min.js" type="text/javascript"></script>
        <!-- jQuery UI 1.10.3 -->
        <script src="../assets/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../assets/js/Director/app.js" type="text/javascript"></script>
    </body>
</html>
