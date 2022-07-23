$("#search-box").on('keyup', function (e) {
    var value = $('#search-box').val();
    console.log(value);
    if (e.key === 'Enter' || e.keyCode === 13) {
        window.location.href = "productlist?searchBy="+value;
    }
});


