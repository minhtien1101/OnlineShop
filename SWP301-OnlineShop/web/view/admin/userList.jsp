<%-- 
    Document   : userList
    Created on : May 25, 2022, 5:57:25 AM
    Author     : Admin
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>  
        <meta charset="UTF-8">
        <title>Admin | User List</title>
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
        <c:set var="roleId" value="${requestScope.roleId}"/>
        <c:set var="gender" value="${requestScope.gender}"/>
        <c:set var="status" value="${requestScope.status}"/>
        <c:set var="sort" value="${requestScope.sort}"/>
        <c:set var="orderBy" value="${requestScope.orderBy}"/>
        <c:set var="page" value="${requestScope.page}"/>
        <c:set var="gapPage" value="2"/>
        <c:set var="totalPage" value="${requestScope.totalPage}"/>
        <c:set var="content" value="gender=${gender}&status=${status}&roleId=${roleId}&search=${search}&sort=${sort}&orderBy=${orderBy}"/>


        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../admin-layout/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../admin-layout/sideBar.jsp"></jsp:include>
                <!-- Right side. contains main content -->
                <aside class="right-side">
                    <!-- Main content -->
                    <section class="content ">
                        <!--Alter-->
                    <jsp:include page="../admin-layout/alter.jsp"></jsp:include>
                    <div class="app-title">
                        <div>
                            <h3><i class="fa fa-list-ul" aria-hidden="true"></i> User List</h3>
                            <p></p>
                        </div>
                    </div>
                    <!--Search, add and filter user-->
                    <div class="row d-flex" id="searchfilter">
                        <form action="userList" method="get"  class="form-inline" id="formFilter">
                            <input type="hidden" name="action" value="all"/>
                            <input type="hidden" name="xpage" value="1"/>
                        <select name="roleId" id="roleId" class="form-control">
                            <option value="0" ${requestScope.roleId == 0 ? "selected='selected'":""} >All role</option>
                            <c:forEach items="${requestScope.roles}" var="r">
                                <option  value="${r.id}" ${requestScope.roleId == r.id ? "selected='selected'":""}>${r.name}</option>
                            </c:forEach>
                        </select>
                        <select name="gender" id="gender" class="form-control">
                            <option value="all" ${requestScope.gender == "all" ? "selected='selected'":""}>All gender</option>
                            <option value="male" ${requestScope.gender == "male" ? "selected='selected'":""}>Male</option>
                            <option value="female" ${requestScope.gender == "female" ? "selected='selected'":""}>Female</option>
                        </select>
                        <select name="status" id="status" class="form-control">
                            <option value="all" ${requestScope.status == "all" ? "selected='selected'":""}>All status</option>
                            <option value="active" ${requestScope.status == "active" ? "selected='selected'":""}>Active</option>
                            <option value="unactive" ${requestScope.status == "unactive" ? "selected='selected'":""}>Deactive</option>
                        </select>
                        <span>Sort by</span>
                        <select name="sort" id="sort" class="form-control">
                            <option value="id" ${requestScope.sort == "id" ? "selected='selected'":""}>ID</option>
                            <option value="fullname" ${requestScope.sort == "fullname" ? "selected='selected'":""}>Full name</option>
                            <option value="gender" ${requestScope.sort == "gender" ? "selected='selected'":""}>Gender</option>
                            <option value="email" ${requestScope.sort == "email" ? "selected='selected'":""}>Email</option>
                            <option value="mobile" ${requestScope.sort == "mobile" ? "selected='selected'":""}>Mobile</option>
                            <option value="status" ${requestScope.sort == "status" ? "selected='selected'":""}>Status</option>
                        </select>
                        <select name="orderBy" id="orderBy" class="form-control">
                            <option value="asc" ${requestScope.orderBy == "asc" ? "selected='selected'":""}>Asc</option>
                            <option value="desc" ${requestScope.orderBy == "desc" ? "selected='selected'":""}>Desc</option>
                        </select>
                        <input type="text" id="search" name="search" value="${requestScope.search}" placeholder="Enter part of name, phone or email" style="width: 25rem" class="form-control"/>
                    </form>
                    <form action="addNewUser" method="get"  id="formAddNewUser">
                        <input class="btn btn-primary" type="submit" value="Add new user">
                    </form>
                </div><!--/Search, add and filter user-->

                <!--Table list of user-->
                <div class="row ">
                    <div class="table-responsive">
                        <table class="table table-hover ">
                            <thead>
                                <tr>
                                    <th onclick="">Id</th>
                                    <th>Name</th>
                                    <th>Gender</th>
                                    <th>Email</th>
                                    <th>Mobile</th>
                                    <th>Address</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.users}" var="u">
                                    <tr>
                                        <td>${u.id}</td>
                                        <td>${u.fullname}</td>
                                        <td>${u.gender == true ? "Male" : "Female"}</td>
                                        <td>${u.email}</td>
                                        <td>${u.mobile}</td>
                                        <td>${u.address}</td>
                                        <td>${u.role.name}</td>
                                        <td>
                                            <form action="editUserStatus" id="changeStatus-${u.id}" method="get">
                                                <input type="hidden" name="xpage" value="${page}">
                                                <input type="hidden" name="id" value="${u.id}">
                                                <c:if test="${!u.status}">
                                                    <input type="hidden" name="newStatus" value="active">
                                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#active" onclick="openModal('changeStatus-${u.id}')">Unactive</button>
                                                </c:if>
                                                <c:if test="${u.status}">
                                                    <input type="hidden" name="newStatus" value="unactive">
                                                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#active" onclick="openModal('changeStatus-${u.id}')">Active</button>
                                                </c:if>
                                            </form>
                                        </td>
                                        <td>
                                            <a href="editUserProfile?id=${u.id}" class="text-decoration-none text-white">
                                                <button type="button" class="btn btn-primary">
                                                    <i class="fa-solid fa-user-pen"></i>Edit
                                                </button>
                                                </a>
                                        </td>
                                        <td>
                                            <a href="viewUserProfile?id=${u.id}" class="text-decoration-none text-white">
                                                <button type="button" class="btn btn-primary">
                                                   <i class="fa-solid fa-eye" style="margin-right: 2px"></i>View
                                                </button>
                                                </a>
                                        </td>
                                    </tr>
                                    </tbody>
                                </c:forEach>
                        </table>
                    </div>
                </div>
                    <!--Pagging-->
                    <div class="row">
                        <ul class="pagination">
                            <c:if test="${page - gapPage > 0}">
                                <li class="page-item"><a href="#" onclick="paggerFistPage('${content}');" class="page-link">First</a></li>
                                </c:if>

                            <c:if test="${page - gapPage < 0}">
                                <c:forEach var="i" begin="1" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPage('${i}', '${content}')" class="page-link">${i}</a></li>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${page - gapPage == 0}">
                                    <c:forEach var="i" begin="1" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPage('${i}', '${content}')" class="page-link">${i}</a></li>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${page - gapPage > 0}">
                                    <c:forEach var="i" begin="${page - gapPage}" end="${page - 1}" step="1">
                                    <li class="page-item"><a href="#" onclick="paggerPage('${i}', '${content}')" class="page-link">${i}</a></li>
                                    </c:forEach>
                                </c:if>

                            <li class="page-item active"><a class="page-link">${page}</a></li>

                            <c:forEach var="i" begin="${page + 1}" end="${page + gapPage}"  step="1">
                                <c:if test="${i <= totalPage}">
                                    <li class="page-item"><a href="#" onclick="paggerPage('${i}', '${content}')" class="page-link">${i}</a></li>
                                    </c:if>
                                </c:forEach>

                            <c:if test="${page + gapPage < totalPage}">
                                <li class="page-item"><a href="#" onclick="paggeLastPage('${totalPage}', '${content}');" class="page-link">Last</a></li>
                                </c:if>
                        </ul>
                    </div>
                    <!--/Pagging-->
                </section>
            </aside><!-- /.right-side -->
        </div><!-- ./wrapper -->

        <!--Modal-->
        <div class="modal" id="active">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        Are you sure to change status!
                    </div>
                    <div class="modal-footer">
                        <button id="clickChangeStatus" type="button"></button>
                        <!--<button type="button" class="btn btn-primary" onclick="document.getElementById('changeStatus-2').submit();">Yes</button>-->
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>

                </div>
            </div>
        </div>
        <!--/Modal-->


        <!--javascrip-->
        <script src="../../assets/js/admin/userList.js"></script>
        <script src="../../assets/js/admin/main.js" type="text/javascript"></script>
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
