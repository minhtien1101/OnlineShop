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
        <c:set value="${requestScope.productInfomation}" var="product"/>
        <jsp:include page="../home-template/headerProductlist.jsp"/>
        <section>
            <div class="container">
                <div class="row flex-justify">
                    <div class="col-sm-3 box-shadow height-fit-content border-radius-2" >
                        <div class="left-side"> <!-- left-sidebar -->
                            <h2 class="title text-center " style="border-bottom: solid 2px; margin-top: 10px;">Category</h2>
                           <form action="productlist" method="get">
                                <div class="panel-group category-products" id="accordian"><!--category-products-->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">

                                            <div class="search_box">
                                                <!--<input id="search-box" type="text" placeholder="Search..." name="searchBy" value="${requestScope.searchBy}">-->
                                                 <input type="text" name="searchBy" value="${requestScope.searchBy}"  placeholder="Search"/>
                                            </div>
                                        </div>
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a href="productlist">
                                                    <span class="badge pull-right"></span>
                                                    All Category
                                                </a>
                                            </h4>
                                        </div>
                                    </div>
                                    <c:forEach items="${requestScope.listCategorys}" var="list">
                                        <c:if test="${ not empty list.listSubCategory }"> <!-- check empty of list subcategory with that category -->
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">

                                                        <a data-toggle="collapse" data-parent="#accordian" href="#${list.id}">
                                                            <span class="badge pull-right"><i class="fa fa-plus"></i></span>
                                                                ${list.name}
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="${list.id}" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <ul>
                                                            <c:forEach items="${list.listSubCategory}" var="listSub">
                                                                <li><a href="productlist?subCategory=${listSub.id}&searchBy=${searchBy}" ${(requestScope.idCategory == listSub.id)?"":"class=\"collapsed\""} 
                                                                       >${listSub.name} </a></li> 
                                                                </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div><!--/category-products-->    
                            </form>

                            <div class="panel-group category-products" id="accordian"><!-- 3 least product -->
                                <h2 class="title text-center" style="border-bottom: solid 2px;">Latest Product</h2>
                                <%--<c:set var="leat" value="" />--%>
                                <c:if test="${requestScope.leastProduct != null}">
                                    <c:forEach items="${requestScope.leastProduct}" var="leastProduct">
                                        <div class="product-image-wrapper">
                                            <div class="single-products">
                                                <div class="productinfo text-center">
                                                    <a href="productdetails?productID=${leastProduct.id}">
                                                        <img src="${leastProduct.thumbnail}" alt="" />
                                                    </a>
                                                    <h2 class="break-down-line">${leastProduct.name}</h2>
                                                    <p class="break-down-line">${leastProduct.description}</p>
                                                    <p>
                                                        <span class="text-line-through">
                                                            <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${leastProduct.price}"/>
                                                        </span>
                                                        <span class="text-danger">
                                                            <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${leastProduct.priceDiscount}"/>
                                                        </span>
                                                    </p>
                                                    <a href="productdetails?productID=${leastProduct.id}" class="btn btn-default add-to-cart">More Information</a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div><!-- end two least product --> 

                        </div>                     
                    </div>

                    <!--PRODUCT DETAILS-->
                    <div class="col-sm-9 padding-right">
                        <c:if test="${not empty requestScope.listFeedbacks }">
                            <!-- FEEDBACK -->
                            <h2 class="title text-center" style="border-bottom: solid 2px; margin-top: 10px">Feedback</h2>
                            <div class="category-tab shop-details-tab">
                                <div class="tab-pane fade active in" id="reviews">
                                    <c:forEach items="${requestScope.listFeedbacks}" var="feedback">
                                        <div class="col-sm-12" style="border-bottom: solid 1px; margin-top: 20px;">
                                            <ul>

                                                <li><a><i class="fa fa-user"></i>${feedback.user.fullname}</a></li>

                                            </ul>
                                            <ul>
                                                Rating: ${feedback.start} star
                                            </ul>
                                            <p>${feedback.comment}</p>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="pagging">
                                <ul class="pagination pull-right">
                                    <c:if test="${requestScope.totalpage > 1}">
                                        <li><a href="productdetails?page=1&productID=${product.id}">Frist</a></li>
                                        </c:if>
                                        <c:forEach begin="1" end="${requestScope.totalpage}" var="page">
                                        <li class="${pageindex == page ? "active =" : ""}" ><a href="productdetails?page=${page}&productID=${product.id}">${page}</a></li>    
                                        </c:forEach>
                                        <c:if test="${requestScope.totalpage > 1}">
                                        <li><a href="productdetails?page=${requestScope.totalpage}&productID=${product.id}">Last</a></li>
                                        </c:if>
                                </ul>
                            </div>
                            <!--/END FEEDBACK-->
                        </c:if>
                        <c:if test="${empty requestScope.listFeedbacks}">
                            <!-- FEEDBACK -->
                            <h2 class="title text-center" style="border-bottom: solid 2px; margin-top: 10px">Feedback</h2>
                            <div style="text-align: center;">
                                <b>This Product Have No Feedback</b>
                            </div>
                            <!--/END FEEDBACK-->
                        </c:if>
                            </div>
                        </div>

                    </div>
                    <!--END PRODCUT DETAILS-->
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
    </body>

</html>