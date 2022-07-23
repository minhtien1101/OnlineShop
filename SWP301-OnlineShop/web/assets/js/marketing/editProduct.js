/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function closeImgage(imgId, close_btnId, input) {
    var img = document.getElementById(imgId);
    var close_btn = document.getElementById(close_btnId);
    var input_img = document.getElementById(input);
    img.style.display = "none";
    close_btn.style.display = "none";
    input_img.style.display = 'block';
    input_img.value = "";
    input_img.required = true;
}


//function checkFormInput(){
//    var sellerId = document.getElementById('sellerId');
//    var form = document.getElementById('myForm');
//    if(sellerId === null){
////        form.doNotSubmit();
////        document.getElementById('myForm').doNotSubmit();
//        alert('You must chose seller!');
//        
//        window.history.back();
//    }
//}

function mySubmitFunction(){
    var sellerId = document.getElementById('sellerId');
    if(sellerId === null){
        alert('You must chose seller!');
        return false;
    }
    return true;
}