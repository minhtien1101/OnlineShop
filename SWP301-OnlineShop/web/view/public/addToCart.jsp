<!--<!DOCTYPE html>
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
    /head

    <body>
        <c:set value="${requestScope.productInfomation}" var="product"/>
        <jsp:include page="../home-template/headerProductlist.jsp"/>
        <c:if test="${requestScope.alert != null}">
            <div class="fixed float-end t-55px" id="showAlter" style="    width: 18%;
                 z-index: 1024;
                 right: 23%;
                 top: 6%;
                 position: fixed;
                 ">
                <div class="alert alert-success alert-dismissible fade in" id="alterfade">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close" style="transform: rotate(90deg);
                       font-size: 39px;
                       opacity: 0.2;
                       top: 8px;">&times;</a>
                    ${requestScope.alert}
                </div>
            </div>
        </c:if>
        <section>
            <div class="container">
                <div class="row flex-justify">

                    <div class="col-sm-3 box-shadow height-fit-content border-radius-2" >
                        <div class="left-side">  left-sidebar 
                            <h2 class="title text-center " style="border-bottom: solid 2px; margin-top: 10px;">Category</h2>
                            <form action="productlist" method="get">
                                <div class="panel-group category-products" id="accordian">category-products
                                    <div class="panel panel-default">
                                        <div class="panel-heading">

                                            <div class="search_box">
                                                <input id="search-box" type="text" placeholder="Search..." name="searchBy" value="${requestScope.searchBy}">
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
                                        <c:if test="${ not empty list.listSubCategory }">  check empty of list subcategory with that category 
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
                                </div>/category-products    
                            </form>

                            <div class="panel-group category-products" id="accordian"> 3 least product 
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
                            </div> end two least product  

                        </div>                     
                    </div>
                    PRODUCT DETAILS
                    <div class="col-sm-9 padding-right">
                        <h2 class="title text-center" style="border-bottom: solid 2px;">Product Details</h2>
                        <div class="product-details">product-details
                            <div class="col-sm-5">
                                thumbnail
                                <div class="view-product">
                                    <img src="${product.thumbnail}" alt="" />
                                    <h3>ZOOM</h3>
                                </div>
                            </div>
                            ADD TO CART
                            <div class="col-sm-7">
                                <div class="product-information">/product-information
                                    <form action="addcart" method="post"  id="addcart">
                                        <input type="hidden" value="${product.id}" name="productId">
                                        <input type="hidden" value="${product.name}" name="productName">
                                        <input type="hidden" value="${product.quantity}" name="quantity">
                                        <input type="hidden" value="${product.priceDiscount}" name="price">
                                        <input type="hidden" value="${product.user.id}" name="sellerId">
                                        <input type="hidden" va="hidden" value="${product.thumbnail}" name="thumbnail">
                                        <h2><b>${product.name}</b></h2>
                                        <p>Web ID: ${product.id}</p>
                                        <p>Seller: ${product.user.fullname}</p>
                                        <p> 
                                            Description:
                                            ${product.description}
                                        </p>
                                        <p>
                                            <label>Price: </label>
                                            <span class="text-line-through">
                                                <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${product.price}"/>
                                            </span>
                                            <span class="text-danger">
                                                <fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${product.priceDiscount}"/>
                                            </span>
                                        </p>
                                        <p> <label>Total Quantity: ${product.quantity}</label> </p>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <p> 
                                                    <label>Choice Your Quantity: </label>
                                                    <input type="number" id="quantityOrder" name="quantityOrder" style="width: 4em;" 
                                                           min="1" max="${product.quantity}" value = "1" required>
                                                </p>
                                                <button type="submit" style="width: 10em; " class="form-control" onsubmit ="addToCart()">
                                                    <i class="fa fa-shopping-cart"></i>
                                                    Add to cart
                                                </button> 
                                            </div>
                                        </div> comment 
                                    </form>
                                </div>/product-information
                            </div> END ADD TO CART 
                        </div>
                    </div>
                    END PRODCUT DETAILS
                </div>
            </div>
        </section>
        <script language="JavaScript" type="text/javascript">
            function addToCart() {
                
                var result = confirm("Are you sure to add this product to your cart?");
                if (result) {
//                    window.location.href = "addcart"
                    document.getElementById("addcart").submit();
                }
            }
            setTimeout(function () {
                const element = document.getElementById('showAlter');
                element.remove();
            }, 3000);
        </script>
        <jsp:include page="../home-template/footer.jsp"/>
        <script src="../../assets/public/js/jquery.js"></script>
        <script src="../../assets/public/js/bootstrap.min.js"></script>
        <script src="../../assets/public/js/jquery.scrollUp.min.js"></script>
        <script src="../../assets/public/js/price-range.js"></script>
        <script src="../../assets/public/js/jquery.prettyPhoto.js"></script>
        <script src="../../assets/public/js/main.js"></script>
        <script src="../../assets/js/home/home.js"></script>
    </body>

</html>-->