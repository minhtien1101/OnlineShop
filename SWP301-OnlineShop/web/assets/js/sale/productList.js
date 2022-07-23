/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */




$(document).ready(function () {
    $('#categoryId').on('change', function () {
        this.form.submit();
    });
    $('#subCategoryId').on('change', function () {
        this.form.submit();
    });
    $('#featured').on('change', function () {
        this.form.submit();
    });
    $('#status').on('change', function () {
        this.form.submit();
    });

    $('#orderBy').on('change', function () {
        this.form.submit();
    });
    $('#sort').on('change', function () {
        this.form.submit();
    });
    $('#search').on('change', function () {
        this.form.submit();
    });
    $('#page').on('change', function () {
        this.form.submit();
    });
});


function submitForm(id) {
    document.getElementById(id).submit();
}


function handleShowSubCategory() {
    $.ajax({
        url: '/marketing/addSubCategoryProduct',
        type: 'GET',
        data: {
            categoryId: document.getElementById('categoryId').value
        },
        success: function (data, textStatus, jqXHR) {
            document.getElementById('subCategoryId').innerHTML = data;
        }
    });
}



function paggerPageIndex(page, content) {
    var href = 'productlist?xpage=' + page + '&' + content;
    window.location.href = href;
}
