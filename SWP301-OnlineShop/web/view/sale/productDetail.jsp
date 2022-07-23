<%-- 
    Document   : productList
    Created on : Jun 6, 2022, 7:48:57 AM
    Author     : Admin
--%>
<%@page import="model.Image"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sale | Product Details</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <!-- bootstrap 3.0.2 -->
        <!--<link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />-->
        <!-- font Awesome -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

        <!-- Theme style -->
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
        <!--css-->
        <link href="../../assets/css/admin/userList.css" rel="stylesheet" type="text/css"/>
        <link href="../../assets/css/admin/main.css" rel="stylesheet" type="text/css"/>

          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <!--active button nav in sidebar-->
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <%
            ArrayList<Image> attchedImg = ((Product) request.getAttribute("product")).getImage();
            if (attchedImg == null || attchedImg.size() == 0) {
                Image image = new Image();
                image.setImage("#");
                attchedImg.add(image);
                attchedImg.add(image);
            }
        %>


    </head>


    <body class="skin-black">

        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../sale-template/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../sale-template/sideBar.jsp"></jsp:include>
                <!-- Right side. contains main content -->
                <aside class="right-side">
                    <!-- Main content -->
                    <section class="content ">

                        <div class="product-content product-wrap clearfix product-deatil">
                            <div class="row">
                                <div class="col-md-5 col-sm-12 col-xs-12">
                                    <div class="product-image">

                                        <div id="myCarousel" class="carousel slide" data-ride="carousel">
                                            <!-- Indicators -->
                                            <ol class="carousel-indicators">
                                                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                                                <li data-target="#myCarousel" data-slide-to="1"></li>
                                                <li data-target="#myCarousel" data-slide-to="2"></li>
                                            </ol>

                                            <!-- Wrapper for slides -->
                                        <div class="carousel-inner">
                                            <div class="item active">
                                                <img src="${requestScope.product.thumbnail}" alt="thumbnail" style="height: 300px; width: 100%;">
                                            </div>
                                            <div class="item">
                                                 <img src="<%= attchedImg.get(1).getImage()%>" alt="thumbnail" style="height: 300px; width: 100%;">
                                            </div>
                                            <div class="item">
                                                <img src="<%= attchedImg.get(0).getImage()%>" alt="thumbnail" style="height: 300px; width: 100%;">
                                            </div>
                                        </div>

                                        <!-- Left and right controls -->
                                        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                                            <span class="glyphicon glyphicon-chevron-left"></span>
                                            <span class="sr-only">Previous</span>
                                        </a>
                                        <a class="right carousel-control" href="#myCarousel" data-slide="next">
                                            <span class="glyphicon glyphicon-chevron-right"></span>
                                            <span class="sr-only">Next</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-md-offset-1 col-sm-12 col-xs-12">
                                <h2 class="name" style="text-transform: uppercase; font-weight: 700;">
                                    ${requestScope.product.name}
                                </h2>
                                <hr>
                                <div class="price-container" style="display: flex; justify-content: space-between;">
                                    <div>
                                        <div style="margin-bottom: 10px;">
                                            <strong><i class="fa fa-info-circle" aria-hidden="true"></i>&nbspStatus:</strong>&nbsp&nbsp
                                            <c:choose>
                                                <c:when test="${requestScope.product.status}">
                                                    <span class="label label-success" style="padding: 4px 8px">Show</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-danger" style="padding: 4px 8px">Hide</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div>
                                            <strong><i class="fa fa-flag" aria-hidden="true"></i>&nbspFeatured:</strong>&nbsp&nbsp
                                            <c:choose>
                                                <c:when test="${requestScope.product.featured==true}">
                                                    <span class="label label-success" style="padding: 4px 8px">On</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-danger" style="padding: 4px 8px">Off</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div>
                                        <div style="margin-bottom: 10px;">
                                            <strong><i class="fa fa-at" aria-hidden="true"></i>&nbspSeller:</strong>&nbsp&nbsp<span>${requestScope.product.user.fullname}</span>
                                        </div>
                                        <div style="margin-bottom: 10px;">
                                            <strong><i class="fa fa-upload" aria-hidden="true"></i>&nbspPublish:</strong>&nbsp&nbsp<span><fmt:formatDate type = "date" />${requestScope.product.date}</span>
                                        </div>
                                        <div>
                                            <strong><i class="fa fa-file" aria-hidden="true"></i>&nbspCategory:</strong>&nbsp&nbsp<span>${requestScope.product.subCategory.category.name}</span>
                                        </div>

                                    </div>
                                </div>
                                <hr />
                                <div class="tab-pane fade active in" id="more-information">
                                    <strong>Description Title:</strong>
                                    <p style="text-indent: 1.5rem; font-size: 15px;">
                                        ${requestScope.product.description}
                                    </p>
                                </div>  

                                <div class="tab-pane fade active in mb-10" id="more-information">
                                    <strong>Quantity</strong>
                                    <span style="text-indent: 1.5rem; font-size: 15px;">
                                        ${requestScope.product.quantity}
                                    </span>
                                </div> 

                                <div class="tab-pane fade active in" id="more-information">
                                    <strong>Price</strong>
                                    <span style="text-indent: 1.5rem; font-size: 15px;">
                                        ${requestScope.product.price}
                                    </span>
                                </div>

                                <hr />
                                <div class="row">
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        <a href="./productlist" class="btn btn-success btn-lg"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i>&nbspBack to List</a>
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        <div class="btn-group pull-right">
                                            <a href="./editProductInfo?id=${requestScope.product.id}" class="btn btn-success btn-lg"><i class="fa-solid fa-pen-to-square" aria-hidden="true"></i>&nbspEdit</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section> <!--/ Main content -->
            </aside><!-- /.right-side -->
        </div>

        <!--javascrip-->
        <script src="../../assets/js/marketing/productList.js"></script>
        <script src="../../assets/js/marketing/addNewProduct.js"></script>
        <script src="../../assets/js/marketing/editProduct.js"></script>
        <!--javascrip-->
        <script src="../../assets/js/admin/main.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
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
