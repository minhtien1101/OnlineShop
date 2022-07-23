/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


//Submit form search and fiter onchange 
$(document).ready(function () {
    $('#roleId').on('change', function () {
        this.form.submit();
    });
    $('#gender').on('change', function () {
        this.form.submit();
    });
    $('#status').on('change', function () {
        this.form.submit();
    });
    $('#page').on('change', function () {
        this.form.submit();
    });
    $('#search').on('change', function () {
        this.form.submit();
    });
    $('#sort').on('change', function () {
        this.form.submit();
    });
    $('#orderBy').on('change', function () {
        this.form.submit();
    });
});

// set active button in nav bar
var element = document.getElementById("nav-user-list");
element.classList.add("active");

// js for modal
function openModal(id) {
    var button = document.getElementById('clickChangeStatus');
    button.innerHTML = '';
    button.setAttribute('class', "btn btn-primary");
    button.setAttribute('onclick', 'document.getElementById("' + id + '").submit();');
    button.innerHTML = 'Yes';
}



//js for paggig

function paggerFistPage(content) {
    var href = 'userList?xpage=1&' + content;
    window.location.href = href;
}

function paggerPage(page, content) {
    var href = 'userList?xpage=' + page + '&' + content;
    window.location.href = href;
}

function paggeLastPage(totalPage, content) {
    var href = 'userList?xpage=' + totalPage + '&' + content;
    window.location.href = href;
}

