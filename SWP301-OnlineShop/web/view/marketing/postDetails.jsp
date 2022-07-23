<%-- 
    Document   : PostDetail
    Created on : Jun 6, 2022, 2:04:28 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Marketing | Post Details</title>
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
        <link href="../../assets/css/admin/main.css" rel="stylesheet" type="text/css"/>

        <!--active button nav in sidebar-->

        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    </head>
    <body class="skin-black">
        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../marketing-template/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../marketing-template/sideBar.jsp"></jsp:include>
                <!-- Right side. contains main content -->
                <aside class="right-side">
                    <!-- Main content -->
                    <section class="content ">
                        <div class="product-content product-wrap clearfix product-deatil">
                            <div class="row">
                                <div class="col-md-5 col-sm-12 col-xs-12">
                                    <div class="product-image">
                                        <div id="myCarousel-2" class="carousel slide">

                                            <div class="carousel-inner">
                                                <!-- Slide 1 -->
                                                <div class="item active boder-radius-over-h" border="1" style="margin: 0 auto; width: fit-content;">

                                                    <img style="height: 100%;" src="${requestScope.post.thumbnail}" alt="thumbnail" class="w-h-100">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-md-offset-1 col-sm-12 col-xs-12">
                                <h2 class="name text-tranform-wrap" >
                                    ${requestScope.post.title}
                                </h2>
                                <hr>
                                <div class="price-container flex-between">
                                    <div>
                                        <div class="mb-10px">
                                            <strong><i class="fa fa-info-circle" aria-hidden="true"></i>&nbspStatus:</strong>&nbsp&nbsp
                                            <c:choose>
                                                <c:when test="${requestScope.post.status}">
                                                    <span class="label label-success pd-v4-h8">Show</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-danger pd-v4-h8">Hide</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div>
                                            <strong><i class="fa fa-flag" aria-hidden="true"></i>&nbspFeatured:</strong>&nbsp&nbsp
                                            <c:choose>
                                                <c:when test="${requestScope.post.featured==true}">
                                                    <span class="label label-success pd-v4-h8">On</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-danger pd-v4-h8">Off</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="mb-10px">
                                            <strong><i class="fa fa-at" aria-hidden="true"></i>&nbspAuthor:</strong>&nbsp&nbsp<span>${requestScope.post.user.fullname}</span>
                                        </div>
<!--                                        <div class="mb-10px">
                                            <strong><i class="fa fa-upload" aria-hidden="true"></i>&nbspPublish:</strong>&nbsp&nbsp<span><fmt:formatDate type = "date" 
                                                            value = "${requestScope.post.date}" /></span>
                                        </div>-->
                                        <div>
                                            <strong><i class="fa fa-file" aria-hidden="true"></i>&nbspCategory:</strong>&nbsp&nbsp<span>${requestScope.post.postCategory.category.name}</span>
                                        </div>
                                    </div>

                                </div>
                                <hr />
                                <div class="tab-pane fade active in" id="more-information">
                                    <strong>Brief Information: </strong>
                                    <p class="text-indent">
                                        ${requestScope.post.briefInfo}
                                    </p>
                                </div>  
                                <div class="tab-pane fade active in" id="more-information">
                                    <strong>Description: </strong>
                                    <p class="text-indent">
                                        ${requestScope.post.description}
                                    </p>
                                </div>  
                                <hr />
                                <div class="row">
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        <a href="./postlist" class="btn btn-success btn-lg"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i>&nbspBack to List</a>
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        <div class="btn-group pull-right">
                                            <a href="./editPost?id=${requestScope.post.id}" class="btn btn-success btn-lg"><i class="fa-solid fa-pen-to-square" aria-hidden="true"></i>&nbspEdit</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section> <!--/ Main content -->
            </aside><!-- /.right-side -->
        </div>

        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="../../js/jquery.min.js" type="text/javascript"></script>
        <!-- jQuery UI 1.10.3 -->
        <script src="../../assets/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../../assets/js/Director/app.js" type="text/javascript"></script>
    </body>
</html>
