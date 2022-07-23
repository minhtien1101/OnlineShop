/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.getElementById('getval').addEventListener('change', readURL, true);
function readURL() {
    var file = document.getElementById("getval").files[0];
    var reader = new FileReader();
    reader.onloadend = function () {
        document.getElementById('clock').style.backgroundImage = "url(" + reader.result + ")";
    }
    if (file) {
        reader.readAsDataURL(file);
    } else {
    }
}

$('#btn-id').click(function () {
    var status = $('#h-status-save').val();
    if (status == 'true') {
        $('#h-status-save').val('true');
        $('#ct').html('If the status is changed to <b>HIDE</b>, the Slider will not appear on the homepage.');
    } else {
        $('#h-status-save').val('false');
        $('#ct').html('If the status is changed to <b>SHOW</b>, the Slider will be displayed on the homepage.');
    }
    $('#confirm-show-slider').modal({
        backdrop: 'static',
        keyboard: true,
        show: true
    }

    );
    return false;
});

$('.btn-change').click(function () {
    var status = $('#h-status-save').val();
    
    if (status == 'true') {
        $('#h-status-save').val('false');
        $('#btn-id').prop('checked', false);
    } else {
        $('#h-status-save').val('true');
        $('#btn-id').prop('checked', true);
    }
    $('#confirm-show-slider').modal('toggle');


});

$('#btn-update').click(function (){
    $('#confirm-update-slider').modal({
        backdrop: 'static',
        keyboard: true,
        show: true
    });
});

$('.btn-update-slider').click(function (){
    $('#confirm-update-slider').modal('toggle');
    $('#form-add-update').submit();
});







