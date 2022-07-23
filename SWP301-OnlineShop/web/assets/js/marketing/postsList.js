/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// modal for change status
function openModals(id) {
    var button = document.getElementById('btn-change');
    document.getElementById('message-modal').innerHTML = "Are you sure to change status post?";
    var btn_status = document.querySelector('#btn-status-' + id).parentNode;
    var btn_name_status = document.querySelector('#btn-status-' + id).innerHTML.toLowerCase();
    console.log(btn_status);
    console.log(btn_name_status);

    button.onclick = function () {
        $.ajax({
            type: "POST",
            url: "../marketing/handlePost",
            data: {
                idPost: id,
                idStatus: btn_name_status,
                action: 'status'
            },
            success: function (data, textStatus, jqXHR) {
                btn_status.innerHTML = "" + data;
                document.querySelector('#section-content').innerHTML += `<div class="fixed float-end t-55px" id="showAlter">
                            <div class="alert alert-success alert-dismissible fade in" id="alterfade">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                Change Status Post Sucessfull!
                            </div>
                        </div>`;
            },
            error: function (jqXHR, textStatus, errorThrown) {

            }
        });
//        document.getElementById('showAlter').innerHTML = "Change Successfuly";
    };
}
// modal for chang featured
function openAnnouceAccept(id) {
    var button = document.getElementById('btn-change');
    document.getElementById('message-modal').innerHTML = "Are you sure to change featured post?";
    var btn_featured = document.querySelector('#btn-featured-' + id).parentNode;
    var btn_name_featured = document.querySelector('#btn-featured-' + id).innerHTML.toLowerCase();

//    button.innerHTML = '';
//    button.setAttribute('class', "btn btn-primary");
//    button.setAttribute('onclick', 'document.getElementById("' + id + '").submit();');
    button.onclick = function () {
        $.ajax({
            type: "POST",
            url: "../marketing/handlePost",
            data: {
                idPost: id,
                idFeatured: btn_name_featured,
                action: "featured"
            },
            success: function (data, textStatus, jqXHR) {
                btn_featured.innerHTML = "" + data;
                document.querySelector('#section-content').innerHTML += `<div class="fixed float-end t-55px" id="showAlter">
                            <div class="alert alert-success alert-dismissible fade in" id="alterfade">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                Change Featured Post Sucessfull!
                            </div>
                        </div>`;
            },
            error: function (jqXHR, textStatus, errorThrown) {

            }
        });
//        document.getElementById('showAlter').innerHTML = "Change Successfuly";
    };
}
//Submit form search and fiter onchange 
$(document).ready(function () {
    $('#category').on('change', function () {
        this.form.submit();
    });
    $('#author').on('change', function () {
        this.form.submit();
    });
    $('#status').on('change', function () {
        this.form.submit();
    });
    $('#search').on('keyup', function (e) {
        if (e.key === 'Enter' || e.keyCode === 13) {
            this.form.submit();
        }
    });
    $('#sort').on('change', function () {
        this.form.submit();
    });
    $('#orderBy').on('change', function () {
        this.form.submit();
    });
});

setTimeout(function () {
    const element = document.getElementById('showAlter');
    element.remove();
}, 3000);

// js paging
function paggerFistPage1(content) {
    var href = './postlist?page=1&' + content;
    window.location.href = href;
}

function paggerPage1(page, content) {
    var href = './postlist?page=' + page + '&' + content;
    window.location.href = href;
}

function paggeLastPage1(totalPage, content) {
    var href = './postlist?page=' + totalPage + '&' + content;
    window.location.href = href;
}

setTimeout(function () {
    const element = document.getElementById('showAlter');
    element.remove();
}, 3000);