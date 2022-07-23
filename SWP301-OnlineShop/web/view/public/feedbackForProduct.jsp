<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Feedback Product</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../../assets/public/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../assets/public/css/font-awesome.min.css" rel="stylesheet">
        <link href="../../assets/public/css/prettyPhoto.css" rel="stylesheet">
        <link href="../../assets/public/css/price-range.css" rel="stylesheet">
        <link href="../../assets/public/css/animate.css" rel="stylesheet">
        <!--css-->
        <link href="../../assets/css/admin/userList.css" rel="stylesheet" type="text/css"/>
        <!--<link href="../../assets/css/admin/main.css" rel="stylesheet" type="text/css"/>-->

        <link href="../../assets/public/css/main.css" rel="stylesheet">
        <link href="../../assets/public/css/responsive.css" rel="stylesheet">
        <!-- font Awesome -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="../../assets/public/css/style.css" rel="stylesheet">
        <link href="../../assets/css/admin/feedback.css" rel="stylesheet">

        <!--css for star-->
        <link href="../../assets/css/admin/feedbackForProduct.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <c:set var="userBuyInfor" value="${requestScope.userInformation}"/>
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
                            <h2 class="title text-center" style="border-bottom: solid 2px; margin-top: 10px">Feedback Product</h2>
                            <section class="panel">

                                <div class="panel-body">

                                    <form action="/feedbackProduct" method="post" id="submitForm" enctype="multipart/form-data">
                                        <input type="hidden" name="userID" value="${sessionScope.user.id}">
                                        <input type="hidden" name="productID" value="${requestScope.productID}">
                                        <input type="hidden" name="orderID" value="${requestScope.orderID}">
                                        <div class="row">

                                            <div class="col-md-12 form-group">
                                                <p>
                                                    <label>Full Name: </label>
                                                    <span> ${userBuyInfor.fullname}</span> 
                                                    <input type="hidden" name="fullname" value="${userBuyInfor.fullname}">
                                                </p>
                                                <p>
                                                    <label> Gender: </label>
                                                    <span> ${userBuyInfor.gender == true ? "Male" : "Female"}</span> 
                                                    <input type="hidden" name="gender" value="${userBuyInfor.gender}">
                                                </p>
                                                <p>
                                                    <label>Email: </label>
                                                    <span> ${userBuyInfor.email}</span> 
                                                    <input type="hidden" name="email" value="${userBuyInfor.email}">
                                                </p><p>
                                                    <label> Mobile: </label>
                                                    <span> ${userBuyInfor.mobile} </span> 
                                                    <input type="hidden" name="mobile" value="${userBuyInfor.mobile}">
                                                </p>
                                            </div>
                                            <div class="col-md-6 form-group"  style="display: flex;">
                                                <div class="center">
                                                    <label> Star: </label> 
                                                    <div class="stars">
                                                        <input class="star star-5" id="star-5" type="radio" name="star" value="5"/>
                                                        <label class="star star-5" for="star-5"></label>
                                                        <input class="star star-4" id="star-4" type="radio" name="star" value="4"/>
                                                        <label class="star star-4" for="star-4"></label>
                                                        <input class="star star-3" id="star-3" type="radio" name="star" value="3"/>
                                                        <label class="star star-3" for="star-3"></label>
                                                        <input class="star star-2" id="star-2" type="radio" name="star" value="2"/>
                                                        <label class="star star-2" for="star-2"></label>
                                                        <input class="star star-1" id="star-1" type="radio" name="star" value="1" checked=""/>
                                                        <label class="star star-1" for="star-1"></label>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="col-md-12 form-group" style="margin: inherit;">
                                                <div class="col-md-6 form-group">
                                                    <label for="story">Comment: </label>

                                                    <textarea id="story" name="commnet"rows="5" cols="33"> </textarea>
                                                </div>
                                            </div>
                                            <div class="col-md-12 form-group" style="margin: inherit;">

                                                <div class="col-md-6 form-group">
                                                    <!--Attached image-->
                                                    <div class="form-group w-70-percent mx-auto">
                                                        <label >Feedback Image</label>

                                                        <div class="display-flex">
                                                            <div>
                                                                 <div  class="w-100percent image-input-layout display-flex" style="position: relative; width: fit-content;">
                                                                    <div id="closeBtn-imgBoxfeedback-1" class="close-btn" onclick="closeImg('imgBoxfeedback-1', 'closeBtn-imgBoxfeedback-1', 'imgfeedback1');"> <i class="fa-solid fa-xmark close-btn"></i></div>
                                                                    <img id="imgBoxfeedback-1" src="#" alt="your image" style="height:150px;" class="boder-radius display-none image-thumbnail"/>
                                                                </div>
                                                                <input type="file" name="imgfeedback1" id="imgfeedback1" onchange="showAttachedImgFeedback('imgBoxfeedback-1', 'closeBtn-imgBoxfeedback-1', 'imgfeedback1')" required=""/>
                                                            </div>
                                                            <div>
                                                                 <div  class="w-100percent image-input-layout display-flex" style="position: relative; width: fit-content;">
                                                                    <div id="closeBtn-imgBoxfeedback-2" class="close-btn" onclick="closeImg('imgBoxfeedback-2', 'closeBtn-imgBoxfeedback-2', 'imgfeedback2');"> <i class="fa-solid fa-xmark close-btn"></i></div>
                                                                    <img id="imgBoxfeedback-2" src="#" alt="your image" style="height:150px;" class="boder-radius display-none image-thumbnail"/>
                                                                </div>
                                                                <input type="file" name="imgfeedback2" id="imgfeedback2" onchange="showAttachedImgFeedback('imgBoxfeedback-2', 'closeBtn-imgBoxfeedback-2', 'imgfeedback2')" required=""/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!--/Attacked image-->
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-offset-1 pull-right " >
                                                <button onclick="return confirm('Are you sure to feedback this product?')" class="btn btn-info" >Feedback</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </section>
                        </div>
                    </div>

                </div>

            </div>
            <div id="add-to-cart-alter"></div>
        </section>

        <jsp:include page="../home-template/footer.jsp"/>
        <script language="JavaScript" type="text/javascript">
            setTimeout(function () {
                const element = document.getElementById('showAlter');
                element.remove();
            }, 3000);
        </script>
        <script src="../../assets/js/feedbackk/feedback.js" type="text/javascript"></script>
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