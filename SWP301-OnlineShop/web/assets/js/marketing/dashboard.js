/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//$().ready(function () {
//    $("#dateForm").validate({
//        onfocusout: false,
//        onkeyup: false,
//        onclick: false,
//        rules: {
//
//            "txtStart": {
//
//                checkDate: true
//            },
//            "txtEnd": {
//                checkDate: true
//            }
//        }
//    });
//});

//$.validator.addMethod("checkDate", function(value, element, params) {
//
//    if (!/Invalid|NaN/.test(new Date(value))) {
//        return new Date(value) > new Date($(params).val());
//    }
//
//    return isNaN(value) && isNaN($(params).val()) 
//        || (Number(value) > Number($(params).val())); 
//},'Must be greater than {0}.');

document.getElementById('dt').max = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split("T")[0];
document.getElementById('dt1').max = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split("T")[0];
$('#search-product').click(function () {
    var stringDate = document.getElementById('dt').value;
    var stringDate1 = document.getElementById('dt1').value;
    var myDate = new Date(stringDate);
    var myDate1 = new Date(stringDate1);
    if (myDate > myDate1) {
        $('#notefi').html('The start date must be less than the end date!');

        return false;
    } else {
        $('#notefi').html('');
        $('#form-search-product').submit();
    }
});
//function checkDate2() {
//    var stringDate = document.getElementById('dt').value;
//    var stringDate1 = document.getElementById('dt1').value;
//    var myDate = new Date(stringDate);
//    var myDate1 = new Date(stringDate1);
//    if (myDate > myDate1) {
//        $('#notefi').html('The start date must be less than the end date!');
//
//        document.getElementById("dateForm").reset();
//        return false;
//    } else {
//        $('#notefi').html('');
//        return true;
//    }
//
//}




//Input date saller
//$().ready(function () {
//    $("#dateForm1").validate({
//        onfocusout: false,
//        onkeyup: false,
//        onclick: false,
//        rules: {
//
//            "txtStartSel": {
//
//                checkDate: true
//            },
//            "txtEndSel": {
//                checkDate: true
//            }
//        }
//    });
//});

document.getElementById('dtS').max = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split("T")[0];
document.getElementById('dt1S').max = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split("T")[0];
$('#search-seller').click(function () {
    var stringDate = document.getElementById('dtS').value;
    var stringDate1 = document.getElementById('dt1S').value;
    var myDate = new Date(stringDate);
    var myDate1 = new Date(stringDate1);
    if (myDate > myDate1) {
        $('#notefi1').html('The start date must be less than the end date!');
        return false;
    } else {
        $('#notefi1').html('');
        $('#form-search-seller').submit();
    }
});
