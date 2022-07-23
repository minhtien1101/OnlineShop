<%-- 
    Document   : AddNewPost
    Created on : Jun 7, 2022, 9:20:27 AM
    Author     : DELL
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>  
        <meta charset="UTF-8">
        <title>Marketing | Add Post</title>
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
        <!-- header logo: style can be found in header.less -->
        <jsp:include page="../marketing-template/header.jsp"></jsp:include>
            <div class="wrapper row-offcanvas row-offcanvas-left">
                <!-- Left side column. contains the logo and sidebar -->
            <jsp:include page="../marketing-template/sideBar.jsp"></jsp:include>
                <!-- Right side. contains main content -->
                <aside class="right-side">
                    <div id="back-previous-page"  onclick="history.go(-1)" class="cursor-mg">
                        <span><a><i class="fa-solid fa-angle-left">  <span class="title">Back</span></i> </a></span>
                    </div>
                    <!-- Main content -->
                    <section class="content" id="section-content">
                        <h2 class="title text-center">Add New Post</h2>
                        <div class="mb-10" >
                            <form action="./addPost" method="POST" class="dflex-column" enctype="multipart/form-data">
                                <div class="form-group w-70-percent" >
                                    <div id="preview-box" class="preview-img" style="height: 300px; width: fit-content;">
                                        <img class="img-fit" id="previewImage" style="width: 100%;
                                             height: 100%;"/>
                                        <button onclick="btnClosePreviewImage()" type="button" class="close btn-close-preview" >&times;</button>
                                    </div>
                                    <label for="thumbnail">Thumbnail</label>
                                    <input type="file" class="form-control" name="file" id="file" required="" onchange="checkFileInput(this)" accept="image/*"/>
                                </div>
                                <div class="form-group w-70-percent">
                                    <label for="title">Title</label>
                                    <input type="text" class="form-control" name="title" id="title" placeholder="Enter title of post" required=""/>
                                </div>
                                <div class="form-group w-70-percent">
                                    <label for="bief">Brief</label>
                                    <input type="text" class="form-control" name="brief" id="brief" required="" placeholder="Enter biref of post"/>
                                </div>
                                <div class="form-group w-70-percent">
                                    <label for="description">Description</label>
                                    <textarea type="text" class="form-control" name="description" id="description" placeholder="Enter description of post" required="" rows="3"></textarea>
                                </div>
                                <div class="form-group w-70-percent">
                                    <label for="category">Category</label>
                                    <div class="display-flex">
                                        <select id="listCategory" class="form-control" name="category" onclick="selectSubCategory()">
                                            <!--<option value="-1"></option>-->
                                        <c:forEach items="${requestScope.listCategoryPost}" var="c">
                                            <option value="${c.id}">${c.name}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="button" data-toggle="modal" data-target="#addCategoryModal" class="btn btn-primary ml-2percent">Add new Category</button>
                                </div>
                            </div>

                            <div class="form-group w-70-percent" >
                                <label for="subCategory">SubCategory</label>
                                <div class="display-flex">
                                    <select id="subCatePost" class="form-control" name="subCategory" id="subCategory">
                                        <c:forEach items="${requestScope.listSubcategoryPost}" var="c">
                                            <option value="${c.id}">${c.name}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="button" data-toggle="modal" data-target="#addSubcategoryModal" class="btn btn-primary ml-2percent">Add new SubCategory</button>
                                </div>
                            </div>
                            <!--feature-->
                            <div class="form-group w-70-percent">
                                <label class="form-check-label" for="featureActivate">Featured</label>
                                <div class="display-flex form-group w-70-percent">
                                    <div class="form-check form-check-inline mr-5percent">
                                        <input class="form-check-input" type="radio" name="featured" id="featureActivate"  value="on"/> On
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="featured" id="featureDeactivate"  value="Off" checked="checked"/> Off
                                    </div>
                                </div>
                            </div>
                            <!-- / feature-->

                            <!--Status-->
                            <div class="form-group w-70-percent">
                                <label class="form-check-label" for="statusActivate">Status</label>
                                <div class="display-flex form-group w-70-percent">
                                    <div class="form-check form-check-inline mr-5percent">
                                        <input class="form-check-input" type="radio" name="status" id="statusActivate"  value="show"/> Show
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="status" id="statusDeactivate"  value="hide" checked="checked"/> Hide
                                    </div>
                                </div>
                            </div>
                            <!-- / Status-->
                            <input class="btn btn-primary active"type="submit" value="Save"/>
                        </form>
                    </div>
                </section> <!--/ Main content -->
            </aside><!-- /.right-side -->
        </div>

        <!-- Modal for add new category-->
        <div class="modal fade modal-category" id="addCategoryModal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <!--<h4 class="modal-title">Modal Header</h4>-->
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="newCategory">New Category</label>
                            <input type="text" class="form-control" name="newCategory" id="newCategory1" placeholder="Enter new category name" required=""/> 
                        </div>
                        <div class="form-group">
                            <label for="newCategory">New SubCategory</label>
                            <input type="text" class="form-control" name="newCategory" id="newSubCategory1" placeholder="Enter new subcategory name" required=""/> 
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="handleSaveCategory()">Save</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>


        <!-- Modal for add new Subcategory-->
        <div class="modal fade modal-subcategory" id="addSubcategoryModal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <!--<h4 class="modal-title">Modal Header</h4>-->
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="newSubcategory">Category</label>
                            <select id="category2" name="category2" class="form-control">
                                <c:forEach items="${requestScope.listCategoryPost}" var="c">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="newSubcategory">New subcategory</label>
                            <input type="text" class="form-control" name="newSubcategory2" id="newSubcategory2" placeholder="Enter new Subcategory name" required=""/> 
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="handleSaveSubcategory()">Save</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
        <!--javascrip-->
        <script src="../../assets/js/marketing/addNewPost.js" type="text/javascript"></script>
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

