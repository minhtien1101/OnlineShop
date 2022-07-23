<%-- 
    Document   : backlink
    Created on : Jun 13, 2022, 4:36:07 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../../assets/public/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../assets/public/css/font-awesome.min.css" rel="stylesheet">
        <link href="../../assets/public/css/prettyPhoto.css" rel="stylesheet">
        <link href="../../assets/public/css/price-range.css" rel="stylesheet">
        <link href="../../assets/public/css/animate.css" rel="stylesheet">
        <link href="../../assets/public/css/main.css" rel="stylesheet">
        <link href="../../assets/public/css/responsive.css" rel="stylesheet">

        <link href="../../assets/css/back-link/style.css" rel="stylesheet">
        <title>Backlink | E-Shopper</title>
    </head>
    <body>
        <c:if test="${requestScope.alter != null}">
            <div class="fixed float-end t-55px" id="showAlter" style="    width: 18%;
                 z-index: 1024;
                 right: 19%;
                 top: 6%;
                 position: fixed;
                 ">
                <div class="alert alert-success alert-dismissible fade in" id="alterfade">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close" style="transform: rotate(90deg);
                       font-size: 39px;
                       opacity: 0.2;
                       top: 8px;">&times;</a>
                    ${requestScope.alter} 
                </div>
            </div>
        </c:if>
        <header id="header">
            <div class="header-top">
                <!--header-top-->
                <div class="container">
                    <div class="row" style="padding: 5px 0px; box-shadow: 0px 4px 5px -3px #fe980f;">
                        <div class="col-sm-4">
                            <div class="logo pull-left">
                                <a href="./home"><img src="../../assets/public/images/home/logo.png" alt="" /></a>
                            </div>
                        </div>
                        <div class="col-sm-8">
                            <div class="shop-menu pull-right">
                                <ul class="nav navbar-nav">
                                    <c:if test="${sessionScope.user.role.id == 4}">
                                        <li>
                                            <a href="cartDetails">
                                                <i class="fa fa-shopping-cart cart-icon"></i> Cart 
                                            </a>
                                        </li>
                                    </c:if>

                                    <c:if test="${sessionScope.user == null}">
                                        <li><a href="login" id="button"><i class="fa fa-lock"></i> Login</a></li>
                                        </c:if>

                                    <c:if test="${sessionScope.user != null}">
                                        <li class="dropdown">
                                            <a href="#">
                                                <i class="fa fa-user"></i>
                                                ${sessionScope.user.fullname}
                                                <i class="fa fa-angle-down"></i>
                                            </a>
                                            <ul role="menu" class="sub-menu">
                                                <li>
                                                    <!--<a href="user/profile" class="text-none-underline">Profile</a>-->
                                                    <a href="#" class="text-none-underline" data-toggle="modal" data-target="#myModalProfilfe">Profile</a>
                                                </li>

                                                <c:if test="${sessionScope.user.role.id == 1}">
                                                    <li>
                                                        <a href="admin/userList" class="text-none-underline">Admin manage</a>
                                                    </li>
                                                </c:if>

                                                <c:if test="${sessionScope.user.role.id == 2}">
                                                    <li>
                                                        <a href="/marketing/productlist" class="text-none-underline">Maketing manage</a>
                                                    </li>
                                                </c:if>

                                                <c:if test="${sessionScope.user.role.id == 3 || sessionScope.user.role.id  == 21}">
                                                    <li>
                                                        <a href="sale/productlist" class="text-none-underline">Sale manage</a>
                                                    </li>
                                                </c:if>  

                                                <c:if test="${sessionScope.user.role.id == 4}">
                                                    <li>
                                                        <a href="myorders" class="text-none-underline">My orders</a>
                                                    </li>
                                                </c:if>
                                                <li>
                                                    <a href="#" class="text-none-underline" data-toggle="modal" data-target="#myModal">Change password</a>
                                                </li>

                                                <li>
                                                    <a href="logout" class="text-none-underline">Logout</a>
                                                </li>
                                            </ul>
                                        </li>                   
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--/header-top-->

            <div class="header-bottom">
                <!--header-bottom-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-9">
                            <div class="navbar-header">
                                <button type="button" class="navbar-toggle" data-toggle="collapse"
                                        data-target=".navbar-collapse">
                                    <span class="sr-only">Toggle navigation</span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                </button>
                            </div>
                            <div class="mainmenu pull-left">
                                <ul class="nav navbar-nav collapse navbar-collapse">
                                    <li><a href="home" ${(requestScope.active == "home")?"class=\"active\"":""}>Home</a></li>
                                    <li><a href="bloglist" ${(requestScope.active == "blog")?"class=\"active\"":""}>Blog</a></li>
                                    <li><a href="productlist" ${(requestScope.active == "productList")?"class=\"active\"":""}>Products</a></li>
                                </ul>
                            </div>
                        </div>
                        <form action="backlink" method="get">
                            <div class="col-sm-3">
                                <div class="search_box pull-right">
                                    <input id="search-box" type="text" placeholder="Search" value="${search}" name="searchBy"/>
                                    <input type="hidden" value="${sid}" name="sid">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>  
            <!--/header-bottom-->

            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog w-30percent" role="document"">
                    <div class="modal-content pd-20px">
                        <div class="modal-header text-center" >
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
                            <h4 class="modal-title fw-bolder-fz-20" id="myModalLabel">Change Password</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <!-- Success alert -->
                                <div class="alert alert-success" id="alertSuccess">
                                    <strong>Success!</strong> ${requestScope.msg}
                                </div>

                                <!-- Failed alert -->
                                <div class="alert alert-danger" id="alertDanger">
                                    <strong>Danger!</strong> ${requestScope.msg}
                                </div>
                                <form id ="changePassForm">
                                    <div class="form-group">
                                        <label for="oldPassword">Old Password</label>
                                        <input type="password" class="form-control" id="oldPassword" placeholder="Enter old password" name="oldPassword">
                                    </div>
                                    <div class="form-group">
                                        <label for="newPassword">New Password</label>
                                        <input type="password" class="form-control" id="newPassword" placeholder="Enter new password" name="newPassword">
                                    </div>
                                    <div class="form-group">
                                        <label for="reEnterNewPassword">Re-enter new password</label>
                                        <input type="password" class="form-control" id="reEnterNewPassword" placeholder="Re-enter new password" name="reEnterPass">
                                    </div>
                                    <div class="btn-modal flex-justify-end-wrap">
                                        <button type="button" class="btn btn-primary bg-darkgray-radius" data-dismiss="modal">Cancel</button>
                                        <button type="button" class="btn btn-primary mg-l-3percent-radius" id="saveNewPassword">Save changes</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="../home-template/userProfile.jsp"/>

        </header>

        <div >
            <div style="margin: 0 auto; width: 80%;">
                <img src="../../assets/img/backlink1.jpg"/>
                <div class="brands_product "> 
                    <!--products_featured-->
                    <h2 class="title text-center">Product Sale</h2>
                    <c:forEach items="${listProduct}" var="i">
                        <div class="col-sm-4">
                            <div class="product-image-wrapper">
                                <div class="single-products">
                                    <div class="productinfo text-center">
                                        <a href="#${i.id}">
                                            <img class="img-height" src="${i.thumbnail}" alt="" style="width: 200px; height: auto;"/>
                                        </a>
                                        <h2 class="break-down-line">${i.name}</h2>
                                        <p>
                                            <span class="text-line-through">
                                                <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${i.price}"/>
                                            </span>
                                            <span class="text-danger">
                                                <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${i.price - (i.price*i.discount/100)}"/>
                                            </span>
                                        </p>
                                        <a href="productdetails?productID=${i.id}" class="btn btn-default add-to-cart">Show</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
