<%-- 
    Document   : bloglist.jsp
    Created on : May 26, 2022, 8:54:44 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../../assets/public/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../assets/public/css/font-awesome.min.css" rel="stylesheet">
        <link href="../../assets/public/css/prettyPhoto.css" rel="stylesheet">
        <link href="../../assets/public/css/price-range.css" rel="stylesheet">
        <link href="../../assets/public/css/animate.css" rel="stylesheet">
        <link href="../../assets/public/css/main.css" rel="stylesheet">
        <link href="../../assets/public/css/responsive.css" rel="stylesheet">
        <title>Blog list | E-Shopper</title>
    </head>
    <body>
        <jsp:include page="../home-template/header.jsp"/>
        <input type="hidden" value="${requestScope.searchContent}" id="search-content"/>
        <section>
            <div class="container">
                <div class="row flex-justify">
                    <div class="col-sm-3 pd-top-15 box-shadow height-fit-content border-radius-20">
                        <div class="left-side"> <!-- left-sidebar -->
                            <h2 class="title text-center" style="border-bottom: solid 2px;">Category</h2>
                            <div class="panel-group category-products" id="accordian"><!--category-productsr-->
                                <div class="panel panel-default">
                                    <input type="hidden" value="${requestScope.idCategory}" id="search-category"/>
                                    <input type="hidden" value="${requestScope.idSubCategory}" id="search-subcategory"/>
                                    
                                    <div class="panel-heading">
                                        <h4 class="panel-title"><a ${(requestScope.idCategory == -1)?"class=\"active-category\"":""} id="all-category" href="#">All Category</a></h4>
                                    </div>
                                </div>
                                <c:forEach items="${requestScope.listAllCateogry}" var="i">
                                    <c:if test="${i.listSubPost != null}">                                                                         
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordian" href="#${i.id}" ${(requestScope.idCategory == i.id)?"":"class=\"collapsed\""}>
                                                        <span class="badge pull-right"><i class="fa fa-plus"></i></span>
                                                            ${i.name}
                                                    </a>
                                                </h4>
                                            </div>
                                            <c:if test="${i.listSubPost != null}">
                                                <div id="${i.id}" class="panel-collapse ${(requestScope.idCategory == i.id)?"in height-auto":"collapse"}">
                                                    <div class="panel-body">
                                                        <ul>
                                                            <c:forEach items="${i.listSubPost}" var="s">
                                                                <li><a href="bloglist?category=${i.id}&subcategory=${s.id}" ${(requestScope.idSubCategory == s.id)?"class=\"active-category\"":""}>${s.name}</a></li>
                                                                </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                    </c:if>
                                </c:forEach>

                                <%--<c:forEach items="${requestScope.listAllCateogry}" var="i">--%>
                                <!--<div class="panel panel-default">-->
                                <!--<div class="panel-heading">-->
                                    <!--<h4 class="panel-title"><a ${(requestScope.idCategory == i.id)?"class=\"active-category\"":""} id="${i.id}" href="#">${i.name}</a></h4>-->
                                <!--</div>-->
                                <!--</div>-->
                                <%--</c:forEach>--%>
                            </div><!--/category-products-->
                            <div class="panel-group category-products" id="accordian">
                                <h2 class="title text-center" style="border-bottom: solid 2px;">Latest Posts</h2>
                                <c:if test="${requestScope.listTopLatestPost != null}">
                                    <c:forEach items="${requestScope.listTopLatestPost}" var="i">
                                        <div class="panel panel-default">
                                            <div class="col-sm-12">
                                                <div class="product-image-wrapper">
                                                    <div class="single-products">
                                                        <div class="productinfo text-center">
                                                            <a href="blogDetail?blogId=${i.id}">
                                                                <img src="${i.thumbnail}" alt="" />
                                                            </a>
                                                            <h2 class="break-down-line">${i.title}</h2>
                                                            <p class="break-down-line">${i.briefInfo}</p>
                                                            <a href="blogDetail?blogId=${i.id}" class="btn btn-default add-to-cart">Read more</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>                     
                    </div>
                    <div class="col-sm-8 pd-top-15 box-shadow border-radius-20 height-fit-content">
                        <!--blog-post-area-->
                        <div class=" features_items">
                            <h2 class="title text-center" style="border-bottom: solid 2px;">Post List</h2>
                            <c:forEach items="${requestScope.listPostFiltered}" var="i">
                                <!--single-blog-post-->
                                <div class="col-sm-4">
                                    <div class="product-image-wrapper">
                                        <div class="single-products">
                                            <div class="productinfo text-center">
                                                <a href="blogDetail?blogId=${i.id}">
                                                    <img class="img-height-255" src="${i.thumbnail}" alt="" />
                                                </a>
                                                <h2 class="break-down-line">${i.title}</h2>
                                                <p class="break-down-line" >${i.briefInfo}</p>
                                                <c:if test="${requestScope.idCategory == -1}">
                                                    <a  class="btn btn-default add-to-cart" href="blogDetail?blogId=${i.id}">Read More</a>
                                                </c:if>
                                                <c:if test="${requestScope.idCategory != -1}">
                                                    <a  class="btn btn-default add-to-cart" href="blogDetail?blogId=${i.id}&category=${requestScope.idCategory}&subcategory=${requestScope.idSubCategory}">Read More</a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--                                    <div class="post-meta">
                                                                        <ul>
                                                                            <li><i class="fa fa-user"></i>${i.user.fullname}</li>
                                                                            <li><i class="fa fa-calendar"></i>${i.date}</li>
                                                                            <li><i class="fa fa-caret-right"></i>${i.postCategory.name}</li>
                                                                        </ul>
                                                                    </div>
                                                                    <a href="blogDetail?blogId=${i.id}">
                                                                        <img class="img-height-500" src="${i.thumbnail}" alt="">
                                                                    </a>
                                                                    <h3 class="break-down-line">${i.title}</h3>
                                                                    <p class="break-down-line">${i.briefInfo}</p>-->
                                <%--<c:if test="${requestScope.idCategory == -1}">--%>
                                    <!--<a  class="btn btn-default add-to-cart" href="blogDetail?blogId=${i.id}">Read More</a>-->
                                <%--</c:if>--%>
                                <%--<c:if test="${requestScope.idCategory != -1}">--%>
                                    <!--<a  class="btn btn-default add-to-cart" href="blogDetail?blogId=${i.id}&category=${requestScope.idCategory}&subcategory=${requestScope.idSubCategory}">Read More</a>-->
                                <%--</c:if>--%>
                                <!--</div>-->
                            </c:forEach>


                        </div>
                        <div class="pagination-area">
                            <input type="hidden" value="${requestScope.pageIndex}" id="search-page-index"/>
                            <c:if test="${requestScope.totalPage >= 2}">
                                <ul class="pagination">
                                    <c:forEach var="i" begin="1" end="${requestScope.totalPage}" step="1">
                                        <li><a href="#" class="${(i == 1)? "active":""}">${i}</a></li>
                                        </c:forEach>
                                    <li><a id="next-page" href="#"><i class="fa fa-angle-double-right"></i></a></li>
                                </ul>
                            </c:if>
                        </div>
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
        <script src="../../assets/js/blog/bloglist.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.4/jquery.validate.min.js" integrity="sha512-FOhq9HThdn7ltbK8abmGn60A/EMtEzIzv1rvuh+DqzJtSGq8BRdEN0U+j0iKEIffiw/yEtVuladk6rsG4X6Uqg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    </body>
</html>
