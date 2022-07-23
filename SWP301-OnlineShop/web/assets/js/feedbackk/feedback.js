/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function closeImg(imgId, close_btnId, input_img) {
    var img = document.getElementById(imgId);
    var close_btn = document.getElementById(close_btnId);
    var input_img = document.getElementById(input_img);
    img.style.display = "none";
    close_btn.style.display = "none";
    input_img.value = '';
}

//function showAttachedImgFeedback(input_img, imgBox, closeBtn) {
////    const [file] = document.getElementById(input_img).files;
////    if (file) {
////        document.getElementById(closeBtn).style.display = "block";
////        document.getElementById(imgBox).style.display = "block";
////        document.getElementById(imgBox).src = URL.createObjectURL(file)
////    }
//    const [file] = document.getElementById('attachedImg1').files;
//    if (file) {
//        document.getElementById('closeBtn-attachedBoxImg1').style.display = "block";
//        document.getElementById('attachedBoxImg1').style.display = "block";
//        document.getElementById('attachedBoxImg1').src = URL.createObjectURL(file)
//    }
//}

function showAttachedImgFeedback(img, close_btm, input){
    const [file] = document.getElementById(input).files;
    if (file) {
        document.getElementById(close_btm).style.display = "block";
        document.getElementById(img).style.display = "block";
        document.getElementById(img).src = URL.createObjectURL(file)
    }
}


