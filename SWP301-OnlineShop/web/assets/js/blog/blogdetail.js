/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


// search box
function search(searchContentCategory) {
    $("#search-box").on('keyup', function (e) {
        var search = document.querySelector('#search-box').value;
        if (e.key === 'Enter' || e.keyCode === 13) {
            var url = "bloglist?search=" + search;
            if (searchContentCategory != -1) {
                url += "&category=" + searchContentCategory;
            }
            window.location.href = url;
        }
    });
}


// handle box search
function handleAttributeBoxSearch(content) {
    if (content) {
        document.querySelector('#search-box').setAttribute('value', content);
    } else {
        document.querySelector('#search-box').setAttribute('placeholder', 'Search post');
    }
}

//handle category
function handleCategory() {
    var categories = document.querySelectorAll('.panel.panel-default .panel-heading .panel-title a');
    for (var i = 0; i < categories.length; i++) {
        categories[i].onclick = function (e) {
            var category = e.target.id;
            var url = "bloglist?category=" + category;
            if (category == -1) {
                url = "bloglist";
            }
            window.location.href = url;
        };
    }
}