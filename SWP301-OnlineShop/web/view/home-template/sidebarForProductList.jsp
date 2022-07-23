<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-sm-3 box-shadow height-fit-content border-radius-2" >
    <div class="left-side"> <!-- left-sidebar -->
        <h2 class="title text-center " style="border-bottom: solid 2px; margin-top: 10px;">Category</h2>
        <form action="productlist" method="get">
            <div class="panel-group category-products" id="accordian"><!--category-products-->
                <div class="panel panel-default">
                    <div class="panel-heading">

                        <div class="search_box">
                            <!--<input id="search-box" type="text" placeholder="Search..." name="searchBy" value="${requestScope.searchBy}">-->
                            <input type="text" name="searchBy" value="${param.searchBy}"  placeholder="Search"/>
                        </div>
                    </div>
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="productlist" ${(requestScope.categoryID == 0) ? "class=\"active-category\"" : ""}>
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

                                    <a data-toggle="collapse" data-parent="#accordian" href="#${list.id}" ${(requestScope.categoryID == list.id)?"":"class=\"collapsed\""} >
                                        <span class="badge pull-right"><i class="fa fa-plus"></i></span>
                                            ${list.name}

                                    </a>
                                </h4>
                            </div>
                            <div id="${list.id}" class="panel-collapse ${(requestScope.categoryID == list.id)?"in height-auto":"collapse"}">
                                <div class="panel-body">
                                    <ul>
                                        <c:forEach items="${list.listSubCategory}" var="listSub">
                                            <li><a href="productlist?subCategory=${listSub.id}&searchBy=${searchBy}&categoryID=${list.id}" ${(requestScope.subCategory == listSub.id)?"class=\"active-category\"":""} 
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


        <div class="panel-group category-products" id="accordian"><!-- 2 least product -->
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
                                <!--<p class="break-down-line">${leastProduct.description}</p>-->
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