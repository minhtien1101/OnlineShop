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
        <link href="../../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <!-- font Awesome -->
        <link href="../../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <!-- Ionicons -->
        <link href="../../assets/css/ionicons.css" rel="stylesheet" type="text/css"/>
        <link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
        <!-- Theme style -->
        <link href="../../assets/css/style.css" rel="stylesheet" type="text/css"/>

    </head>

    <body class="skin-black">
        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../admin-layout/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../admin-layout/sideBar.jsp"></jsp:include>

                <!-- Right side column. Contains the navbar and content of the page -->
                <aside class="right-side">

                    <!-- Main content -->
                    <section class="content">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="panel">
                                    <header class="panel-heading">
                                        Customer Details Information
                                    </header>
                                    <div class="panel-body">
                                        <form class="form-horizontal" role="form" action="" method="post">
                                        <c:set var="customer" value="${requestScope.customer}"></c:set>
                                            <div class="form-group">
                                                <div class="col-md-8">
                                                    <input type="hidden" class="form-control" id="customerID" name="customerID" value="${customer.id}">
                                                <input type="hidden" class="form-control" name="updateBy" value="${sessionScope.user.fullname}">
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label for="inputName" class="col-lg-3 col-sm-2 control-label">Name: </label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control" name="fullname" id="fullname" value="${customer.fullname}" pattern="^[a-z A-Z 0-9]+$" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputEmail" class="col-lg-3 col-sm-2 control-label">Email:</label>
                                            <div class="col-md-8">
                                                <input type="email" class="form-control" name="email" id="email"  value="${customer.email}"  required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputMobile" class="col-lg-3 col-sm-2 control-label">Mobile: </label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control" name="mobile" id="mobile"  value="${customer.mobile}" pattern="^[0-9]+$"  required
                                                       title="Your phone must be number">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputAdress"
                                                   class="col-lg-3 col-sm-2 control-label">Address: </label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control" name="address" id="address" value="${customer.address}" required >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputGender" class="col-lg-3 col-sm-2 control-label">Gender:</label>
                                            <div class="col-md-8">                                       
                                                <label class="checkbox-inline">
                                                    <input type="radio" name="gender" ${customer.gender?"checked='checked'":"" }value="male" required> Male 
                                                </label>
                                                <label class="checkbox-inline">
                                                    <input type="radio" name="gender" ${!customer.gender?"checked='checked'":""}value="female" required> Female
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
                                            <div class="col-md-offset-9">
                                                <button type="submit" type="button" class="btn btn-info"> Save </button>
                                                <a href="../customer/list" class="btn btn-danger">Back</a>
                                            </div>
                                        </div>
                                    </form>
                                </div><!-- /.panel-body -->

                            </div><!-- /.panel -->


                        </div><!-- /.col -->
                        <div class="col-md-6">
                            <div class="panel">
                                <header class="panel-heading">
                                    Customer Edit History
                                </header>
                                <div class="panel-body">
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
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->


                        </div><!-- /.col -->
                    </div><!-- /.row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="panel">
                                <header class="panel-heading">
                                    Responsive Hover Table

                                </header>
                                <!-- <div class="box-header"> -->
                                <!-- <h3 class="box-title">Responsive Hover Table</h3> -->

                                <!-- </div> -->
                                <div class="panel-body table-responsive">
                                    <div class="box-tools m-b-15">
                                        <div class="input-group">
                                            <input type="text" name="table_search" class="form-control input-sm pull-right" style="width: 150px;" placeholder="Search"/>
                                            <div class="input-group-btn">
                                                <button class="btn btn-sm btn-default"><i class="fa fa-search"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                    <table class="table table-hover">
                                        <tr>
                                            <th>ID</th>
                                            <th>User</th>
                                            <th>Date</th>
                                            <th>Status</th>
                                            <th>Reason</th>
                                        </tr>
                                        <tr>
                                            <td>183</td>
                                            <td>John Doe</td>
                                            <td>11-7-2014</td>
                                            <td><span class="label label-success">Approved</span></td>
                                            <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                        <tr>
                                            <td>219</td>
                                            <td>Jane Doe</td>
                                            <td>11-7-2014</td>
                                            <td><span class="label label-warning">Pending</span></td>
                                            <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                        <tr>
                                            <td>657</td>
                                            <td>Bob Doe</td>
                                            <td>11-7-2014</td>
                                            <td><span class="label label-primary">Approved</span></td>
                                            <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                        <tr>
                                            <td>175</td>
                                            <td>Mike Doe</td>
                                            <td>11-7-2014</td>
                                            <td><span class="label label-danger">Denied</span></td>
                                            <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                    </table>
                                </div><!-- /.box-body -->
                            </div><!-- /.box -->
                        </div>
                    </div>
                </section><!-- /.content -->
                <div class="footer-main">
                    Copyright &copy Director, 2014
                </div>
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