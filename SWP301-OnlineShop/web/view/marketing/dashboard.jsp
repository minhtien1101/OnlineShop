<%-- 
    Document   : productList
    Created on : Jun 6, 2022, 7:48:57 AM
    Author     : Admin
--%>
<%@page import="configs.KeyValuePair"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>  

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <title>Dashboard</title> 
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="../../assets/css/marketing/style.css"/>
        <link rel="stylesheet" type="text/css" href="../../assets/css/marketing/main.css"/>
        <!-- bootstrap 3.0.2 -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!--        <link rel="stylesheet" type="text/css" href="../../assets/css/marketing/main.css">-->
        <!--active button nav in sidebar-->
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
                        <div class="app-title" style="margin: 20px 20px;">
                            <div>
                                <h1><i class="fa fa-dashboard"></i> Dashboard</h1>
                                <p></p>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-6 col-lg-3">
                                <div class="widget-small primary coloured-icon"><i class="icon fa fa-users fa-3x"></i>
                                    <div class="info">
                                        <h4>Customers</h4>
                                        <p><b>${customerNumber}</b></p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="widget-small info coloured-icon"><i class="icon fa fa-product-hunt fa-3x"></i>
                                <div class="info">
                                    <h4>Products</h4>
                                    <p><b>${productNumber}</b></p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="widget-small warning coloured-icon"><i class="icon fa fa-files-o fa-3x"></i>
                                <div class="info">
                                    <h4>Post</h4>
                                    <p><b>${postNumber}</b></p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="widget-small danger coloured-icon"><i class="icon fa fa-commenting fa-3x"></i>
                                <div class="info">
                                    <h4>Feedbacks</h4>
                                    <p><b>${feedbackNumber}</b></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row" >
                        <div class="col-md-12">
                            <div class="tile">
                                <div class="row row-class" id="row1">
                                    <div class="title-dash">
                                        <h3>TOP 5 BEST SELLING PRODUCTS</h3>
                                      
                                    </div>
                                    <div class="form-date">
                                        <form action="/marketing/dashboard" method="post" id="form-search-product">
                                            <input placeholder="Start Date" class="textbox-n height" type="text" onfocus="(this.type = 'date')" id="dt" style="height: 25px;" name="txtStart" value="${startD}">
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input placeholder="End Date" class="textbox-n height" type="text" onfocus="(this.type = 'date')" id="dt1" style="height: 25px;" name="txtEnd" value="${endD}">
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <button class="btn btn-primary" type="button" id="search-product" >Search</button>
                                        </form>
                                        <p id="notefi"></p>
                                    </div>
                                </div>

                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th id="width">#</th>
                                            <th>Product name</th>
                                            <th>Product number</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            int i = 1;
                                        %>

                                        <c:forEach var="u" items="${list}">
                                            <tr class="table-danger ">
                                                <td><%=i%></td>
                                                <td>${(u.key).name}</td>
                                                <td>${u.value}</td>
                                                <%
                                                    i += 1;
                                                %>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                                        <div class="tile">


                        <div class="row row-class" id="row1">
                            <div class="title-dash">
                                <h3>TOP 5 BEST SELLER</h3>
                              
                            </div>
                            <div class="form-date-1">
                                <form action="/marketing/dashboard" method="post" id="form-search-seller">
                                    <input placeholder="Start Date" class="textbox-n height" type="text" onfocus="(this.type = 'date')" id="dtS" style="height: 25px;" name="txtStartSel" value="${startSeller}" onchange="checkDate2()">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <input placeholder="End Date" class="textbox-n height" type="text" onfocus="(this.type = 'date')" id="dt1S" style="height: 25px;" name="txtEndSel" value="${endSeller}" onchange="checkDate2()">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class="btn btn-primary" type="button" id="search-seller">Search</button>
                                </form>
                                <p id="notefi1"></p>
                            </div>
                        </div>

                        <table class="table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Nunber Order</th>
                                    <th>Total Cost</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    int i1 = 1;
                                %>
                                <c:forEach var="u" items="${lstSeller}">
                                    <tr class="table-primary">
                                        <td><%=i1%></td>
                                        <td>${(u.key).fullname}</td>
                                        <td>${(u.key).email}</td>
                                        <td>${u.value}</td>
                                        <td><b><fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${u.value1}"/></b></td>
                                        <%
                                            i1 += 1;
                                        %>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </div>
                    <div class="tile">
                        <h3>TOP 5 BEST CUSTOMER</h3>           
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Mobile</th>
                                    <th>Total Cost</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    int i2 = 1;
                                %>
                                <c:forEach var="u" items="${lstUserTop3}">
                                    <tr class="table-info">
                                        <td><%=i2%></td>
                                        <td>${(u.key).fullname}</td>
                                        <td>${(u.key).email}</td>
                                        <td>${(u.key).mobile}</td>
                                        <td><b><fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${u.value}"/></b></td>
                                        <%
                                            i2 += 1;
                                        %>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </div>
                    

                </section> <!--/ Main content -->
            </aside><!-- /.right-side -->
        </div>


        <!--javascrip-->
        <script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>

        <script src="../../assets/js/marketing/dashboard.js"></script>
        <!--     <script src="../../assets/js/marketing/productList.js"></script>-->
        <script src="../../js/jquery.min.js" type="text/javascript"></script>
        <!-- jQuery UI 1.10.3 -->
        <script src="../../assets/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../../assets/js/Director/app.js" type="text/javascript"></script>



    </body>
</html>
