<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>My orders | E-shopper</title>
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

        <link href="../../assets/public/css/style.css" rel="stylesheet">
        <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

    </head>
    <!--/head-->

    <body>
        <jsp:include page="../home-template/headerProductlist.jsp"/>
        <section>
            <div class="container">
                <div class="row flex-justify">
                    <jsp:include page="../home-template/sidebarForProductList.jsp"/>
                    <div class="col-sm-9 padding-right">
                        <div class="features_items">
                            <!--features_items-->
                            <h2 class="title text-center" style="border-bottom: solid 2px; margin-top: 10px">Your orders</h2>
                            <div id ="customToolbar">
                                <span>Please set date range to view your data:</span></br>
                                <div id="reportrange" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
                                    <i class="fa fa-calendar"></i>&nbsp;
                                    <span></span> <i class="fa fa-caret-down"></i>
                                </div>
                            </div>
                            <!-- Order table -->
                            <table
                                data-toggle="table"
                                data-pagination="true"
                                data-sortable="true"
                                data-search="true"
                                data-toolbar="#customToolbar">

                                <thead>
                                    <tr>
                                        <th data-sortable="true">Order ID</th>
                                        <th data-sortable="true">Ordered Date</th>
                                        <th data-sortable="true">Product (First product name)</th>
                                        <th data-sortable="true">Number of products</th>
                                        <th data-sortable="true">Total cost</th>
                                        <th data-sortable="true"data-align="center">Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.orders}" var="o">
                                        <tr>
                                            <td><a href="orderInfor?orderID=${o.id}">${o.id}</a></td>
                                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${o.date}"/></td>
                                            <td>${o.products[0].name}</td>
                                            <td>${o.numproducts}</td>
                                            <td><fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${o.totalcost}"/></td>
                                            <c:if test="${o.status == 0}">
                                                <td>
                                                    <span class="label label-default">Cancelled</span>
                                                </td>
                                            </c:if>
                                            <c:if test="${o.status == 1}">
                                                <td>
                                                    <span class="label label-warning">Submitted</span>
                                                </td>
                                            </c:if>
                                            <c:if test="${o.status == 2}">
                                                <td>
                                                    <span class="label label-info">Processing</span>
                                                </td>
                                            </c:if>
                                            <c:if test="${o.status == 3}">
                                                <td>
                                                    <span class="label label-primary">Shipping</span>
                                                </td>
                                            </c:if>
                                            <c:if test="${o.status == 4}">
                                                <td>
                                                    <span class="label label-success">Completed</span>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach> 
                                </tbody>
                            </table>
                            <!-- Order table -->
                        </div>

                        <!--features_items-->
                    </div>
                </div>
            </div>
        </section>





        <jsp:include page="../home-template/footer.jsp"/>
        <script src="../../assets/public/js/jquery.js"></script>
        <script src="../../assets/public/js/bootstrap.min.js"></script>
        <script src="../../assets/public/js/jquery.scrollUp.min.js"></script>
        <script src="../../assets/public/js/price-range.js"></script>
        <script src="../../assets/public/js/jquery.prettyPhoto.js"></script>
        <script src="../../assets/public/js/main.js"></script>
        <script src="../../assets/js/home/home.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.4/jquery.validate.min.js" integrity="sha512-FOhq9HThdn7ltbK8abmGn60A/EMtEzIzv1rvuh+DqzJtSGq8BRdEN0U+j0iKEIffiw/yEtVuladk6rsG4X6Uqg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    </body>

</html>