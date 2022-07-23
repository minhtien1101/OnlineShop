<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Customer List</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <!-- bootstrap 3.0.2 -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

        <!-- Theme style -->
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />

        <script src="../../assets/js/customer/customerList.js" type="text/javascript"></script>

    </head>

    <body class="skin-black">
        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../marketing-template/header.jsp"></jsp:include>
        <c:if test="${param.alert != null}">
            <div class="fixed float-end t-55px" id="showAlter" style="    width: 14%;
    z-index: 1024;
    right: 1%;
    top: 5%;
    position: fixed;
                 ">
                <div class="alert alert-success alert-dismissible fade in" style="text-align: center"id="alterfade">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close" style="transform: rotate(90deg);">&times;</a>
                    ${param.alert}
                </div>
            </div>
        </c:if>
        <div class="wrapper row-offcanvas row-offcanvas-left">
            <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../marketing-template/sideBar.jsp"></jsp:include>

                <!-- Right side column. Contains the navbar and content of the page -->
                <aside class="right-side">
                    <!-- Main content -->
                    <section class="content">
                        <section class="panel">
                            <!--Header-->
                            <header class="panel-heading">
                                Customer List
                            </header>
                            <!--Body-->
                            <div class="panel-body">
                                <!-- Search and add-->
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-md-6">
                                        <form  class="form-inline" action="../customer/list" method="get"> 

                                            <input class="form-control" style="width: 20rem;" type="text" name="searchBy" value="${requestScope.searchBy}" placeholder="Search">

                                        <select class="form-control" name="statusBy">
                                            <option value="-1"  ${requestScope.statusBy == "" ? "selected='selected'":""}>Status By</option>
                                            <option value="1" ${requestScope.statusBy == "1" ? "selected='selected'":""} >Active</option>
                                            <option value="0" ${requestScope.statusBy == "0" ? "selected='selected'":""}>Deactive</option>
                                        </select>
                                        <select class="form-control" name="sortBy" onchange="submitForm()">
                                            <option value="-1" ${requestScope.sortBy == "" ? "selected='selected'":""}>SortBy</option>
                                            <option value="fullname" ${requestScope.sortBy == "fullname" ? "selected='selected'":""}>Full Name</option>
                                            <option value="email" ${requestScope.sortBy == "email" ? "selected='selected'":""}>Email</option>
                                            <option value="mobile" ${requestScope.sortBy == "mobile" ? "selected='selected'":""}>Mobile</option>
                                            <option value="status" ${requestScope.sortBy == "status" ? "selected='selected'":""}>Status</option>
                                        </select>
                                        <button type="submit" class="btn btn-info">Search</button>
                                    </form>
                                </div>
                                <div class=" addtask-row">
                                    <a class="btn btn-success btn-md pull-right" href="../customer/add">Add New Customer</a>
                                </div>
                            </div>
                            <!--List of Customer Infomation-->
                            <div class="row">
                                <section class="panel">
                                    <header class="panel-heading">
                                    </header>
                                    <div class="panel-body table-responsive">
                                        <!--Table for customer list-->
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col">ID</th>
                                                    <th scope="col">Full Name</th>
                                                    <th scope="col">Gender</th>
                                                    <th scope="col">Mobile</th>
                                                    <th scope="col">Email</th>
                                                    <th scope="col">Address</th>
                                                    <th scope="col">Status</th>
                                                    <th scope="col">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${requestScope.listCustomer}" var="list">
                                                    <tr>

                                                        <td>${list.id}</td>
                                                        <td>${list.fullname}</td>
                                                        <td>${list.gender ==true ? "Male" : "Female"}</td>
                                                        <td>${list.mobile}</td>
                                                        <td>${list.email}</td>
                                                        <td>${list.address}</td>
                                                        <!--Active And Deactive-->
                                                        <td>
                                                            <form action="../customer/editStatus" id="changeStatus-${list.id}" method="get">
                                                                <input type="hidden" name="id" value="${list.id}">
                                                                <c:if test="${!list.status}">
                                                                    <input type="hidden" name="status" value="active">
                                                                    <!--<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#active" onclick="openModal('changeStatus-${list.id}')">Deactive</button>-->
                                                                    <button type="button" class="btn btn-danger" >Deactive</button>
                                                                </c:if>
                                                                <c:if test="${list.status}">
                                                                    <input type="hidden" name="status" value="deactive">
                                                                    <!--<button type="button" class="btn btn-success" data-toggle="modal" data-target="#active" onclick="openModal('changeStatus-${list.id}')">Active</button>-->
                                                                    <button type="button"  class="btn btn-success">Active</button>
                                                                </c:if>
                                                            </form>
                                                        </td>
                                                        <!--EDIT-->
                                                        <td>
                                                            <a href="../../customer/editDetails?id=${list.id}" style="text-decoration: none; color:white">
                                                                <button type="button" class="btn btn-primary">
                                                                    <i class="fa-solid fa-user-pen"></i>Edit
                                                                </button>
                                                            </a>
                                                            <!--VIEW-->
                                                            <a href="../../customer/viewdetails?id=${list.id}" style="text-decoration: none; color:white">
                                                                <button type="button" class="btn btn-primary">
                                                                    <i class="fa-solid fa-user-pen"></i>View
                                                                </button>
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>

                                            </tbody>
                                        </table>
                                    </div>
                            </div>
                            <!--Pagging -->
                            <!--<div class="pull-right" id="pagger"> </div>-->

                            <div class="pagging">
                                <ul class="pagination pull-right">
                                    <c:if test="${requestScope.totalpage > 1}">

                                        <li><a href="list?page=1&searchBy=${searchBy}&statusBy=${statusBy}&sortBy=${sortBy}">Frist</a></li>
                                        </c:if>
                                        <c:forEach begin="1" end="${requestScope.totalpage}" var="page">
                                        <li class="${pageindex == page ? "active =" : ""}" ><a href="list?page=${page}&searchBy=${searchBy}&statusBy=${statusBy}&sortBy=${sortBy}">${page}</a></li>    
                                        </c:forEach>
                                        <c:if test="${requestScope.totalpage > 1}">
                                        <li><a href="list?page=${requestScope.totalpage}&searchBy=${searchBy}&statusBy=${statusBy}&sortBy=${sortBy}">Last</a></li>
                                        </c:if>
                                </ul>
                            </div>

                        </div>
                    </section>
                </section>
            </aside> 
        </div>
        <div class="footer-main">
            Copyright &copy Director, 2014
        </div>
        <!--Modal-->
        <div class="modal" id="active">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        Do you want to change this status!
                    </div>
                    <div class="modal-footer">
                        <button id="clickChangeStatus" type="button"></button>
                        <!--<button type="button" class="btn btn-primary" onclick="document.getElementById('changeStatus-2').submit();">Yes</button>-->
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>

                </div>
            </div>
        </div>
        <script language="JavaScript" type="text/javascript">
            pagger('pagger',${requestScope.pageindex},${requestScope.totalpage});
        </script>
        <script language="JavaScript" type="text/javascript" >
            function openModal(id) {
                var button = document.getElementById('clickChangeStatus');
                button.innerHTML = '';
                button.setAttribute('class', "btn btn-primary");
                button.setAttribute('onclick', 'document.getElementById("' + id + '").submit();');
                button.innerHTML = 'Yes';
            }
            function submitForm() {
                document.getElementById("searchForm").submit();
            }
            setTimeout(function () {
                const element = document.getElementById('showAlter');
                element.remove();
            }, 5000);
        </script>



        <script src="../../assets/js/customer/customerList.js" type="text/javascript"></script>



        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="../../assets/js/jquery.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../../assets/js/Director/app.js" type="text/javascript"></script>
    </body>

</html>