/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function  handleSaveCategory() {
    var category = document.getElementById('newCategory').value;
    var selectCategory = document.getElementById('category');
    $.ajax({
        url: '/sale/addCategoryProduct',
        type: 'POST',
        data: {
            newCategory: category
        },
        success: function (data, textStatus, jqXHR) {
            selectCategory.innerHTML += data;
            alert('Add new category success!');
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert('The category name was existed!');
        }
    });
}

function handleShowSubCategory() {
    $.ajax({
        url: '/sale/addSubCategoryProduct',
        type: 'GET',
        data: {
            categoryId: document.getElementById('category').value
        },
        success: function (data, textStatus, jqXHR) {
            document.getElementById('subCategory').innerHTML = data;
        }
    });
}



function handleSaveSubcategory() {
    var selectCategory = document.getElementById('subCategory');
    $.ajax({
        url: '/sale/addSubCategoryProduct',
        type: 'POST',
        data: {
            categoryId: document.getElementById('category').value,
            subCategory: document.getElementById('newSubcategory').value
        },
        success: function (data, textStatus, jqXHR) {
            selectCategory.innerHTML += data;
            alert('Add new subcategory success!');
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert('The subcategory name was existed!');
        }
    });
}





function showThumbnail() {
    const [file] = document.getElementById('thumbnail').files;
    if (file) {
        document.getElementById('closeBtn-thumbnailBoxImg').style.display = "block";
        document.getElementById('thumbnailBoxImg').style.display = "block";
        document.getElementById('thumbnailBoxImg').src = URL.createObjectURL(file)
    }
}

function closeImg(imgId, close_btnId, input_img) {
    var img = document.getElementById(imgId);
    var close_btn = document.getElementById(close_btnId);
    var input_img = document.getElementById(input_img);
    img.style.display = "none";
    close_btn.style.display = "none";
    input_img.value = '';
}

function showAttachedImg(input_img, imgBox, closeBtn) {
//    const [file] = document.getElementById(input_img).files;
//    if (file) {
//        document.getElementById(closeBtn).style.display = "block";
//        document.getElementById(imgBox).style.display = "block";
//        document.getElementById(imgBox).src = URL.createObjectURL(file)
//    }
    const [file] = document.getElementById('attachedImg1').files;
    if (file) {
        document.getElementById('closeBtn-attachedBoxImg1').style.display = "block";
        document.getElementById('attachedBoxImg1').style.display = "block";
        document.getElementById('attachedBoxImg1').src = URL.createObjectURL(file)
    }
}

function showAttachedImg(img, close_btm, input){
    const [file] = document.getElementById(input).files;
    if (file) {
        document.getElementById(close_btm).style.display = "block";
        document.getElementById(img).style.display = "block";
        document.getElementById(img).src = URL.createObjectURL(file)
    }
}


function validateForm(){
    var sellerId = document.getElementById('sellerId');
    if(sellerId === null){
        alert('You must chose seller!');
//        window.history.back();
    }
}
