/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


// set active button in nav bar
var element = document.getElementById("nav-user-list");
element.classList.add("active");

$(document).ready(function () {
    $("#basic-form").validate({
        rules: {
            fullname: {
                required: true,
                minlength: 3
            },
            mobile: {
                required: true,
                minlength: 10,
                maxlength: 10
//                            phonenumber:true
            },
            email: {
                required: true,
                email: true
            },
            address: {
                required: true,
                minlength: 3
            }

        },
        messages: {
            name: {
                required: "Please enter your full name",
                minlength: "Name should be at least 3 characters"
            },
            mobile: {
                required: "Please enter your mobile",
//                            
            },
            email: {
                email: "The email should be in the format: abc@domain.tld"
            },
            address: {
                required: "Please enter your address"
            }
        }
    });

//                jQuery.validator.addMethod('phonenumber', function (value, element) {
//                    var regex = '^0[0-9]{9}$';
//                    if (element.value.match(regex)) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                    , 'Invalid phone number';
//                });
});


function  validateMobile() {
    var label = document.getElementById('mobile-error');
    var value = document.getElementById('mobile').value;
    console.log(value);
    const regex = new RegExp('^0[0-9]{9}$');
    document.getElementById('mobile').classList.remove("valid");
    var form = document.getElementById('basic-form');
    if (!regex.test(value)) {
        document.getElementById('mobile-errors').innerHTML = "Phone number invalid!";
        document.getElementById('mobile').classList.add('error');
        document.getElementById('mobile').setAttribute('aria-invalid', 'true');
        if (document.getElementById('mobile-error').innerHTML == '') {
            document.getElementById('mobile-errors').style.display = 'block';
        }
        $('#basic-form').submit(function (event) {
            event.preventDefault();
            window.history.back();
        });
    } else {
        $('#basic-form').submit(function (event) {
            document.getElementById('basic-form').submit();
        });
        document.getElementById('mobile-errors').style.display = 'none';
    }
}
// remove message element
setTimeout(function () {
    const element = document.getElementById('message');
    element.remove();
}, 3000);

