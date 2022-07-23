<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Customer Details</title>
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


    </head>

    <body class="skin-black">
        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../marketing-template/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../marketing-template/sideBar.jsp"></jsp:include>

                <!-- Right side column. Contains the navbar and content of the page -->
                <aside class="right-side">

                    <section class="content col-md-6" style="margin-left: 20%;">
                        <section class="panel">
                            <header class="panel-heading">
                                View Customer
                            </header>
                            <!--BODY-->
                            <div class="panel-body">
                                <!--FORM OF CUSTOMER INFOMATION DATA-->
                                <form class="form-horizontal" role="form">
                                <c:set var="customer" value="${requestScope.customer}"></c:set>

                                    <div class="form-group">
                                        <label for="inputName" class="col-lg-3 col-sm-2 control-label">Name: </label>
                                        <div class="col-md-8">
                                            <input readonly class="form-control"value="${customer.fullname}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputEmail" class="col-lg-3 col-sm-2 control-label">Email:</label>
                                    <div class="col-md-8">
                                        <input readonly class="form-control"value="${customer.email}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputMobile" class="col-lg-3 col-sm-2 control-label">Mobile: </label>
                                    <div class="col-md-8">
                                        <input readonly class="form-control"value="${customer.mobile}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputAdress"
                                           class="col-lg-3 col-sm-2 control-label">Address: </label>
                                    <div class="col-md-8">
                                        <input readonly class="form-control"value="${customer.address}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputGender" class="col-lg-3 col-sm-2 control-label">Gender:</label>
                                    <div class="col-md-8">                                       
                                        <label class="checkbox-inline">
                                            <input type="radio" name="gender" ${customer.gender?"checked='checked'":"" } disabled> Male 
                                        </label>
                                        <label class="checkbox-inline">
                                            <input type="radio" name="gender" ${!customer.gender?"checked='checked'":""} disabled> Female
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputStatus" class="col-lg-3 col-sm-2 control-label">Status:</label>
                                    <div class="col-md-8">                                       
                                        <label class="checkbox-inline">
                                            <input type="radio" name="checkActive" ${customer.status?"checked='checked'":""} disabled> Active
                                        </label>
                                        <label class="checkbox-inline">
                                            <input type="radio" name="checkActive" ${!customer.status?"checked='checked'":""} disabled> Deactive
                                        </label>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col-md-12 ">
                                        <a class="btn btn-danger pull-right" href="../customer/list">Back</a>
                                    </div> 
                                </div>
                            </form>
                        </div>
                    </section>
                </section>
                <section class="content col-md-8" style="margin-left: 14%; display: inline-table">                
                    <section class="panel" >
                            <div class="panel">
                                <header class="panel-heading">
                                    Customer Edit History
                                </header>
                                <div class="panel-body">
                                    <c:if test="${ not empty requestScope.listHistoryUpdate}">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Email</th>
                                                    <th scope="col">Update By</th>
                                                    <th scope="col">Update Date</th>
                                                    <th scope="col">Full Name</th>
                                                    <th scope="col">Gender</th>
                                                    <th scope="col">Mobile</th>
                                                    <th scope="col">Address</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${requestScope.listHistoryUpdate}" var="updateHistory">
                                                    <tr>
                                                        <td>${updateHistory.email}</td>
                                                        <td>${updateHistory.updateBy}</td>
                                                        <td>${updateHistory.updateDate}</td>
                                                        <td>${updateHistory.fullname}</td>
                                                        <td>${updateHistory.gender ==true ? "Male" : "Female"}</td>
                                                        <td>${updateHistory.mobile}</td>
                                                        <td>${updateHistory.address}</td>

                                                    </tr>
                                                </c:forEach>

                                            </tbody>
                                        </table>
                                        <div class="pagging">
                                            <ul class="pagination pull-right">
                                                <c:if test="${requestScope.totalpage > 1}">
                                                    <li><a href="../customer/viewdetails?id=${customer.id}&page=1">Frist</a></li>
                                                    </c:if>
                                                    <c:forEach begin="1" end="${requestScope.totalpage}" var="page">
                                                    <li class="${pageindex == page ? "active =" : ""}" ><a href="../customer/viewdetails?id=${customer.id}&page=${page}">${page}</a></li>    
                                                    </c:forEach>
                                                    <c:if test="${requestScope.totalpage > 1}">
                                                    <li><a href="../customer/viewdetails?id=${customer.id}&page=${requestScope.totalpage}">Last</a></li>
                                                    </c:if>
                                            </ul>
                                        </div>
                                    </c:if>
                                    <c:if test="${empty requestScope.listHistoryUpdate}">
                                        <div style="text-align: center;">
                                            <p>This customer have no update before!!</p>
                                        </div>
                                    </c:if>
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                    </section>
                </section>
            </aside><!-- /.right-side -->
        </div>
        <div class="footer-main">
            Copyright &copy Director, 2014
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="../../assets/js/jquery.min.js" type="text/javascript"></script>
        <!-- Bootstrap -->
        <script src="../../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- Director App -->
        <script src="../../assets/js/Director/app.js" type="text/javascript"></script>
    </body>

</html>