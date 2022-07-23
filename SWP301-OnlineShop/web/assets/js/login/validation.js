/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
$().ready(function () {
    $("#passwordForm").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {

            "txtPassword1": {
                required: true,
                validatePassword: true,
            },
            "txtPassword2": {
                equalTo: "#password1",
            }
        }
    });
});

$.validator.addMethod("validatePassword", function (value, element) {
    return this.optional(element) || /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$/i.test(value);
}, "Password from 8 - 16 characters including uppercase, lowercase and at least one number");



$().ready(function () {
    $("#registerForm1").validate({
        rules: {
            "txtName": {
                required: true,
                maxlength: 30
            },
            "txtEmail": {
                required: true
            },
            "txtPassword": {
                required: true,
                validatePassword: true
            },
            "txtPassword1": {
                equalTo: "#password"
            },
            "txtMobile": {
                required: true,
                validateMobile: true
            },
            "txtAddress": {
                required: true,
                minlength: 5
            }
        }
    });
});

$.validator.addMethod("validatePassword", function (value, element) {
    return this.optional(element) || /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$/i.test(value);
}, "Password from 8 - 16 characters including uppercase, lowercase and at least one number");

//$.validator.addMethod("validateName", function (value, element) {
//    return this.optional(element) || /^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$/.test(value);
//}, "Invalid full name!");

$.validator.addMethod("validateMobile", function (value, element) {
    return this.optional(element) || /^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$/g.test(value);
}, "Phone number is not in correct format! (Example +(123) - 456-78-90)");


