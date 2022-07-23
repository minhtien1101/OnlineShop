var searchContent = document.querySelector('#search-content').value;
var searchCategory = document.querySelector('#search-category').value;
var searchSubCategory = document.querySelector('#search-subcategory').value;
var pageIndex = document.querySelector('#search-page-index').value;

var categories = document.querySelectorAll('.panel.panel-default .panel-heading .panel-title a');
console.log(categories);

var active_category = document.querySelector("#all-category");
active_category.addEventListener('click', () => window.location.href = "bloglist");

// search box
$("#search-box").on('keyup', function (e) {
    var search = document.querySelector('#search-box').value;
    if (e.key === 'Enter' || e.keyCode === 13) {
        var url = "bloglist?search=" + search;
        if (searchCategory != -1) {
            url += "&category=" + searchCategory;
        }
        if(searchSubCategory != -1) {
            url += "&subcategory=" + searchSubCategory;
        }
        window.location.href = url;
    }
});

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


$("#next-page").on('click', function () {
    var paging = document.querySelectorAll('.pagination li a');
    for (var i = 0; i < paging.length - 2; i++) {
        if (paging[i].className.includes("active")) {
            var indexNextPage = i + 1;
            var url = "bloglist?page=" + paging[indexNextPage].innerHTML;
            if (searchCategory) {
                if (searchCategory != -1) {
                    url += "&category=" + searchCategory;
                }
            }
            if (searchCategory) {
                if (searchSubCategory != -1) {
                    url += "&subcategory=" + searchSubCategory;
                }
            }
            if (searchContent) {
                url += "&search=" + searchContent;
            }

            window.location.href = url;
            break;
        }
    }
});

function setActivePaging(page) {
    var paging = document.querySelectorAll('.pagination li a');
    for (var i = 0; i < paging.length - 2; i++) {
        if (paging[i].className.includes("active")) {
            paging[i].classList.remove("active");
            paging[page - 1].classList.add("active");
            break;
        }
    }
}

function handlePaging() {
    var paging = document.querySelectorAll('.pagination li a');
    for (var i = 0; i < paging.length - 1; i++) {
        paging[i].onclick = function (e) {
            var url = "bloglist?page=" + e.target.innerHTML;
            if (searchCategory != -1) {
                url += "&category=" + searchCategory;
            }
            if (searchSubCategory != -1) {
                url += "&subcategory=" + searchSubCategory;
            }
            if (searchContent) {
                url += "&search=" + searchContent;
            }

            window.location.href = url;
        };
    }
}
function app(page, searchContent) {
    handlePaging();
    setActivePaging(page);
    handleAttributeBoxSearch(searchContent);
//    handleCategory();
}
app(pageIndex, searchContent);