/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function() {
  // Gets the span width of the filled-ratings span
  // this will be the same for each rating
  var star_rating_width = $('.fill-ratings span').width();
  // Sets the container of the ratings to span width
  // thus the percentages in mobile will never be wrong
  $('.star-ratings').width(star_rating_width);
});


function  openFeedbackImg(id) {
    var divImage = document.getElementById('feedback-img-zoom-' + id);
    console.log('feedback-img-zoom-' + id);
    console.log(divImage);
    $.ajax({
        url: '/handlegetdata',
        type: 'POST',
        data: {
            feedbackId: id,
            action: "getFeedbackImageModal"
        },
        success: function (data, textStatus, jqXHR) {
            divImage.innerHTML = data;
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert("daw");
        }
    });
}


function closeFeedbackImage(divId) {
    var divImage = document.getElementById('feedback-img-zoom-' + divId);
    divImage.innerHTML = "";
}


function addToCartFunction() {
    var customerId = document.getElementById('customerId').value === "" ? -1 : document.getElementById('customerId').value;
    var productId = document.getElementById('productId').value;
    var quantityOrder = document.getElementById('quantityOrder').value;
    var quantityProduct = document.getElementById('productQuantity').value;
    
    
    var divAlterQuantityOrder = document.getElementById('alter_quantityOrder');
    
//    if(typeof (quantityOrder) === 'string'){
//        divAlterQuantityOrder.innerHTML = "<div class='alert alert-danger' style='padding: 10px;width: 75%;'>Quantity must be a number! </div>";
//        return ;
//    }
    if (customerId === -1) {
        window.location.href = "/login";
        window.location.assign("http://localhost:8080/login");
        return ;
    }
    if(quantityOrder <= 0){
        divAlterQuantityOrder.innerHTML = "<div class='alert alert-danger' style='padding: 10px;width: 75%;'>Quantity must be greater than 1! </div>";
        return ;
    }

    if(quantityOrder - 0 > quantityProduct - 0){
        divAlterQuantityOrder.innerHTML = "<div class='alert alert-danger' style='padding: 10px;width: 75%;'>Quantity must be less than " + quantityProduct + "!"+"</div>";
        return ;
    }
    
    
    $.ajax({
        url: '/addProductToCart',
        type: 'POST',
        data: {
            customerId: customerId,
            quantityOrder: quantityOrder,
            productId: productId
        },
        success: function (data, textStatus, jqXHR) {
            var divAlter = document.getElementById('add-to-cart-alter');
            divAlter.innerHTML = "<div class='add-to-cart-show'>\n"
                    + '<div class="add-to-cart-show__icon-check"><i class="fa-solid fa-circle-check icon-check"></i></div>\n'
                    + '<div class="add-to-cart-show__content">Product was added in to Cart!</div>\n'
                    + '</div>\n';
            setTimeout(function () {
                divAlter.innerHTML = "";
            }, 3000);
            
            divAlterQuantityOrder.innerHTML="";
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(ajaxOptions);
        }
    });

}

function ZoomProductImage(productId){
    var container = document.getElementById('carouse-zoom');
    $.ajax({
        url: '/handlegetdata',
        type: 'POST',
        data: {
            productId: productId,
            action:"zoomImageProduct"
        },
        success: function (data, textStatus, jqXHR) {
            container.innerHTML = data;
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(ajaxOptions);
        }
    });
}

$(document).click((event) => {
  if (!$(event.target).closest('#carouse-zoom').length) {
      document.getElementById('carouse-zoom').innerHTML = "";
  }        
});

                        
                          





            