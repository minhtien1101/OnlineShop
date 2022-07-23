<%-- 
    Document   : userList
    Created on : May 25, 2022, 5:57:25 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <title>Slider Detail</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- font Awesome --
    <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons -->
    <link href="../assets/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- Morris chart -->
    <link href="../assets/css/morris/morris.css" rel="stylesheet" type="text/css" />
    <!-- jvectormap -->
    <link href="../assets/css/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
    <!-- Date Picker -->
    <link href="../css/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />

    <link href="../assets/css/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
    <!-- iCheck for checkboxes and radio inputs -->
    <link href="../assets/css/iCheck/all.css" rel="stylesheet" type="text/css" />
    <!-- bootstrap wysihtml5 - text editor -->

    <link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
    <!-- Theme style -->
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
    <link href="../../assets/css/marketing/style.css" rel="stylesheet" type="text/css" />
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../../assets/css/marketing/main.css">
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body class="skin-black">
    <!-- header logo: style can be found in header.less -->
    <jsp:include page="../marketing-template/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../marketing-template/sideBar.jsp"></jsp:include>

            <!-- Right side. contains main content -->
            <aside class="right-side">
                <!-- Main content -->
                <section class="content">
                    <div class="product-content product-wrap clearfix product-deatil">
                        <div class="row">
                            <div class="col-md-5 col-sm-12 col-xs-12">
                                <div class="product-image">
                                    <div id="myCarousel-2" class="carousel slide">

                                        <div class="carousel-inner">
                                            <!-- Slide 1 -->
                                                <div class="tile">

                                                <img src="${slider.image}" alt="">
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-md-offset-1 col-sm-12 col-xs-12">
                            <h2 class="name">
                                ${slider.title}
                            </h2>
                            <hr />
                            <h4 class="price-container">
                                <strong><i class="fa fa-external-link" aria-hidden="true"></i>&nbsp;Backlink:</strong>
                                <a href="${slider.backlink}"><span>${slider.backlink}</span></a>
                            </h4>
                            <hr>
                            <h4 class="price-container" style="transform: translateY(0px);
                                display: inline-block;">
                                <strong><i class="fa fa-info-circle" aria-hidden="true"></i>&nbspStatus:</strong>&nbsp&nbsp
                                <c:choose>
                                    <c:when test="${slider.status==true}">
                                        <span class="label label-success" style="padding: 4px 8px">Show</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="label label-danger" style="padding: 4px 8px">Hide</span>
                                    </c:otherwise>
                                </c:choose>

                            </h4>
                            <hr />
                            <div class="tab-pane fade active in" id="more-information">
                                <strong>Description Title</strong>
                                <p>
                                    ${slider.note}
                                </p>
                            </div>  
                            <hr />
                            <div class="row">
                                <div class="col-sm-12 col-md-6 col-lg-6">
                                    <a href="/marketing/sliderList" class="btn btn-success btn-lg"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i>&nbspBack to List</a>
                                </div>
                              
                            </div>
                        </div>
                    </div>
                </div>
            </section><!-- /.content -->


            <!--                <div class="footer-main">
                                Copyright &copy Director, 2014
                            </div>-->
        </aside><!-- /.right-side -->


    </div><!-- ./wrapper -->







    <!-- jQuery 2.0.2 -->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
    <script src="../js/jquery.min.js" type="text/javascript"></script>
    <!-- jQuery UI 1.10.3 -->
    <script src="../assets/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
    <!-- Bootstrap -->
    <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- daterangepicker -->
    <script src="../assets/js/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <script src="../assets/js/plugins/chart.js" type="text/javascript"></script>
    <script src="../assets/js/plugins/iCheck/icheck.min.js" type="text/javascript"></script>
    <!-- calendar -->
    <script src="../assets/js/plugins/fullcalendar/fullcalendar.js" type="text/javascript"></script>
    <!-- Director App -->
    <script src="../assets/js/Director/app.js" type="text/javascript"></script>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</body>
</html>
