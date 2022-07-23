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
        <title>Admin | Edit role</title>
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
        <link href="../../assets/css/admin/editUserRole.css" rel="stylesheet" type="text/css"/>
        <link href="../../assets/css/admin/main.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet" type="text/css"/>
    </head>
    <c:set var="user" value="${sessionScope.user}"/>
    <body class="skin-black">
        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../admin-layout/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../admin-layout/sideBar.jsp"></jsp:include>
                <!-- Right side. contains main content -->
                <aside class="right-side">
                    <!-- Main content -->
                    <section class='content'>
                        <h2 class="text-center" style="font-weight: 700; text-align: center">Edit a role</h2>
                        <div class="error-msg">
                        </div>
                        <form action="editRole" method="post" class="form-horizontal" id="form-updaterole">
                            <p>
                                <label for="roleName">Select role name<span class="text-danger">*</span></label>
                                <select name="roleId" class="form-select required">
                                    <option value="" selected disabled hidden>Please choose a role</option>
                                <c:forEach items="${requestScope.roles}" var="r">
                                    <c:if test="${!r.name.equalsIgnoreCase('customer') && r.name != 'Super Admin'}">
                                        <option value="${r.id}" ${r.id == requestScope.rawid ? "selected='selected'":""}>${r.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </p>

                        <div class="form-group">
                            <p style="font-size: 20px; font-weight: 700; text-align: center">List of features</p>
                            <p>
                                <label for="adminFeatutes">Admin</label><br>
                                <input type="checkbox" id="btnSelectAllAdmin" ${sessionScope.user.role.name == 'Super Admin'?'':'disabled'}/>
                                <label for="checkall">Select all</label><br>
                                <c:forEach items="${requestScope.AdminFeatures}" var="a">
                                    <input type="checkbox" id="adminFeatures" name="fid" value="${a.id}"
                                           <c:forEach items="${requestScope.allowedFeatures}" var='b'>
                                               <c:if test='${a.id == b.key.id && b.value == true && user.role.name == "Super Admin"}'>
                                                   checked
                                               </c:if>
                                               <c:if test='${a.id == b.key.id && b.value == true && user.role.name != "Super Admin"}'>
                                                   checked disabled
                                               </c:if>
                                               <c:if test='${user.role.name != "Super Admin"}'>
                                                   disabled
                                               </c:if>
                                           </c:forEach>>
                                    <label for="fid">${a.name}</label><br>
                                </c:forEach>
                            </p>
                        </div>
                        <div class="form-group">
                            <p>
                                <label for="marketingFeatutes">Marketing</label><br>
                                <input type="checkbox" id="btnSelectAllMarketing"/>
                                <label for="checkall">Select all</label><br>
                                <c:forEach items="${requestScope.MarketingFeatures}" var="a">
                                    <input type="checkbox" id="marketingFeatures" name="fid" value="${a.id}"                                      
                                           <c:forEach items="${requestScope.allowedFeatures}" var='b'>
                                               <c:if test='${a.id == b.key.id && b.value == true}'>
                                                   checked
                                               </c:if>
                                           </c:forEach>>
                                    <label for="fid">${a.name}</label><br>
                                </c:forEach>
                            </p>
                        </div>
                        <div class="form-group">
                            <p>
                                <label for="salesFeatures">Sale</label><br>
                                <input type="checkbox" id="btnSelectAllSale"/>
                                <label for="checkall">Select all</label><br>
                                <c:forEach items="${requestScope.SalesFeatues}" var="a">
                                    <input type="checkbox" id="saleFeatures" name="fid" value="${a.id}"                                                
                                           <c:forEach items="${requestScope.allowedFeatures}" var='b'>
                                               <c:if test='${a.id == b.key.id && b.value == true}'>
                                                   checked
                                               </c:if>
                                           </c:forEach>>
                                    <label for="role">${a.name}</label><br>
                                </c:forEach>
                            </p>
                        </div>
                        <input id="submitUpdateRole" type="submit" value="UPDATE">
                    </form>
                </section>
                <!-- Modal -->
                <div id="myModal" class="modal fade" role="dialog">
                    <div class="modal-dialog">
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Confirm update role</h4>
                            </div>
                            <div class="modal-body">
                                Do you want to update?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                <button id ="btnConfirmUpdateRole" type="button" class="btn btn-primary" data-dismiss="modal">Confirm</button>
                            </div>
                        </div>

                    </div>
                </div>
            </aside>
        </div>

        <!--javascrip-->
        <script src="../../assets/js/admin/role.js"></script>
        <script src="../../assets/js/admin/main.js" type="text/javascript"></script>
        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
        <!-- jQuery UI 1.10.3 -->
        <script src="../../assets/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../../assets/js/Director/app.js" type="text/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js" type="text/javascript"></script>
        <script type = "text/javascript">
            function showSuccessMsg(title, message)
            {
                toastr.success(message, title);
            }
            function showErrMsg(title, message)
            {
                toastr.error(message, title);
            }
        </script>
        <c:if test="${requestScope.message != null && requestScope.error == false}">
            <script>
                showSuccessMsg('Noftification', "${requestScope.message}");
            </script>
        </c:if>
        <c:if test="${requestScope.message != null && requestScope.error == true}">
            <script>
                showErrMsg('Noftification', "${requestScope.message}");
            </script>
        </c:if>
    </body>
</html>
