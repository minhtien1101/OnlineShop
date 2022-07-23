<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Order Information</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../../assets/public/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../assets/public/css/font-awesome.min.css" rel="stylesheet">
        <link href="../../assets/public/css/prettyPhoto.css" rel="stylesheet">
        <link href="../../assets/public/css/price-range.css" rel="stylesheet">
        <link href="../../assets/public/css/animate.css" rel="stylesheet">
        <link href="../../assets/public/css/main.css" rel="stylesheet">
        <link href="../../assets/public/css/responsive.css" rel="stylesheet">
        <!-- font Awesome -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="../../assets/public/css/style.css" rel="stylesheet">
        <link href="../../assets/css/admin/feedback.css" rel="stylesheet">
    </head>

    <body>
        <c:set var="orderInfor" value="${requestScope.informationOrder}"/>
        <c:set var="userBuyInfor" value="${requestScope.userOrderInfioramtion}"/>
        <jsp:include page="../home-template/headerProductlist.jsp"/>
        <c:if test="${requestScope.alter != null}">
            <div class="fixed float-end t-55px" id="showAlter" style="    width: 21%;
                 z-index: 1024;
                 right: 36%;
                 top: 6%;
                 position: fixed;
                 ">
                <div class="alert alert-success alert-dismissible fade in" id="alterfade">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close" style="transform: rotate(90deg); top: 8%;">&times;</a>
                    ${requestScope.alter}
                </div>
            </div>
        </c:if>
        <section>
            <div class="container">
                <div class="row flex-justify">
                    <jsp:include page="../home-template/sidebarForProductList.jsp"/>
                    <!--PRODUCT LIST-->
                    <div class="col-sm-9 padding-right">
                        <div class="features_items">
                            <!--features_items-->
                            <h2 class="title text-center" style="border-bottom: solid 2px; margin-top: 10px">Order Information</h2>
                            <section class="panel">

                                <div class="panel-body">

                                    <div class="row">
                                        <!--LEFT-->
                                        <div class="col-md-6 form-group">
                                            <p>
                                                <label>ID Order: </label>
                                                <span>${orderInfor.id}</span> 
                                            </p>
                                            <p>
                                                <label> Order Date: </label>
                                                <span>${orderInfor.date}</span> 
                                            </p>
                                            <p>
                                                <label> Total Cost: </label>
                                                <span><fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${orderInfor.totalcost}"/></span> 
                                            </p>
                                            <p>
                                                <label> Status: </label>

                                                <c:if test="${orderInfor.status == 0}">
                                                <td><span class="label label-default">Cancelled</span></td>
                                            </c:if>
                                            <c:if test="${orderInfor.status == 1}">
                                                <td><span class="label label-success">Submitted</span></td>
                                            </c:if>
                                            <c:if test="${orderInfor.status == 2}">
                                                <td><span class="label label-info">Processing</span></td>
                                            </c:if>
                                            <c:if test="${orderInfor.status == 3}">
                                                <td><span class="label label-success">Shipping</span></td>
                                            </c:if>
                                            <c:if test="${orderInfor.status == 4}">
                                                <td><span class="label label-success">Completed</span></td>
                                            </c:if>
                                            </p>

                                            <c:if test="${orderInfor.status == 0}">
                                                <p>
                                                    <label> Cancel Reasions: </label>
                                                    <span>${requestScope.reasionCancel}</span> 
                                                </p>
                                            </c:if>

                                        </div>
                                        <!--RIGHT-->
                                        <div class="col-md-6 form-group">
                                            <p>
                                                <label>Full Name: </label>
                                                <span> ${userBuyInfor.fullname}</span> 
                                            </p>
                                            <p>
                                                <label> Gender: </label>
                                                <span> ${userBuyInfor.gender == true ? "Male" : "Female"}</span> 
                                            </p>
                                            <p>
                                                <label>Email: </label>
                                                <span> ${userBuyInfor.email}</span> 
                                            </p>
                                            <p>
                                                <label> Mobile: </label>
                                                <span> ${userBuyInfor.mobile} </span> 
                                            </p>
                                        </div>

                                        <div class="col-md-12 form-group">
                                            <div class="panel-body table-responsive">
                                                <table class="table table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>Thumbnail</th>
                                                            <th>Product Name</th>
                                                            <th>Category</th>
                                                            <th>Unit Price</th>
                                                            <th>Quantity</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${requestScope.listOrderProductOfUser}" var="listOrder">
                                                            <c:forEach items="${listOrder.products}" var="listProductOrder">
                                                                <tr>
                                                                    <td>
                                                                        <div style="height: 100px; width: fit-content">
                                                                            <img style="width: 100%; height: 100%; object-fit: contain" src="${listProductOrder.thumbnail}" alt="alt"/>
                                                                        </div>
                                                                    </td>
                                                                    <td>${listProductOrder.name}</td>
                                                                    <td>${listProductOrder.subCategory.category.name}</td>
                                                                    <td><fmt:formatNumber  maxFractionDigits = "3" type = "currency" value = "${listProductOrder.getUnitPrice()}"/></td>
                                                                    <td>${listProductOrder.quantity}</td>
                                                                    <td><div class="col-md-offset-0 pull-right" >
                                                                            <c:if test="${orderInfor.status == 4}">
                                                                                <c:if test="${!listOrder.isFeedback}">
                                                                                    <button class="btn btn-success " onclick="feedbackProdcut(${orderInfor.id},${listProductOrder.id},${sessionScope.user.id});">
                                                                                        Feedback
                                                                                    </button>    
                                                                                </c:if>
                                                                                <c:if test="${listOrder.isFeedback}">
                                                                                    <button class="btn btn-danger">
                                                                                        Feedbacked
                                                                                    </button>    
                                                                                </c:if>
                                                                                <button class="btn btn-info " onclick="addToCartFunction2(${listProductOrder.id},${listProductOrder.quantity},${sessionScope.user.id});">
                                                                                    ReBuy  
                                                                                </button>
                                                                            </c:if>
                                                                        </div>
                                                                    </td>
                                                                </tr>    
                                                            </c:forEach>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-1 pull-right " >
                                <%--<c:if test="${orderInfor.status == 1}">--%>
                                    <!--<a  href="#" onclick="updateOrder(${orderInfor.id})"  class="btn btn-danger"> Update </a>-->
                                    <a href="#"  class="btn btn-danger" data-toggle="modal" data-target="#myModalCancel">Cancel</a>
                                <%--</c:if>--%>
                                <a href="../myorders"  class="btn btn-danger">Back</a>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
            <div id="add-to-cart-alter"></div>
        </section>
        <!-- Modal User Profile -->
        <div class="modal fade" id="myModalCancel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog w-30percent" role="document"">
                <div class="modal-content pd-20px">
                    <div class="modal-header text-center" >
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
                        <h4 class="modal-title fw-bolder-fz-20" id="myModalLabel">Enter reasions why do you Cancel this Order?</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <form action="/cancelOrder" class="form-horizontal" method="post">
                                <input type="hidden" value="${orderInfor.id}" name="orderID">
                                <div class="form-group">
                                    <label for="Reasions"
                                           class="col-lg-3 col-sm-2 control-label">My Reasions: </label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="cancel" required="">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <div class="col-md-offset">
                                        <button type="submit" onclick="return confirm('Are you sure Cancel?')"  class="btn btn-info"> Save </button>
                                        <!--<button type="submit" class="btn btn-info">Save</button>-->
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>                   


        <jsp:include page="../home-template/footer.jsp"/>
        <script>
            function cancelOrder(id) {
                var result = confirm("Are you sure to cancel this order?");
                if (result) {
                    window.location.href = "cancelOrder?orderID=" + id
                }
            }
            function updateOrder(id) {
//                var currentUrl = window.location.pathname;
                var result = confirm("Are you sure to update this Order?");
                if (result) {
                    window.location.href = "updateOrder?orderID=" + id
                }
            }
            function feedbackProdcut(orderID, productID, userID) {
                var result = confirm("Are you sure to feedback for this product?");
                if (result) {
                    window.location.href = "feedbackProduct?orderID=" + orderID + "&productID=" + productID + "&userID=" + userID
                }
            }
            setTimeout(function () {
                const element = document.getElementById('showAlter');
                element.remove();
            }, 3000);
        </script>

        <jsp:include page="../home-template/footer.jsp"/>
        <script src="../../assets/js/home/productList.js"></script>
        <script src="../../assets/public/js/jquery.js"></script>
        <script src="../../assets/public/js/bootstrap.min.js"></script>
        <script src="../../assets/public/js/jquery.scrollUp.min.js"></script>
        <script src="../../assets/public/js/price-range.js"></script>
        <script src="../../assets/public/js/jquery.prettyPhoto.js"></script>
        <script src="../../assets/public/js/main.js"></script>
        <script src="../../assets/js/home/home.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.4/jquery.validate.min.js" integrity="sha512-FOhq9HThdn7ltbK8abmGn60A/EMtEzIzv1rvuh+DqzJtSGq8BRdEN0U+j0iKEIffiw/yEtVuladk6rsG4X6Uqg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    </body>

</html>