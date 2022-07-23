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

        var start = moment().subtract(7, 'days');
        var end = moment();
        const params = new Proxy(new URLSearchParams(window.location.search), {
            get: (searchParams, prop) => searchParams.get(prop),
        });
        var startTime = params.reStartTime;
        var endTime = params.reEndTime;
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
        window.location.href = window.location.pathname + $.query.set('reStartTime', picker.startDate.format('YYYY-MM-DD')).set('reEndTime', picker.endDate.format('YYYY-MM-DD')).toString();
    });

    $(function () {

        var start = moment().subtract(7, 'days');
        var end = moment();
        const params = new Proxy(new URLSearchParams(window.location.search), {
            get: (searchParams, prop) => searchParams.get(prop),
        });
        var startTime = params.trendStartTime;
        var endTime = params.trendEndTime;
        if (startTime === null || endTime === null)
        {
            $('#startTime').prop('value', start.format('YYYY-MM-DD'));
            $('#endTime').prop('value', end.format('YYYY-MM-DD'));
            $('#reportrange2 span').html(start.format('YYYY-MM-DD') + ' -> ' + end.format('YYYY-MM-DD'));
        } else
        {
            $('#startTime').prop('value', startTime);
            $('#endTime').prop('value', endTime);
            $('#reportrange2 span').html(startTime + ' -> ' + endTime);
        }
        $('#reportrange2').daterangepicker({
            startDate: start,
            endDate: end,
            minDate: moment().subtract(30, 'days'),
            maxDate: moment(),    
            ranges: {
                'Last 7 Days': [moment().subtract(7, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        });
    });

    $('#reportrange2').on('apply.daterangepicker', function (ev, picker) {
        window.location.href = window.location.pathname + $.query.set('trendStartTime', picker.startDate.format('YYYY-MM-DD')).set('trendEndTime', picker.endDate.format('YYYY-MM-DD')).toString();
    });
});