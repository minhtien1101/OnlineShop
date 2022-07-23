<%-- 
    Document   : productList
    Created on : Jun 6, 2022, 7:48:57 AM
    Author     : Admin
--%>
<%@page import="model.Image"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>  
        <meta charset="UTF-8">
        <title>Marketing | Produt list</title>
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
        <%
            ArrayList<Image> attchedImg = ((Product)request.getAttribute("product")).getImage();
            if(attchedImg == null || attchedImg.size() == 0){
                Image image = new Image();
                image.setImage("#");
                attchedImg.add(image);
                attchedImg.add(image);
            }
        %>
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
                    <section class="content ">
                        <!--alter-->
                    <jsp:include page="../marketing-template/alter.jsp"></jsp:include>
                        <!--/alter-->
                    <div class="mb-10" >
                        <div class="w-70-percent mx-auto">
                            <div>
                                <h3><i class="fa fa-list-ul" aria-hidden="true"></i> Edit product's info</h3>
                                <p></p>
                                <br>
                            </div>
                        </div>
                        <form action="editProductInfo" method="POST" enctype="multipart/form-data" id="editProductInfo" >
                            
                            <input type="hidden" name="id" value="${requestScope.product.id}"/>
                            
                            <!--Thumbnail-->
                            <div class="form-group w-70-percent mx-auto">
                                <label for="thumbnail">Thumbnail</label>
                                <div  class="w-35percent image-input-layout display-flex" style="position: relative; width: fit-content;">
                                    <div id="closeBtn-thumbnailBoxImg" class="close-btn-not-hide" onclick="closeImgage('thumbnailBoxImg','closeBtn-thumbnailBoxImg','thumbnail');"> <i class="fa-solid fa-xmark close-btn"></i></div>
                                    <img id="thumbnailBoxImg" src="${requestScope.product.thumbnail}" alt="your image" style="height:100%; width:100%;" class="boder-radius  image-thumbnail"/>
                                </div>
                                <input type="file" name="thumbnail" id="thumbnail" accept="image/*" onchange="showThumbnail()" />
                            </div>
                            <!--/Thumbnail-->
                            
                             <!--Attached image-->
                            <div class="form-group w-70-percent mx-auto" >
                                <label >Attached image</label>
                                <div class="display-flex">
                                    <div>
                                        <div  class="w-100percent image-input-layout display-flex" style="position: relative;width: fit-content;">
                                            <div id="closeBtn-attachedBoxImg-1" class="close-btn-not-hide" onclick="closeImgage('attachedBoxImg-1','closeBtn-attachedBoxImg-1','attachedImg1');"> <i class="fa-solid fa-xmark close-btn"></i></div>
                                            <img id="attachedBoxImg-1" src="<%= attchedImg.get(0).getImage() %>" alt="your image" style="height:100%; width:100%;" class="boder-radius image-thumbnail"/>
                                        </div>
                                        <input type="file" name="attachedImg1" id="attachedImg1" accept="image/*" onchange="showAttachedImg('attachedBoxImg-1','closeBtn-attachedBoxImg-1','attachedImg1')"/>
                                    </div>
                                    <div>
                                        <div  class="w-100percent image-input-layout ml-2percent" style="position: relative;width: fit-content;">
                                            <div id="closeBtn-attachedBoxImg-2" class="close-btn-not-hide" onclick="closeImgage('attachedBoxImg-2','closeBtn-attachedBoxImg-2','attachedImg2');"> <i class="fa-solid fa-xmark close-btn"></i></div>
                                            <img id="attachedBoxImg-2" src="<%= attchedImg.get(1).getImage() %>" alt="your image" style="height:100%; width:100%;" class="boder-radius image-thumbnail"/>
                                        </div>
                                        <input type="file" name="attachedImg2" id="attachedImg2" accept="image/*" onchange="showAttachedImg('attachedBoxImg-2','closeBtn-attachedBoxImg-2','attachedImg2')"/>
                                    </div>
                                </div>
                            </div>
                            <!--/Attacked image-->

                            <!--Name-->
                            <div class="form-group w-70-percent mx-auto" >
                                <label for="name">Name</label>
                                <input type="text" class="form-control" value="${requestScope.product.name}" name="name" id="name" placeholder="Enter name of product"  required=""/>
                            </div>
                            <!--/Name-->

                            <!--Description-->
                            <div class="form-group w-70-percent" style="margin: 0 auto">
                                <label for="description">Description</label>
                                <textarea type="text" class="form-control"  name="description" id="description" placeholder="Enter description of product" required="" rows="5">${requestScope.product.description}</textarea>
                            </div>
                            <!--/Description-->

                            <!--Seller-->
                            <div class="form-group w-70-percent mx-auto" style="margin: 0 auto">
                                <label for="seller">Seller</label>
                                <div class="display-flex" >
                                    <input type="hidden" class="form-control" value="${requestScope.product.user.id}" name="sellerId"  />
                                    <input type="text" class="form-control" value="${requestScope.product.user.email}" id="seller"  disabled="" />
                                    <button type="button" onclick="handleSearchSellerInfo()" class="btn btn-primary ml-2percent ">Info</button>
                                </div>
                            </div>
                                    
                            <div class="form-group w-70-percent mx-auto mt-10" id="rowShowSeller"></div>
                            <!--/Seller-->
                            
                            <!--Category-->
                            <div class="form-group w-70-percent mx-auto">
                                <label for="category">Category</label>
                                <div class="display-flex">
                                    <select class="form-control" name="category" id="category" onchange="handleShowSubCategory()">
                                        <c:forEach items="${requestScope.categorys}" var="c">
                                            <option value="${c.id}" ${requestScope.categoryId == c.id ? "selected='selected'" : ""}>
                                                ${c.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <button type="button" class="btn btn-primary ml-2percent" data-toggle="modal" data-target="#addCategoryModal">Add new Category</button>
                                </div>
                            </div>
                            <!--/Category-->
                            
                            <!--SubCategory-->
                            <div class="form-group w-70-percent mx-auto" >
                                <label for="subCategory">SubCategory</label>
                                <div class="display-flex">
                                    <select class="form-control" name="subCategory" id="subCategory" required="">
                                        <c:forEach items="${requestScope.subCategorys}" var="c">
                                            <option value="${c.id}" 
                                                    <c:if test="${subCategory == c.id}">slected = "slected"</c:if> >
                                                ${c.name}
                                            </option>
                                        </c:forEach>    
                                    </select>
                                    <button type="button" class="btn btn-primary ml-2percent " data-toggle="modal" data-target="#addSubcategoryModal">Add new SubCategory</button>
                                </div>
                            </div>
                            <!--SubCategory-->

                            <!--Price-->
                            <div class="form-group w-70-percent mx-auto">
                                <label for="price">Price</label>
                                <input type="number" class="form-control" name="price" id="price" min="0" placeholder="Enter price of product" value="${requestScope.product.price}" required="">
                            </div>
                            <!--Price-->

                            <!--Discount-->
                            <div class="form-group w-70-percent mx-auto">
                                <label for="discount">Discount</label>
                                <input type="number" class="form-control" name="discount" id="discount" min="0" max="100" placeholder="Enter discount of product" value="${requestScope.product.discount}" required="">
                            </div>
                            <!--/Discount-->
                            
                            <!--Quantity-->
                            <div class="form-group w-70-percent mx-auto">
                                <label for="quantity">Quantity</label>
                                <input type="number" class="form-control" name="quantity" id="quantity" min="0"  placeholder="Enter quantity of product" value="${requestScope.product.quantity}"  required="">
                            </div>
                            <!--/Quantity-->
                            
                            <!--feature-->
                            <div class="form-group w-70-percent mx-auto">
                                <label class="form-check-label" for="featureActivate">Feattured</label>
                                <div class="display-flex">
                                    <div class="form-check form-check-inline mr-5percent">
                                        <input class="form-check-input" type="radio" name="featured" id="featureActivate"  value="activate" <c:if test="${requestScope.product.featured == true}">checked="checked"</c:if> > Active
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="featured" id="featureDeactivate"  value="deactivate" <c:if test="${requestScope.product.featured == false}">checked="checked"</c:if>> Deactivate
                                    </div>
                                </div>
                            </div>
                            <!-- / feature-->

                             <!--Status-->
                            <div class="form-group w-70-percent mx-auto">
                                <label class="form-check-label" for="statusActivate">Status</label>
                                <div class="display-flex">
                                    <div class="form-check form-check-inline mr-5percent">
                                        <input class="form-check-input" type="radio" name="status" id="statusActivate"  value="activate" <c:if test="${requestScope.product.status == true}">checked="checked"</c:if>> Active
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="status" id="statusDeactivate"  value="deactivate" <c:if test="${requestScope.product.status == false}">checked="checked"</c:if>> Deactivate
                                    </div>
                                </div>
                           </div>
                            <!-- / Status-->
                            <div class="form-group w-70-percent mx-auto mt-10">
                                <button type="button" class="btn btn-success  " data-toggle="modal" data-target="#saveEdit">Save</button>
                            </div>
                        </form>
                    </div>
                </section> <!--/ Main content -->
            </aside><!-- /.right-side -->

        </div>


        <!-- Modal for add new category-->
        <div class="modal fade" id="addCategoryModal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <!--<h4 class="modal-title">Modal Header</h4>-->
                    </div>
                    <div class="modal-body">
                            <div class="form-group">
                                <label for="newCategory">New category</label>
                                <input type="text" class="form-control" name="newCategory" id="newCategory" placeholder="Enter new category name" required=""> 
                            </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="handleSaveCategory()">Save</button>
                        <!--<button type="button" class="btn btn-primary" onclick="document.getElementById('saveAddNewCategory').submit();">Save</button>-->
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
                
            </div>
        </div>
        
        
        <!-- Modal for add new Subcategory-->
        <div class="modal fade" id="addSubcategoryModal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <!--<h4 class="modal-title">Modal Header</h4>-->
                    </div>
                    <div class="modal-body">
                            <div class="form-group">
                                <label for="newSubcategory">New subcategory</label>
                                <input type="text" class="form-control" name="newSubcategory" id="newSubcategory" placeholder="Enter new Subcategory name" required=""> 
                            </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="handleSaveSubcategory()">Save</button>
                        <!--<button type="button" class="btn btn-primary" onclick="document.getElementById('saveAddNewCategory').submit();">Save</button>-->
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
                
            </div>
        </div>
        
        <!-- Modal for Save button-->
        <div class="modal fade" id="saveEdit" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <!--<h4 class="modal-title">Modal Header</h4>-->
                    </div>
                    <div class="modal-body">
                         Are you sure to update ?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="document.getElementById('editProductInfo').submit()">Save</button>
                        <!--<button type="button" class="btn btn-primary" onclick="document.getElementById('saveAddNewCategory').submit();">Save</button>-->
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
                
            </div>
        </div>
        <div></div>

        <script>
            
        </script>

         <!--javascrip-->
        <script src="../../assets/js/marketing/productList.js"></script>
        <script src="../../assets/js/marketing/addNewProduct.js"></script>
        <script src="../../assets/js/marketing/editProduct.js"></script>
        <!--javascrip-->
        <script src="../../assets/js/admin/main.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
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
