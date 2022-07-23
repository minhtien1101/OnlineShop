<%-- 
    Document   : productList
    Created on : Jun 6, 2022, 7:48:57 AM
    Author     : Admin
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>  
        <meta charset="UTF-8">
        <title>Marketing | Feedback Details</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <!-- bootstrap 3.0.2 -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

        <!-- Theme style -->
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
        <!--css-->
        <link href="../../assets/css/admin/userList.css" rel="stylesheet" type="text/css"/>
        <link href="../../assets/css/cart/main.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.css">

        <!--active button nav in sidebar-->

        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body class="skin-black">

        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../marketing-template/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../marketing-template/sideBar.jsp"></jsp:include>
                <aside class="right-side">
                <!-- Main content -->
                <section class="content ">
                    <div class ="row d-flex justify-content-center">
                        <h2 class="title text-center">Feedback Details</h2>
                    </div>
                    <section class="panel">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-4">
                                    <p>
                                        <label>Full name: </label>
                                        <span>${requestScope.feedback.user.fullname}</span> 
                                    </p>
                                    <p>
                                        <label>Email: </label>
                                        <span>${requestScope.feedback.user.email}</span> 
                                    </p>
                                    <p>
                                        <label>Mobile: </label>
                                        <span>${requestScope.feedback.user.mobile}</span> 
                                    </p>
                                </div>

                                <div class="col-md-4">
                                    <p>
                                        <label>Product: </label>
                                        <span>${requestScope.feedback.product.name}</span> 
                                        <span>(ID: ${requestScope.feedback.product.id} - <a id="btnShowThumbnail" href="#" data-thumbnail="${requestScope.feedback.product.thumbnail}">Click here to show product thumbnail</a>)</span> 

                                    </p>
                                    <p>
                                        <label>Rated star: </label>
                                        <c:forEach begin="1" end="${requestScope.feedback.start}" step="1">
                                            <span class="fa fa-star" style="color:orange;"></span>
                                        </c:forEach>
                                        <span> (${requestScope.feedback.start} / 5)</span> 
                                    </p>
                                    <p>
                                        <label>Comment: </label>
                                        <span> ${requestScope.feedback.comment}</span> 
                                    </p>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <label>Status: </label>
                                        <c:if test="${requestScope.feedback.status == true}">
                                            <span id="lblStatus" class="label label-success">Active</span></br>
                                            <button type="button" class="btn btn-danger" data-passing='{"fid":${requestScope.feedback.id},"status":0}' data-toggle="modal" data-target="#myModal1" id="btnUpdateFBStatus">Change to De-active</button>
                                        </c:if>
                                        <c:if test="${requestScope.feedback.status == false}">
                                            <span id="lblStatus" class="label label-danger">De-active</span></br>
                                            <button type="button" class="btn btn-success" data-passing='{"fid":${requestScope.feedback.id},"status":1}' data-toggle="modal" data-target="#myModal1" id="btnUpdateFBStatus">Change to Active</button>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <label>Feedback Images:</label>
                                    <div class="display-flex" id="feedbackImg">
                                        <c:if test="${requestScope.feedback.image.size() == 0}">
                                            <span>No images given!</span>
                                        </c:if>
                                        <c:if test="${requestScope.feedback.image.size() != 0}">
                                            <c:forEach items="${requestScope.feedback.image}" var="i">
                                                <img src="${i.image}" class="img-contain"/>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    </div>

                    <!-- Modal -->
                    <div id="myModal" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <!-- Modal content-->
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Product thumbnail</h4>
                                </div>
                                <div class="modal-body">
                                    <img src="" class="imagepreview" style="width: 100%;" >
                                </div>
                            </div>

                        </div>
                    </div>
                    <div id="myModal1" class="modal fade" role="dialog">
                        <div class="modal-dialog">
                            <!-- Modal content-->
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Confirm</h4>
                                </div>
                                <div class="modal-body">
                                    <span>Do you want to update the status?</span>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    <button id ="btnConfirmUpdateFBStatus" type="button" class="btn btn-primary" data-dismiss="modal">Update</button>
                                </div>
                            </div>

                        </div>
                    </div>
                </section> <!--/ Main content -->
            </aside><!-- /.right-side -->
        </div>
        <!--javascrip-->
        <script src="../../assets/js/marketing/productList.js"></script>
        <script src="../../assets/js/admin/main.js"></script>
        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <!-- jQuery UI 1.10.3 -->
        <script src="../../assets/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../../assets/js/Director/app.js" type="text/javascript"></script>
        <script src="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.min.js"></script>
    </body>
</html>
