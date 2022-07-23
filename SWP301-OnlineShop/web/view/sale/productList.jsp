<%-- 
    Document   : productList
    Created on : Jun 6, 2022, 7:48:57 AM
    Author     : Admin
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>  
        <meta charset="UTF-8">
        <title>Sale | Produt list</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <!-- bootstrap 3.0.2 -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

        <!-- Theme style -->
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
        <!--css-->
        <link href="../../assets/css/admin/userList.css" rel="stylesheet" type="text/css"/>
        <link href="../../assets/css/admin/main.css" rel="stylesheet" type="text/css"/>

        <!--active button nav in sidebar-->

        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body class="skin-black">
        <c:set var="categoryId" value="${requestScope.categoryId}"></c:set>
        <c:set var="subCategoryId" value="${requestScope.subCategoryId}"></c:set>
        <c:set var="status" value="${requestScope.status}"></c:set>
        <c:set var="featured" value="${requestScope.featured}"></c:set>
        <c:set var="search" value="${requestScope.search}"></c:set>
        <c:set var="orderBy" value="${requestScope.orderBy}"></c:set>
        <c:set var="sort" value="${requestScope.sort}"></c:set>
        <c:set var="gapPage" value="1"></c:set>
        <c:set var="totalPage" value="${requestScope.totalPage}"></c:set>
        <c:set var="content" value="categoryId=${categoryId}&subCategoryId=${subCategoryId}&featured=${featured}&status=${status}&search=${search}&orderBy=${orderBy}&sort=${sort}"></c:set>
        
        
        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../sale-template/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../sale-template/sideBar.jsp"></jsp:include>
            <!-- Right side. contains main content -->
            <aside class="right-side">
                <!-- Main content -->
                <section class="content ">
                    <!--Alter-->
                    <jsp:include page="../admin-layout/alter.jsp"></jsp:include>
                    <!--Alter-->
                        <!--Search, add and filter product-->
                        <div class="row d-flex" id="searchfilter">
                            <form action="productlist" method="get"  class="form-inline" id="formFilter">
                                <input type="hidden" name="action" value="all"/>
                                <input type="hidden" name="xpage" value="1"/>
                                
                                <!--Category-->
                                <select name="categoryId" id="categoryId" class="form-control">
                                    <option value="0" ${requestScope.categoryId == 0 ? "selected='selected'":""} >All Category</option>
                                    <c:forEach items="${requestScope.categorys}" var="r">
                                        <option  value="${r.id}" ${requestScope.categoryId == r.id ? "selected='selected'":""}>${r.name}</option>
                                    </c:forEach>
                                </select>
                                <!--/Category-->
                                
                                <!--subCategory-->
                                <select name="subCategoryId" id="subCategoryId" class="form-control">
                                    <option value="0" ${requestScope.categoryId == 0 ? "selected='selected'":""} >All sub category</option>
                                    <c:forEach items="${requestScope.subCategorys}" var="r">
                                        <option  value="${r.id}" ${requestScope.subCategoryId == r.id ? "selected='selected'":""}>${r.name}</option>
                                    </c:forEach>
                                </select>
                                <!--/subCategory-->
                                
                                <!--featured-->
                                <select name="featured" id="featured" class="form-control">
                                    <option value="all" ${requestScope.featured == "all" ? "selected='selected'":""}>All featured</option>
                                    <option value="active" ${requestScope.featured == "active" ? "selected='selected'":""}>On</option>
                                    <option value="unactive" ${requestScope.featured == "unactive" ? "selected='selected'":""}>Off</option>
                                </select>
                                <!--/featured-->
                                
                                <!--status-->
                                <select name="status" id="status" class="form-control">
                                    <option value="all" ${requestScope.status == "all" ? "selected='selected'":""}>All status</option>
                                    <option value="active" ${requestScope.status == "active" ? "selected='selected'":""}>Active</option>
                                    <option value="unactive" ${requestScope.status == "unactive" ? "selected='selected'":""}>Deactive</option>
                                </select>
                                <!--/status-->
                                
                                <span>Sort by</span>
                                
                                <!--Order by-->
                                <select name="orderBy" id="orderBy" class="form-control">
                                    <option value="id" ${requestScope.orderBy == "id" ? "selected='selected'":""}>ID</option>
                                    <option value="category" ${requestScope.orderBy == "category" ? "selected='selected'":""}>Category</option>
                                    <option value="subCategory" ${requestScope.orderBy == "subCategory" ? "selected='selected'":""}>subCategory</option>
                                    <option value="originalPrice" ${requestScope.orderBy == "originalPrice" ? "selected='selected'":""}>Original price</option>
                                    <option value="featured" ${requestScope.orderBy == "featured" ? "selected='selected'":""}>Featured</option>
                                    <option value="status" ${requestScope.orderBy == "status" ? "selected='selected'":""}>Status</option>
                                </select>
                                <!--/Order by-->
                                
                                <!--Sort-->
                                <select name="sort" id="sort" class="form-control">
                                    <option value="asc" ${requestScope.sort == "asc" ? "selected='selected'":""}>Asc</option>
                                    <option value="desc" ${requestScope.sort == "desc" ? "selected='selected'":""}>Desc</option>
                                </select>
                                <!--/Sort-->
                                
                                <!--search-->
                                <input type="text" id="search" name="search" value="${requestScope.search}" placeholder="Enter name or id" style="width: 30rem" class="form-control"/>
                                <!--/search-->
                            </form>
                                
                            <form action="addproduct" method="get"  class="m-0-2percent padding-0 float-r display-inline-block">
                                <input class="btn btn-primary" type="submit" value="Add new Product">
                            </form>
                        </div><!--/Search, add and filter user-->

                    <!--Table list of product-->
                    <div class="row ">
                        <div class="table-responsive">
                            <table class="table table-hover ">
                                <thead>
                                    <th>ID</th>
                                    <th>Thumbnail</th>
                                    <th>Name</th>
                                    <th>Category</th>
                                    <th>Subcategory</th>
                                    <th>Original Price</th>
                                    <th>Sale Price</th>
                                    <th>Featured</th>
                                    <th>Status</th>
                                    <th></th>
                                    <th></th>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.products}" var="p">
                                        <tr>
                                            <td>${p.id}</td>
                                            <td><img src="${p.thumbnail}" alt="thumbnail" width="100px" height="100px"/></td>
                                            <td>${p.name}</td>
                                            <td>${p.subCategory.category.name}</td>
                                            <td>${p.subCategory.name}</td>
                                            <td>${p.price}</td>
                                            <td>${p.priceDiscount}</td>
                                            <td>
                                                <c:if test="${!p.featured}"><span class="text-color-red">Off</span></c:if>
                                                <c:if test="${p.featured}"><span class="text-color-green">On</span></c:if>        
                                            </td>
                                            <td>
                                                <form action="editStatusProduct" id="changeStatus-${p.id}" method="get">
                                                    <input type="hidden" name="xpage" value="${page}">
                                                    <input type="hidden" name="id" value="${p.id}">
                                                    <c:if test="${!p.status}">
                                                        <input type="hidden" name="newStatus" value="active">
                                                        <button type="button" class="btn btn-danger" onclick="submitForm('changeStatus-${p.id}')">Hide</button>
                                                    </c:if>
                                                    <c:if test="${p.status}">
                                                        <input type="hidden" name="newStatus" value="unactive">
                                                        <button type="button" class="btn btn-success" onclick="submitForm('changeStatus-${p.id}')">Show</button>
                                                    </c:if>
                                                </form>
                                            </td>
                                            <td>
                                                <a href="editProductInfo?id=${p.id}" class="text-decoration-none text-white">
                                                    <button type="button" class="btn btn-primary">
                                                        <i class="fa-solid fa-pen-to-square" style="margin-right: 2px"></i>Edit
                                                    </button>
                                                </a>
                                            </td>
                                            <td>
                                                <a href="productdetail?id=${p.id}" class="text-decoration-none text-white">
                                                    <button type="button" class="btn btn-primary">
                                                        <i class="fa-solid fa-eye" style="margin-right: 2px"></i>View
                                                    </button>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div><!-- /Table list of product-->

                    <!--Pagging-->
                    <div class="row">
                        <ul class="pagination">
                            <c:if test="${page - gapPage > 0}">
                                <li class="page-item"><a href="#" onclick="paggerPageIndex('${page - 1}','${content}');" class="page-link">Previous</a></li>
                            </c:if>

                            <c:if test="${page - gapPage < 0}">
                                <c:forEach var="i" begin="1" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPageIndex('${i}','${content}')" class="page-link">${i}</a></li>
                                </c:forEach>
                            </c:if>
                            <c:if test="${page - gapPage == 0}">
                                <c:forEach var="i" begin="1" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPageIndex('${i}','${content}')" class="page-link">${i}</a></li>
                                </c:forEach>
                            </c:if>
                            <c:if test="${page - gapPage > 0}">
                                <c:forEach var="i" begin="${page - gapPage}" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPageIndex('${i}','${content}')" class="page-link">${i}</a></li>
                                </c:forEach>
                            </c:if>

                            <li class="page-item active"><a class="page-link">${page}</a></li>

                            <c:forEach var="i" begin="${page + 1}" end="${page + gapPage}"  step="1">
                                <c:if test="${i <= totalPage}">
                                    <li class="page-item"><a href="#" onclick="paggerPageIndex('${i}','${content}')" class="page-link">${i}</a></li>
                                </c:if>
                            </c:forEach>

                            <c:if test="${page + gapPage <= totalPage}">
                                <li class="page-item"><a href="#" onclick="paggerPageIndex('${page + 1}','${content}');" class="page-link">Next</a></li>
                            </c:if>
                        </ul>
                    </div> <!-- / Pagging-->

                </section> <!--/ Main content -->
            </aside><!-- /.right-side -->
        </div>
        <!--javascrip-->
        <script src="../../assets/js/marketing/productList.js"></script>
        <script src="../../assets/js/admin/main.js"></script>
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
