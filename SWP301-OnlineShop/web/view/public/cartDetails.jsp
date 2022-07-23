<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en"> 

    <head>

        <script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
        <script data-require="bootstrap@*" data-semver="3.1.1" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
        <link data-require="bootstrap-css@3.1.1" data-semver="3.1.1" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
        <meta charset="UTF-8">
        <title>Cart Details</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../../assets/public/css/bootstrap.min.css" rel="stylesheet">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/lg.png" />
        <link href="../../assets/public/css/font-awesome.min.css" rel="stylesheet">
        <link href="../../assets/public/css/prettyPhoto.css" rel="stylesheet">
        <link href="../../assets/public/css/price-range.css" rel="stylesheet">
        <link href="../../assets/public/css/animate.css" rel="stylesheet">
        <link href="../../assets/public/css/main.css" rel="stylesheet">
        <link href="../../assets/public/css/responsive.css" rel="stylesheet">
        <link href="../../assets/public/css/style.css" rel="stylesheet">
        <link href="../../assets/css/cart/style.css" rel="stylesheet">


    </head>
    <!--/head-->

    <body>
        <jsp:include page="../home-template/headerProductlist.jsp"/>
        <section id="cart_items">
            <div class="container">
                <div class="row flex-justify">
                    <jsp:include page="../home-template/sidebarForProductList.jsp"/>
                    <!--PRODUCT LIST-->


                    <div class="col-sm-9 padding-right">
                        <div class="features_items">
                            <!--features_items-->
                            <h2 class="title text-center" style="border-bottom: solid 2px; margin-top: 10px">Cart</h2>
                            <!--Show product-->
                            <!--<form action="/cartCompletion" method="GET" id="submitCart">-->
                            <form action="/cartContact" method="get" id="form-cart-id">
                                <c:choose>

                                    <c:when test="${not empty carts}">
                                        <div class="table-responsive cart_info">
                                            <table class="table table-condensed">
                                                <thead>
                                                    <tr class="cart_menu">
                                                        <td><input type="checkbox" name="all" id="checkall"> All</td>
                                                        <td class="image">Item</td>
                                                        <td class="description"></td>
                                                        <td class="price">Price</td>
                                                        <td class="quantity">Quantity</td>
                                                        <td>Total</td>
                                                        <td></td>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <c:forEach var="i" items="${carts}">    
                                                    
                                                        <tr id="div-product-${(i.product).id}" class="delete-all ${(i.product).quantity < 1? "disabledbutton":""}">
                                                          
                                                            <td class="cart_description ${(i.product).quantity < 1? "down-size":""}">
                                                                <c:if test="${(i.product).quantity > 0}"><div id="calculator"><input name="cboproduct"  id="cbo-${(i.product).id}" type="checkbox" value="${(i.product).id}" data-price="${i.quantity * (i.product).getPriceDiscount()}" class="cb-element"></div></c:if>
                                                                <c:if test="${(i.product).quantity < 1}"><h6 class="out-stock">OUT OF STOCK</h6></c:if>
                                                            </td>
                                                                <input type="hidden" name="hCartId" value="${cartId}"/>

                                                               
                                                            <td class="cart_product" style="width: 180px">
                                                                <a href=""><img src="${(i.product).thumbnail}" alt="" width="100px" height="auto"></a>
                                                            </td>
                                                            <td class="cart_description">
                                                                <h4><a href="">${(i.product).name}</a></h4>

                                                                <b>Quantity: ${(i.product).quantity}</b>
                                                            </td>
                                                            <td class="cart_price">

                                                                <span class="text-line-through">
                                                                    <fmt:formatNumber type = "number" value = "${(i.product).price}"/> đ
                                                                </span>
                                                                <span class="text-danger">
                                                                    <fmt:formatNumber type = "number" value = "${(i.product).getPriceDiscount()}"/> đ
                                                                </span>
                                                            </td>


                                                            <td class="cart_quantity">
                                               
                                                                <div class="cart_quantity_button" id="div-contain-${(i.product).id}">
                                                                    
                                                                    <input type="button" class="cart_quantity_down" ${i.quantity==1?"disabled":""} is-up="0"  id="id-down-${(i.product).id}" id-product-quantity="${(i.product).id}" cart-id="${cartId}" value="-" data-min="1"/>

                                                                    <div id="show-quantity-${(i.product).id}"><input id="quantity-id-${(i.product).id}" data-product-id="${(i.product).id}" class="cart_quantity_input" type="number" value="${i.quantity}" autocomplete="off" size="2" data-max="${(i.product).quantity}"
                                                                                                                     onkeydown="return event.keyCode !== 69" min="1" oninput="this.value = 
                                                                                                                       !!this.value && Math.abs(this.value) >= 1 ? Math.abs(this.value) : null"></div>
                                                                    <input id="input-${(i.product).id}" class="cart_quantity_input" type="hidden" value="${i.quantity}" autocomplete="off" size="2" data-price-1="${(i.product).getPriceDiscount()}">

                                                                    <input type="button" id="id-up-${(i.product).id}" ${i.quantity==(i.product).quantity?"disabled":""} class="cart_quantity_up" is-up="1" id-product-quantity="${(i.product).id}" cart-id="${cartId}" value="+" data-max="${(i.product).quantity}"/>
                                                                </div>
                                                            </td>
                                                            <td class="cart_total">
                                                                <b style="display: flex;"><p class="cart_total_price_${(i.product).id}"> <fmt:formatNumber type = "number" value = "${i.quantity * (i.product).getPriceDiscount()}"/></p>&nbsp;đ</b>
                                                                <input type="hidden" class="h_cart_total_price_${(i.product).id}" value="${i.quantity * (i.product).getPriceDiscount()}"/>

                                                            </td>
                                                            
                                                            <td class="cart_delete">
                                                                <a class="cart_quantity_delete" id="delete_button_id" data-programid="${(i.product).id}" data-name="${(i.product).name}" data-isAll="0">
                                                                    <i class="fa fa-times"></i></a>
                                                            </td>
                                                        </tr>
                                                    
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!--</form>-->
                                        <section id="do_action">
                                            <div class="container">

                                                <div class="row">
                                                    <div class="col-sm-9">
                                                        <div class="total_area">
                                                            <ul>
                                                                <li><a class="delete-all-product" data-isAll="1" data-programid="-1" data-name="">Delete all</a></li>
<!--                                                                <input type="checkbox" name="all" id="checkall"> Select all-->

                                                                <li style="display: flex"><div>Total</div> <div style="margin-left: auto"><b><span id="total">0</span></b></div>&nbsp;đ</li>
                                                                <input type="hidden" id="total-hidden" value="0"/>
                                                            </ul>
                                                            <ul>

                                                                <a class="btn btn-default update" href="/productlist">Choose More Product</a>
                                                                <a class="btn btn-default update" id="btn-checkout-1">Check Out</a>
                                                                <!--                                                                <button class="btn btn-default check_out" id="btn-checkout-1" type="submit">Check Out</button>-->

                                                            </ul>


                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </section>

                                    </c:when>

                                    <c:otherwise>

                                        <div class="alert alert-danger" role="alert">
                                            <h4 id="empty-cart">There are no products in the shopping cart.</h4>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </form>                                    
                        </div>
                    </div>




                    <!--    This is start delete modal dialog-->

                    <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="myModalLabel">Confirm Delete</h4>
                                </div>

                                <div class="modal-body">
                                    <p id="cfm"></p>
                                    <p>Do you want to proceed?</p>
                                    <p class="debug-url"></p>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                    <a class="btn btn-danger btn-ok">Delete</a>
                                </div>
                                <input type="hidden" value="" id="app_id"/>
                                <input type="hidden" value="" id="app_isAll"/>
                                <input type="hidden" value="${cartId}" id="app_cid"/>
                            </div>
                        </div>
                    </div>
                    <!--    This is end delete modal dialog-->

                    <!--    This is start Choose cart product modal dialog-->
                    <div class="modal fade" id="confirm-choose-checkbox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">

                                <div class="modal-header">

                                    <h4 class="modal-title" id="myModalLabel">Confirm Choose</h4>
                                </div>

                                <div class="modal-body">
                                    <p>You haven't selected any products to buy yet!</p>
                                    <p class="debug-url"></p>
                                </div>

                                <div class="modal-footer">
                                    <a class="btn btn-danger btn-ok-choose">OK</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--    This is end Choose cart product modal dialog-->
                    
                    <!--    This is start enter quantity modal dialog-->
                    
                    <div class="modal fade" id="confirm-enter-quantity" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">

                                <div class="modal-header">

                                    <h4 class="modal-title" id="myModalLabel">Confirm</h4>
                                </div>

                                <div class="modal-body">
                                    <p id="content-quantity"></p>
                                    <p class="debug-url"></p>
                                </div>

                                <div class="modal-footer">
                                    <a class="btn btn-danger btn-ok-quantity">OK</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!--    This is end enter quantity modal dialog-->


                </div>
                <c:if test="${not empty carts}">
                    <div class="pagging">
                        <ul class="pagination pull-right">

                            <c:if test="${index != 1}">
                                <li class="page-item"><a class="page-link" href="/cartDetails?index=${1}"><i class="fa fa-angle-double-left" aria-hidden="true"></i></a></li>
                                <li class="page-item"><a class="page-link" href="/cartDetails?index=${index-1}"><i class="fa fa-angle-left" aria-hidden="true"></i></a></li>
                                    </c:if>   
                                    <c:forEach var = "i" begin = "1" end = "${lastPage}"> 
                                <li class="page-item"><a class="page-link" href="/cartDetails?index=${i}">${i}</a></li>
                                </c:forEach>
                                <c:if test="${index != lastPage}">
                                <li class="page-item"><a class="page-link" href="/cartDetails?index=${index+1}"><i class="fa fa-angle-right" aria-hidden="true"></i></a></li>
                                <li class="page-item"><a class="page-link" href="/cartDetails?index=${lastPage}"><i class="fa fa-angle-double-right" aria-hidden="true"></i></a></li>
                                    </c:if>
                        </ul>
                    </div>
                </c:if>

            </div>
        </section> <!--/#cart_items-->
        <!--        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>-->



        <jsp:include page="../home-template/footer.jsp"/>



        <script src="../../assets/js/cart/script.js"></script>
        <script src="../../assets/public/js/jquery.js"></script>
        <script src="../../assets/public/js/bootstrap.min.js"></script>
        <script src="../../assets/public/js/jquery.scrollUp.min.js"></script>
        <script src="../../assets/public/js/price-range.js"></script>
        <script src="../../assets/public/js/jquery.prettyPhoto.js"></script>
        <script src="../../assets/public/js/main.js"></script>
        <script src="../../assets/js/home/home.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.4/jquery.validate.min.js" integrity="sha512-FOhq9HThdn7ltbK8abmGn60A/EMtEzIzv1rvuh+DqzJtSGq8BRdEN0U+j0iKEIffiw/yEtVuladk6rsG4X6Uqg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    </body>

</html>