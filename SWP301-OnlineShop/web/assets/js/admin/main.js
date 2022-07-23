/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


setTimeout(function () {
    const element = document.getElementById('showAlter');
    element.remove();
}, 3000);


setTimeout(function () {
    const element = document.getElementById('showAlterSuccess');
    element.remove();
}, 3000);
setTimeout(function () {
    const element = document.getElementById('showAlterFail');
    element.remove();
}, 3000);

$(document).ready(function () {
    $(function () {
        $('#txtAreaReason').hide();

        var start = moment();
        var end = moment();
        const params = new Proxy(new URLSearchParams(window.location.search), {
            get: (searchParams, prop) => searchParams.get(prop),
        });
        var startTime = params.startTime;
        var endTime = params.endTime;
        if (startTime === null || endTime === null)
        {
            $('#startTime').prop('value', start.format('YYYY-MM-DD'));
            $('#endTime').prop('value', end.format('YYYY-MM-DD'));
            $('#reportrange span').html(start.format('YYYY-MM-DD') + ' -> ' + end.format('YYYY-MM-DD'));
        } else
        {
            $('#startTime').prop('value', startTime);
            $('#endTime').prop('value', endTime);
            $('#reportrange span').html(startTime + ' -> ' + endTime);
        }
        $('#reportrange').daterangepicker({
            startDate: start,
            endDate: end,
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        });
    });

    $('#reportrange').on('apply.daterangepicker', function (ev, picker) {
        const startTime = picker.startDate.format('YYYY-MM-DD');
        const endTime = picker.endDate.format('YYYY-MM-DD');
        $('#startTime').prop('value', startTime);
        $('#endTime').prop('value', endTime);
        $('#reportrange span').html(startTime + ' -> ' + endTime);
    });

    $('.confirm-process').on('click', function () {
        var orderid = $(this).data("orderid");
        $("#myModal").data('orderid', orderid).modal('show');
    });

    $('#btnConfirmProcessOrder').on('click', function () {
        var orderid = $('#myModal').data("orderid");
        var url = '/sale/order/updatestatus';
        //set status = 2 mean processing
        $.post(url, {orderid: orderid, status: 2}, function (response) {
            $('#row-orderid' + orderid + ' td#tblStatus').html("<span class='label label-info'>Processing</span>");
            $('#row-orderid' + orderid + ' .confirm-process').hide();
            var obj = JSON.parse(response);
            toastr.success(obj.msg, 'Update status');
        })
                .fail(function (e) {
                    var obj = JSON.parse(e.responseText);
                    toastr.error(e.msg, 'Update status')
                });
    });

    $('#statusorder').on("change", function () {
        var selected = $(this).val();
        if (selected == 0)
        {
            $('#txtAreaReason').show();
        } else
        {
            $('#txtAreaReason').hide();
        }
    });

    $('#btnUpdateOrder').on('click', function () {
        var orderid = $(this).data("orderid");
        $("#myModal").data('orderid', orderid).modal('show');
    });

    $('#btnConfirmUpdateStatus').on('click', function (e) {
        var orderid = $('#myModal').data('orderid');
        var status = $('#statusorder :selected').val();
        var cancelledReason = null;
        var emailUserBuy = $('#emailUserBuy').val();

        var url = '/sale/order/updatestatus';
        var url2 = '/send/completedOrder';
        if (status == 0)
        {
            cancelledReason = $('#txtcancelReason').val();
        }
        if ($('#frmUpdateOrderStatus').valid())
        {
            $.post(url, {orderid: orderid, status: status, cancelledReason: cancelledReason, emailUserBuy: emailUserBuy}, function (response) {
                var obj = JSON.parse(response);
                switch (status) {
                    case "0":
                        $('#orderstatus').html('<span class="label label-default">Cancelled</span>');
                        $('#orderstatus').next('p').remove();
                        $("<p><label>Reason: </label>\n\
                            <span>" + cancelledReason + "</span></p>").insertAfter("#orderstatus");
                        break;
                    case "1":
                        $('#orderstatus').html('<span class="label label-warning">Waiting for process</span>');
                        $('#orderstatus').next('p').remove();
                        break;
                    case "2":
                        $('#orderstatus').html('<span class="label label-info">Processing</span>');
                        $('#orderstatus').next('p').remove();
                        break;
                    case "3":
                        $('#orderstatus').html('<span class="label label-primary">Shipping</span>');
                        $('#orderstatus').next('p').remove();
                        break;
                    case "4":
                        $('#orderstatus').html('<span class="label label-success">Completed</span>');
                        $('#orderstatus').next('p').remove();
                        break;
                }
                $("#statusorder option[value='']").prop('selected', true);
                $('#txtAreaReason').hide();
                $('#myModal').modal('hide');
                toastr.success(obj.msg, "Notification");
            })
                    .fail(function () {

                    });
            $.post(url2, {orderid: orderid, status: status, cancelledReason: cancelledReason, emailUserBuy: emailUserBuy}, function () {
//            location.reload();
            })
                    .fail(function () {

                    });
        }
    });

    $('#btnEditSaleNote').on('click', function () {
        $('#txtSaleNote').prop("disabled", false);
        $(this).addClass("display-none"); //hide Edit button
        $('#btnCancelSaleNoteSave').removeClass('display-none'); //show cancel button
        $('#btnSaveSaleNote').removeClass('display-none'); // show save button
    });

    $('#btnCancelSaleNoteSave').on('click', function () {
        $('#txtSaleNote').prop("disabled", true); //disable sale note textarea
        $(this).addClass("display-none"); //hide cancel button
        $('#btnSaveSaleNote').addClass('display-none'); //hide save button
        $('#btnEditSaleNote').removeClass("display-none"); //show edit button
    });

    $('#btnSaveSaleNote').on('click', function () {
        var orderid = $(this).data('orderid');
        var note = $('#txtSaleNote').val();
        var url = '/sale/order/updatenote';
        $.post(url, {orderid: orderid, note: note}, function (response) {
            var obj = JSON.parse(response);
            //location.reload();
            $('#txtSaleNote').val(note);
            $('#txtSaleNote').prop("disabled", true); //disable sale note textarea
            $('#btnCancelSaleNoteSave').addClass("display-none"); //hide cancel button
            $('#btnSaveSaleNote').addClass('display-none'); //hide save button
            $('#btnEditSaleNote').removeClass("display-none"); //show edit button
            toastr.success(obj.msg, 'Notification');
        })
                .fail(function (e) {
                    var obj = JSON.parse(e.responseText);
                    toastr.error(obj.msg, 'Notification');
                });
    });

    $('#btnShowThumbnail').on('click', function () {
        var thumbnail = $(this).data("thumbnail");
        $('.imagepreview').attr('src', thumbnail);
        $("#myModal").modal('show');
    });

    $('#btnConfirmUpdateFBStatus').on('click', function () {
        var fid = $('#btnUpdateFBStatus').data('passing').fid;
        var status = $('#btnUpdateFBStatus').data('passing').status;
        var url = '/marketing/feedback/updatestatus';

        $.post(url, {fid: fid, status: status}, function (response) {
            var obj = JSON.parse(response);
            toastr.success(obj.msg, "Notification");
            if (status == 1)
            {
                $('#lblStatus').removeClass("label-danger").addClass("label-success").text("Active");
                $('#btnUpdateFBStatus').removeClass("btn-success").addClass("btn-danger").text("Change to De-active");
            } else
            {
                $('#lblStatus').removeClass("label-success").addClass("label-danger").text("De-active");
                $('#btnUpdateFBStatus').removeClass("btn-danger").addClass("btn-success").text("Change to Active");
            }
        })
                .fail(function (e) {
                    var obj = JSON.parse(e.responseText);
                    toastr.error(obj.msg, 'Notification');
                });
    });

    $('#btnConfirmUpdateSale').on('click', function () {
        var sid = $('#salename').val();
        var oid = $('#btnChangeSale').data('orderid');
        var url = '/sale/order/updateseller';
        if ($('#frmUpdateSale').valid())
        {
            $.post(url, {sid: sid, oid: oid}, function (response) {
                var obj = JSON.parse(response);
                toastr.success(obj.msg, "Notification");
                $('#assignedSale').text($('#salename option:selected').text());
                $("#salename option[value='']").prop('selected', true);
                $('#myModal2').modal('hide');
            })
                    .fail(function (e) {
                        var obj = JSON.parse(e.responseText);
                        toastr.error(obj.msg, 'Notification');
                    });
        }
    });

    $('#frmUpdateOrderStatus').validate({
        rules: {
            "cancelReason": {
                required: true
            }
        },
        messages: {
            "cancelReason": {
                required: "You must enter the reason"
            }
        }
    });
});