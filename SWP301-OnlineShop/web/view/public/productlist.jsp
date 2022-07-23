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
        <!-- font Awesome -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="../../assets/public/css/style.css" rel="stylesheet">
        <link href="../../assets/css/admin/feedback.css" rel="stylesheet">
    </head>
    <!--/head-->

    <body>
        <jsp:include page="../home-template/headerProductlist.jsp"/>
        <section>
            <div class="container">
                <div class="row flex-justify">
                    <jsp:include page="../home-template/sidebarForProductList.jsp"/>
                    <!--PRODUCT LIST-->
                    <div class="col-sm-9 padding-right">
                        <div class="features_items">
                            <!--features_items-->
                            <h2 class="title text-center" style="border-bottom: solid 2px; margin-top: 10px">Product List</h2>
                            <!--Show product-->
                            <c:forEach items="${requestScope.listProducts}" var="product" >
                                <div class="col-sm-4">
                                    <div class="product-image-wrapper">
                                        <div class="single-products">
                                            <div class="productinfo text-center">
                                                <a href="productdetails?productID=${product.id}">
                                                    <img style="width: 200px; height: 200px;" src="${product.thumbnail}" alt="" />
                                                </a>

                                                <h2 class="break-down-line">${product.name}</h2>
                                                <!--<p class="break-down-line">${product.description}</p>-->
                                                <p>
                                                    <span class="text-line-through">
                                                        <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${product.price}"/>
                                                    </span>
                                                    <span class="text-danger">
                                                        <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${product.priceDiscount}"/>
                                                    </span>
                                                </p>
                                                <c:if test="${product.quantity > 0 && (sessionScope.user.role.id != 3 && sessionScope.user.role.id != 2&&sessionScope.user.role.id != 1)}">
                                                    <button  class="btn btn-default add-to-cart"  onclick="addToCartFunction2(${product.id},${product.quantity},${sessionScope.user.id});" >
                                                        <i class="fa fa-shopping-cart"></i>
                                                        Add to cart
                                                    </button>
                                                </c:if>
                                                <!--Btn to See List Feedback-->
                                                <button class="btn btn-default  add-to-cart">
                                                    <a style="color: black" href="productdetails?productID=${product.id}" >
                                                        <i class="glyphicon glyphicon-thumbs-up"></i>
                                                        Feedback</a>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <!--END PRODCUT LIST-->
                </div>
                <div class="pagging">
                    <ul class="pagination pull-right">
                        <c:if test="${requestScope.totalpage > 1}">
                            <li><a href="productlist?page=1&searchBy=${searchBy}&subCategory=${listSub.id}">Frist</a></li>
                            </c:if>
                            <c:forEach begin="1" end="${requestScope.totalpage}" var="page">
                            <li class="${pageindex == page ? "active =" : ""}" ><a href="productlist?page=${page}&searchBy=${searchBy}&subCategory=${listSub.id}">${page}</a></li>    
                            </c:forEach>
                            <c:if test="${requestScope.totalpage > 1}">
                            <li><a href="productlist?page=${requestScope.totalpage}&searchBy=${searchBy}&subCategory=${listSub.id}">Last</a></li>
                            </c:if>
                    </ul>
                </div>

            </div>
            <div id="add-to-cart-alter"></div>
        </section>

        <jsp:include page="../home-template/footer.jsp"/>
        <script src="../../assets/js/home/productList.js"></script>
        <script src="../../assets/public/js/jquery.js"></script>
        <script src="../../assets/public/js/bootstrap.min.js"></script>
        <script src="../../assets/public/js/jquery.scrollUp.min.js"></script>
        <script src="../../assets/public/js/price-range.js"></script>
        <script src="../../assets/public/js/jquery.prettyPhoto.js"></script>
        <script src="../../assets/public/js/main.js"></script>
        <script src="../../assets/js/home/home.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.4/jquery.validate.min.js" integrity="sha512-FOhq9HThdn7ltbK8abmGn60A/EMtEzIzv1rvuh+DqzJtSGq8BRdEN0U+j0iKEIffiw/yEtVuladk6rsG4X6Uqg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    </body>

</html>