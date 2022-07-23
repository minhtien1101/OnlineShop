function addToCartFunction2(id, quantity, customer) {
    var customerId = customer >= 1  ? customer : -1;
    var productId = id;
    var quantityOrder = 1;
//    var quantityProduct = 999;
//    
//    var divAlterQuantityOrder = document.getElementById('alter_quantityOrder');

//    if(typeof (quantityOrder) === 'string'){
//        divAlterQuantityOrder.innerHTML = "<div class='alert alert-danger' style='padding: 10px;width: 75%;'>Quantity must be a number! </div>";
//        return ;
//    }
    if (customerId === -1) {
        window.location.href = "/login";
        window.location.assign("http://localhost:8080/login");
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

//            divAlterQuantityOrder.innerHTML = "";
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(ajaxOptions);
        }
    });

}