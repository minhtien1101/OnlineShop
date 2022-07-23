/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


$('.login-content [data-toggle="flip"]').click(function () {
    $('.login-box').toggleClass('flipped');
    return false;
});

$(document).ready(function () {
    $("#basic-form").validate();
});

setTimeout(function () {
    // Closing the alert
    $('#alert').alert('close');
}, 7000);


 