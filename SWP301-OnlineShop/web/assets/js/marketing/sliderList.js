

$('.btn-id').click(function (e) {
    var sid = $(e.target).data('id');
    var status = $(e.target).data('status');
    if (status == true) {
        $('#ct').html('If the status is changed to <b>HIDE</b>, the Slider will not appear on the homepage.');
    } else {


        $('#ct').html('If the status is changed to <b>SHOW</b>, the Slider will be displayed on the homepage.');
    }
    $('#hSid').val(sid);
    $('#hStatus').val(status);
    $('#confirm-show-slider').modal({
        backdrop: 'static',
        keyboard: true,
        show: true
    }
    );
});

$('.btn-change').click(function () {
    var sid = $('#hSid').val();
    var status = $('#hStatus').val();
    $.ajax({
        url: "/marketing/sliderList",
        type: "post", //send it through get method
        data: {
            id: sid,
            status: status
        },
        success: function (response) {
            //Do Something
            $('#confirm-show-slider').modal('toggle');
            sessionStorage.setItem('save_order',true);
            window.location.reload();


        },
        error: function (xhr) {

        }
    });
});

$(function () {
    if (sessionStorage.getItem('save_order')) {
        toastr.success("Status change successful!");
        sessionStorage.removeItem('save_order');
    }
}
);

//$('.btn-change-edit').click(function () {
//    var sid = $('#hSid').val();
//    var status = $('#hStatus').val();
//    if (status == false) {
//        $('#lab-id').html("<button id='btn-status-"+ sid +"' data-id='"+ sid + "' data-status='"+ status + "' type='button' class='btn btn-success btn-id>Show</button>");
//                                    
//        $('#status-hidden-id').val(true);
//    }else {
//        $('#btn-status-' + sid).attr('class', 'btn btn-danger btn-id');
//        $('#status-hidden-id').val(false);
//    }
//});





