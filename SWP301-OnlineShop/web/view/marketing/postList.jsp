<%-- 
    Document   : postList
    Created on : Jun 6, 2022, 1:59:27 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Marketing | Post List</title>
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
        <c:set var="idCategory" value="${requestScope.idCategory}"/>
        <c:set var="status" value="${requestScope.idStatus}"/>
        <c:set var="sort" value="${requestScope.sortBy}"/>
        <c:set var="orderBy" value="${requestScope.orderBy}"/>
        <c:set var="search" value="${requestScope.searchContent}"/>
        <c:set var="page" value="${requestScope.page}"/>
        <c:set var="gapPage" value="2"/>
        <c:set var="totalPage" value="${requestScope.totalPage}"/>
        <c:set var="content" value="categoy=${idCategory}&status=${status}&sort=${sort}&orderBy=${orderBy}&search=${search}"/>

        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../marketing-template/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../marketing-template/sideBar.jsp"></jsp:include>
                <!-- Right side. contains main content -->
                <aside class="right-side">
                    <!-- Main content -->
                    <section class="content" id="section-content">
                        
                        <!--Alert-->
                    <c:if test="${requestScope.success != null}">
                        <div class="fixed float-end t-55px" id="showAlter">
                            <div class="alert alert-success alert-dismissible fade in" id="alterfade">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                ${requestScope.success}
                            </div>
                        </div>
                    </c:if>
                        <div class="app-title">
                        <div>
                            <h3><i class="fa fa-list-ul" aria-hidden="true"></i> Post List</h3>
                            <p></p>
                        </div>
                    </div>

                    <c:if test="${requestScope.failed != null}">
                        <div class="fixed float-end t-55px" id="showAlter">
                            <div class="alert alert-danger alert-dismissible fade in" id="alterfade">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                ${requestScope.failed}
                            </div>
                        </div>
                    </c:if>
                    <!--Search, add and filter post-->
                    <div class="row d-flex mb-10">
                        <!--form filter-->
                        <form id="searchForm" action="../marketing/postlist" method="GET" class="form-inline mb-1rem">
                            <select id="category" name="category" class="form-control">
                                <option value="-1">All Category</option>
                                <c:forEach items="${requestScope.listCateogry}" var="i">
                                    <option value="${i.id}" ${(requestScope.idCategory == i.id)?"selected":""}>${i.name}</option>
                                </c:forEach>
                            </select>
                            <select id="status" name="status" class="form-control">
                                <option value="-1">All Status</option>
                                <option value="1" ${(requestScope.idStatus == 1)?"selected":""}>Show</option>
                                <option value="0" ${(requestScope.idStatus == 0)?"selected":""}>Hide</option>
                            </select>
                            <span>Sort by</span>
                            <select id="sort" name="sort" class="form-control">
                                <option value="title">Title</option>
                                <option value="category" ${(requestScope.sortBy == "category")?"selected":""}>Category</option>
                                <option value="author" ${(requestScope.sortBy == "author")?"selected":""}>Author</option>
                                <option value="featured" ${(requestScope.sortBy == "featured")?"selected":""}>Featured</option>
                                <option value="status" ${(requestScope.sortBy == "status")?"selected":""}>Status</option>
                            </select>
                            <select id="orderBy" name="orderBy" class="form-control">
                                <option value="asc">ASC</option>
                                <option value="desc" ${(requestScope.orderBy == "desc")?"selected":""}>DESC</option>
                            </select>
                            <input id="search" name="search" type="text" class="form-control w-25rem" placeholder="Enter Title, Author" 
                                   <c:if test="${(requestScope.searchContent != null)}">
                                       value="${requestScope.searchContent}"
                                   </c:if>
                                   />
                        </form><!-- /form filter-->
                        <!--form add new prodct-->
                        <form action="./addPost" method="GET"  class="m-0-2percent padding-0 float-r display-inline-block">
                            <input class="btn btn-primary" type="submit" value="Add new Post"/>
                        </form>

                    </div><!--/Search, add and filter user-->

                    <!--Table list of product-->
                    <div class="row ">
                        <c:if test="${requestScope.listPosts != null}">
                            <div class="table-responsive">
                                <table class="table table-hover ">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Thumbnail</th>
                                            <th>Title</th>
                                            <th>Category</th>
                                            <th>Author</th>
                                            <th>Featured</th>
                                            <th>Status</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.listPosts}" var="p">
                                            <tr>
                                                <td>${p.id}</td>
                                                <td><img class="img-wh" src="${p.thumbnail}"/></td>
                                                <td class="word-wrap-w-40">${p.title}</td>
                                                <td>${p.postCategory.category.name}</td>
                                                <td>${p.user.fullname}</td>
                                                <td>
                                                    <form id="changeFeatured-${p.id}" action="#" method="POST">
                                                        <button id="btn-featured-${p.id}" type="button" class="btn ${(p.featured)?"btn-success":"btn-danger"}" data-toggle="modal" data-target="#active" onclick="openAnnouceAccept('${p.id}')">
                                                            ${(p.featured)?"On":"Off"}
                                                        </button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <form id="changeStatus-${p.id}" action="" method="POST">
                                                        <button id="btn-status-${p.id}" type="button" class="btn ${(p.status)?"btn-success":"btn-danger"}" data-toggle="modal" data-target="#active" onclick="openModals('${p.id}')">
                                                            ${(p.status)?"Show":"Hide"}
                                                        </button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <a href="./editPost?id=${p.id}" class="text-n-color-w">
                                                        <button type="button" class="btn btn-primary">
                                                            <i class="fa-solid fa-pen-to-square mr-2px"></i>Edit
                                                        </button>
                                                    </a>
                                                    <a href="./postDetails?id=${p.id}" class="text-n-color-w">
                                                        <button type="button" class="btn btn-primary">
                                                            <i class="fa-solid fa-eye mr-2px"></i>View
                                                        </button>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
                    </div><!-- /Table list of product-->

                    <!--Pagging-->
                    <div class="row">
                        <ul class="pagination">
                            <c:if test="${page - gapPage > 0}">
                                <li class="page-item"><a href="#" onclick="paggerFistPage1('${content}');" class="page-link">First</a></li>
                                </c:if>

                            <c:if test="${page - gapPage < 0}">
                                <c:forEach var="i" begin="1" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPage1('${i}', '${content}')" class="page-link">${i}</a></li>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${page - gapPage == 0}">
                                    <c:forEach var="i" begin="1" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPage1('${i}', '${content}')" class="page-link">${i}</a></li>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${page - gapPage > 0}">
                                    <c:forEach var="i" begin="${page - gapPage}" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPage1('${i}', '${content}')" class="page-link">${i}</a></li>
                                    </c:forEach>
                                </c:if>

                            <li class="page-item active"><a class="page-link">${page}</a></li>

                            <c:forEach var="i" begin="${page + 1}" end="${page + gapPage}"  step="1">
                                <c:if test="${i <= totalPage}">
                                    <li class="page-item"><a href="#" onclick="paggerPage1('${i}', '${content}')" class="page-link">${i}</a></li>
                                    </c:if>
                                </c:forEach>

                            <c:if test="${page + gapPage < totalPage}">
                                <li class="page-item"><a href="#" onclick="paggeLastPage1('${totalPage}', '${content}');" class="page-link">Last</a></li>
                                </c:if>
                        </ul>
                    </div> <!-- / Pagging-->

                </section> <!--/ Main content -->
            </aside><!-- /.right-side -->
        </div>
        <!--Modal-->
        <div  class="modal" id="active">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div id="message-modal" class="modal-body">
                        Are you sure to change status!
                    </div>
                    <div class="modal-footer">
                        <button id="btn-change" type="button" class="btn btn-primary" data-dismiss="modal">Yes</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
        <!--/Modal-->
        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="../../js/jquery.min.js" type="text/javascript"></script>
        <!-- jQuery UI 1.10.3 -->
        <script src="../../assets/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../../assets/js/Director/app.js" type="text/javascript"></script>
        <!--javascrip-->
        <script src="../../assets/js/marketing/postsList.js" type="text/javascript">
                                    openModals(id);
        </script>

    </body>
</html>