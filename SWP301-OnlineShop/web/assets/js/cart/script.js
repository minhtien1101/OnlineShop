/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$('#btn-checkout-1').click(function (e) {
    var priceTotal = 0;
    $("#calculator input[type='checkbox']:checked").each(function () {
        var price = $(this).attr('data-price');
        priceTotal += parseInt(price);
    });
    if (priceTotal == 0) {
        jQuery.noConflict();
        $('#confirm-choose-checkbox').modal('show');
    } else {
        $('#form-cart-id').submit();
    }
});

$('.btn-ok-choose').click(function () {
    $('#confirm-choose-checkbox').modal('toggle');
});

$(document).ready(function () {
    $("input[type='checkbox']").click(function () {
        var priceTotal = 0;
        var priceTotal2 = 0;
        $("#calculator input[type='checkbox']:checked").each(function () {
            var price = $(this).attr('data-price');
            priceTotal += parseInt(price);
        });
        $("#calculator input[type='checkbox']").each(function () {
            var price = $(this).attr('data-price');
            priceTotal2 += parseInt(price);
        });
        if (priceTotal == priceTotal2) {
            $('#checkall').prop("checked", true);
        } else {
            $('#checkall').prop("checked", false);
        }
        var output = parseInt(priceTotal).toLocaleString();
        $('#total').html(output);
        $('#total-hidden').val(priceTotal);
    });

});

$('#checkall').change(function () {
    if (this.checked) {
        $('.cb-element').prop('checked', false);
        $('#checkall').prop('checked', false);
    } else {
        $('.cb-element').prop('checked', true);
        $('#checkall').prop('checked', true);
    }
//    $('.cb-element').prop('checked', this.checked);
    var priceTotal = 0;
    $("#calculator input[type='checkbox']:checked").each(function () {
        var price = $(this).attr('data-price');
        priceTotal += parseInt(price);
    });
    var output = parseInt(priceTotal).toLocaleString();
    $('#total').html(output);
    $('#total-hidden').val(priceTotal);
});

$('.cart_quantity_delete, .delete-all-product').on('click', function (e) {
    var pid = $(this).attr('data-programid');
    var name = $(this).attr('data-name');
    var isAll = $(this).attr('data-isAll');


    $('#app_id').val(pid);
    $('#app_isAll').val(isAll);
    if ($(e.target).attr('class') == 'delete-all-product') {
        $('#cfm').text('All products in the cart will be deleted.');
    } else {
        $('#cfm').text('The product (' + name + ') will be removed from the cart.');
    }
    jQuery.noConflict();
    $('#confirm-delete').modal({
        backdrop: "static",
        keybroad: true,
        show: true
    });
});

$('.btn-ok').on('click', function () {

    var pid = $('#app_id').val();
    var cid = $('#app_cid').val();
    var isAll = $('#app_isAll').val();
//                            console.log(123);


    $.ajax({
        url: "/deleteCart",
        type: "get", //send it through get method
        data: {
            pid: pid,
            cid: cid,
            isAll: isAll
        },
        success: function (response) {
            //Do Something
            $('#confirm-delete').modal('toggle');
//                                    $('#content').html(response);
            if (isAll == 1) {
                $('.delete-all').remove();
                location.reload();
            } else {
                $('#div-product-' + pid).remove();

                var priceTotal = 0;
                $("#calculator input[type='checkbox']:checked").each(function () {
                    var price = $(this).attr('data-price');
                    priceTotal += parseInt(price);
                });
                var output = parseInt(priceTotal).toLocaleString();
                $('#total').html(output);
                $('#total-hidden').val(priceTotal);




            }

        },
        error: function (xhr) {
            //Do Something to handle error
            console.log("xhr");
            console.log(xhr);
        }
    });
});


$('#btn-checkout').click(function () {
    var count = 0 * 1;
    var listItem = $('.cb-element:checked');
    var url = "cartCompletion?";
    listItem.each(function () {
        if (count == listItem.length - 1) {
            url += "id=" + $(this).val();
        } else {
            url += "id=" + $(this).val() + "&";
        }
        count++;

    });
    if (listItem.length >= 1) {
        window.location.href = url;
    }

});


$('.cart_quantity_up, .cart_quantity_down').on('click', function (e) {
    var pid = $(e.target).attr('id-product-quantity');
    var isUp = $(e.target).attr('is-up');
    var cartId = $(e.target).attr('cart-id');
    var currentQ = $('#input-' + pid).val();
    var price = parseFloat($('#input-' + pid).attr('data-price-1'));
    var priceTotal = parseFloat($('.h_cart_total_price_' + pid).val());
    var priceTotal1 = parseFloat($('.h_cart_total_price_' + pid).val());
    var checked;
//    if ($('#cbo-' + pid).prop('checked')) {
//        sessionStorage.setItem('save_order', pid);
//    }
//
//    alert(checked);

    if ($(e.target).attr('class') == 'cart_quantity_up') {
        changeQ = parseInt(currentQ) + 1;
        priceTotal += price;
        if (changeQ == $('#id-up-' + pid).data('max')) {
            $('#id-up-' + pid).prop('disabled', true);
        } else {
            $('#id-down-' + pid).prop('disabled', false);
            $('#id-up-' + pid).prop('disabled', false);
        }
    } else {
        changeQ = parseInt(currentQ) - 1;
        priceTotal -= price;
        if (changeQ == $('#id-down-' + pid).data('min')) {
            $('#id-down-' + pid).prop('disabled', true);
        } else {
            $('#id-up-' + pid).prop('disabled', false);
            $('#id-down-' + pid).prop('disabled', false);
        }
    }

    //Set value for input hidden
    $('#input-' + pid).val(changeQ);

    //Set value total price
    var output = parseInt(priceTotal).toLocaleString();
    $('.cart_total_price_' + pid).text(output);
    $('.h_cart_total_price_' + pid).val(priceTotal);

    $('#cbo-' + pid).attr('data-price', priceTotal);
    if ($('#cbo-' + pid + ':checked')) {
        var totalLast = parseFloat($('#total-hidden').val());

        if (totalLast > 0) {
            totalLast = (totalLast - priceTotal1) + priceTotal;
        }

        var output = parseInt(totalLast).toLocaleString();
    }
    $.ajax({
        url: "/cartDetails",
        type: "post", //send it through get method
        data: {
            pid: pid,
            isUp: isUp,
            cartId: cartId
        },
        success: function (response) {
            //Do Something
//            window.location.reload();
            $('#quantity-id-' + pid).val(changeQ);
            $('#total').html(output);
            $('#total-hidden').val(totalLast);
        },
        error: function (xhr) {
            //Do Something to handle error
            console.log("xhr");
        }
    });

});


$('#change-id').click(function () {
    jQuery.noConflict();
    $('#confirm-change').modal({
        backdrop: 'static',
        keyboard: true,
        show: true
    });

});

$('.btn-update').on('click', function () {

    var fullName = $('#iFullName').val();
    var email = $('#iEmail').val();
    var mobile = $('#iMobile').val();
    var address = $('#iAddress').val();
    if (fullName.length == 0) {
        return false;
    }
    if (mobile.length == 0) {
        return false;
    }
    if (address.length == 0) {
        return false;
    }


    $.ajax({
        url: "/cartContact",
        type: "post", //send it through get method
        data: {
            fullName: fullName,
            email: email,
            mobile: mobile,
            address: address
        },
        success: function (response) {
            //Do Something

            $('#name-id-h').val(fullName);
            $('#phone-id-h').val(mobile);
            $('#address-id-h').val(address);
            $('#confirm-change').modal('toggle');
            $('#address-id').html(response);
            //Alert
            toastr.success("Edit Information Sucessfull!");

        },
        error: function (xhr) {

        }
    });

});

$('.cart_quantity_input').on('change', function () {
    var quantityInput = $(this).val();
    var pid = $(this).data('product-id');
    var productQuantity = $(this).data('max');
    var cid = $('#id-down-' + pid).attr('cart-id');
//    var price = $('#input-' + pid).data('price-1');

    jQuery.noConflict();
    if (quantityInput > productQuantity) {
        $('#content-quantity').html('There are only ' + productQuantity + ' quantity remaining for this item');
        quantityInput = productQuantity;
        $.ajax({
            url: "/cartDetails",
            type: "post", //send it through get method
            data: {
                pid: pid,
                isUp: -1,
                cartId: cid,
                quantity: quantityInput
            },
            success: function (response) {

            },
            error: function (xhr) {
            }
        });
        $('#confirm-enter-quantity').modal({
            backdrop: 'static',
            keyboard: true,
            show: true
        });
    } else {
        $.ajax({
            url: "/cartDetails",
            type: "post", //send it through get method
            data: {
                pid: pid,
                isUp: -1,
                cartId: cid,
                quantity: quantityInput
            },
            success: function (response) {
//            $('#show-quantity-' + pid).html(response);
//            var priceTotal = parseFloat(price) * parseInt(quantityInput);
//            //Set value total price
//            var output = parseInt(priceTotal).toLocaleString();
//            $('.cart_total_price_' + pid).text(output);
//            $('.h_cart_total_price_' + pid).val(priceTotal);
                location.reload();

            },
            error: function (xhr) {
            }
        });
    }





});

$('#back-cart-detail').click(function () {
    window.history.back();
});

$('.btn-ok-quantity').click(function () {
    $('#confirm-enter-quantity').modal('toggle');
    location.reload();
});

$('#btn-submit').click(function () {
    $.ajax({
        url: "/errorContact",
        type: "get", //send it through get method
        success: function (response) {
            $('#form-contact').submit();
        },
        error: function (xhr) {
            $('#form-contact').attr('action', 'errorContact');
            $('#form-contact').attr('method', 'post');
            $('#form-contact').submit();
        }
    });

});





