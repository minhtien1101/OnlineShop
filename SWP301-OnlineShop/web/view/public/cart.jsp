<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Product List</title>
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

    </head>
    <!--/head-->
    <body>

        <jsp:include page="../home-template/headerProductlist.jsp"/>
        <section id="cart_items">
            <div class="container" style="margin-top: 4%; margin-bottom: 8%;" >
                <h2 class="title text-center" style="border-bottom: solid 3px;">Cart List</h2>



                <c:if test="${not empty requestScope.listCarts}" >
                    <div class="table-responsive cart_info">
                        <table class="table table-condensed">
                            <thead>
                                <tr class="cart_menu">
                                    <td class="image">Item</td>
                                    <td class="description"></td>
                                    <td class="price">Price</td>
                                    <td class="quantity">Quantity</td>
                                    <td class="total">Total</td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.listCarts}" var="cart">
                                    <tr>
                                        <td class="cart_product">
                                            <a href="#"><img  style="width: 120px; height: 120px;" src="${cart.thumbnail}" alt=""></a>
                                        </td>
                                        <td class="cart_description">
                                            <h4><a href="#">${cart.product.name}</a></h4>
                                            <p>ID: ${cart.product.id}</p>
                                        </td>
                                        <td class="cart_price">
                                            <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${cart.price}"/>
                                        </td>
                                        <td class="cart_quantity" >
                                            <p>${cart.quantityOrder}</p>
                                        </td>
                                        <td class="cart_price">
                                            <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${cart.total}"/>
                                        </td>
                                        <!--                                        <td class="cart_delete">
                                                                                    <a class="cart_quantity_delete" href=""><i class="fa fa-times"></i></a>
                                                                                </td>-->
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${ empty requestScope.listCarts}" >
                    <div style="text-align: center;">
                        <b>You Have No Product In Your Cart</b>
                    </div>
                </c:if>

                <div class="pagging">
                    <ul class="pagination pull-right">
                        <c:if test="${requestScope.totalpage > 1}">
                            <li><a href="cartlist?page=1">Frist</a></li>
                            </c:if>
                            <c:forEach begin="1" end="${requestScope.totalpage}" var="page">
                            <li class="${pageindex == page ? "active =" : ""}" ><a href="cartlist?page=${page}">${page}</a></li>    
                            </c:forEach>
                            <c:if test="${requestScope.totalpage > 1}">
                            <li><a href="cartlist?page=${requestScope.totalpage}">Last</a></li>
                            </c:if>
                    </ul>
                </div>
            </div>

        </section> <!--/#cart_items-->

        <jsp:include page="../home-template/footer.jsp"/>
        <script src="../../assets/public/js/jquery.js"></script>
        <script src="../../assets/public/js/bootstrap.min.js"></script>
        <script src="../../assets/public/js/jquery.scrollUp.min.js"></script>
        <script src="../../assets/public/js/price-range.js"></script>
        <script src="../../assets/public/js/jquery.prettyPhoto.js"></script>
        <script src="../../assets/public/js/main.js"></script>
        <script src="../../assets/js/home/home.js"></script>
    </body>

</html>