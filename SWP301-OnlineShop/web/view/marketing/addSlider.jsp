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
    <c:choose>
        <c:when test="${s == null}">
            <title>Add Slider</title>
        </c:when>
        <c:otherwise>
            <title>Edit Slider</title>
        </c:otherwise>
    </c:choose>

    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- font Awesome -->
    <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons -->
    <link href="../assets/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- Morris chart -->
    <link href="../assets/css/morris/morris.css" rel="stylesheet" type="text/css" />

    <!-- iCheck for checkboxes and radio inputs -->
    <link href="../assets/css/iCheck/all.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
    <link href="../../assets/css/marketing/style.css" rel="stylesheet" type="text/css" />
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="../../assets/css/marketing/main.css"/>

</head>
<style>
    .left-side {
        min-height: 930px !important;
    }
</style>

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
                    <div class="app-title" style="margin-bottom: 20px">
                            <h1>
                            <c:choose>
                                <c:when test="${s != null}">
                                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit Slider
                                </c:when>
                                <c:otherwise>
                                    <i class="fa fa-plus" aria-hidden="true"></i> Add Slider
                                </c:otherwise>
                            </c:choose>
                        </h1>
                </div>

                <c:if test="${messFalse != null}"> 
                    <div id="alert" class="alert alert-danger" style="text-align: center; font-size: 20px;">
                        ${messFalse}
                    </div>
                </c:if>
                <c:if test="${messTrue != null}"> 
                    <div id="alert" class="alert alert-success" style="text-align: center; font-size: 20px;">
                        ${messTrue}&nbsp;
                    </div>
                </c:if>

                    <form role="form" class="form-horizontal" action="/marketing/sliderAdd" method="post" enctype="multipart/form-data" id="form-add-update">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="bs-component">
                                <div class="jumbotron" id="clock">
                                    <div class="col-sm-1">
                                        <div class="toggle lg"> 
                                            <label class="switch ">
                                                <input type="checkbox" class="success" id="btn-id"  ${s.status? "checked":""}>
                                                
                                                <span class="slider round"></span>
                                            </label>
                                        <input type="hidden" id="h-status-save" name="txtStatus" value="${s.status}"/>
                                        </div> 
                                        <p class="note">(Hide/Show)</p>
                                    </div>

                                    <input type="file" name="fImage" id="getval" style="margin-top: 300px;" value="" <c:if test="${s == null}">required=""</c:if>/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">

                            <div class="form-group">
                                <label class="control-label col-sm-1" for="firstname">Title</label>
                                <div class="col-sm-10">
                                    <input type="text" name= "txtTitle" class="form-control" placeholder="Enter Title" value="${s.title}" required="">
                                <input type="hidden" name= "hId" class="form-control" value="${id}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-1">Backlink</label>
                            <div class="col-sm-10">
                                <input type="text" name="txtBacklink" class="form-control" placeholder="Enter Backlink" value="${s.backlink}" required="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-1" for="Password">Notes</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="5" placeholder="Enter your notes" name="txtNotes" required="">${s.note}</textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-1" for="Password"></label>
                            <div class="col-sm-10">
                                <a class="btn btn-primary" href="/marketing/sliderList" role="button"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i>&nbsp;Cancel</a>

                                <c:choose>
                                <c:when test="${s != null}">
                                    <button type="button" id="btn-update" class="btn btn-primary">Update&nbsp;<i class="fa fa-arrow-circle-right" aria-hidden="true"></i></button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn btn-primary">Add&nbsp;<i class="fa fa-arrow-circle-right" aria-hidden="true"></i></button>
                                </c:otherwise>
                            </c:choose>
                                
                            </div>
                        </div>

                    </div>
                </form>
            </section>
        </aside>
    </div>

    <style type="text/css">
        #clock{
            background-image: url("../..${image}");
        }

    </style>
    <div class="modal fade" id="confirm-show-slider" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Confirm Change Status</h4>
                </div>

                <div class="modal-body">
                    <p id="ct"></p>
                    <p>Do you want to proceed?</p>
                    <p class="debug-url"></p>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-danger btn-change">Change</a>
                </div>
               <input type="hidden" id="h-status-save-modal" value=""/>
            </div>
        </div>
    </div>
    
    <div class="modal fade" id="confirm-update-slider" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Update Slider</h4>
                </div>

                <div class="modal-body">
                    <p id="ct">The information of the slider will be updated.</p>
                    <p>Do you want to proceed?</p>
                    <p class="debug-url"></p>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-danger btn-update-slider">Update</a>
                </div>
               <input type="hidden" id="h-status-save-modal" value=""/>
            </div>
        </div>
    </div>


    <script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
    <script data-require="bootstrap@*" data-semver="3.1.1" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <script src="../assets/js/marketing/addSlider.js"></script>
    <script src="../assets/js/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <script src="../assets/js/plugins/chart.js" type="text/javascript"></script>
    <script src="../assets/js/plugins/iCheck/icheck.min.js" type="text/javascript"></script>
</body>
</html>
