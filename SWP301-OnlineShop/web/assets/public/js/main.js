/*price range*/

// $('#sl2').slider();
//
//	var RGBChange = function() {
//	  $('#RGB').css('background', 'rgb('+r.getValue()+','+g.getValue()+','+b.getValue()+')')
//	};	

/*scroll to top*/

$(document).ready(function () {
    $('#alertSuccess').hide();
    $('#alertDanger').hide();

    $.validator.addMethod("validatePassword", function (value, element) {
        return this.optional(element) || /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$/i.test(value);
    }, "Password from 8 - 16 characters including uppercase, lowercase and at least one number");

    $(function () {
        $.scrollUp({
            scrollName: 'scrollUp', // Element ID
            scrollDistance: 300, // Distance from top/bottom before showing element (px)
            scrollFrom: 'top', // 'top' or 'bottom'
            scrollSpeed: 300, // Speed back to top (ms)
            easingType: 'linear', // Scroll to top easing (see http://easings.net/)
            animation: 'fade', // Fade, slide, none
            animationSpeed: 200, // Animation in speed (ms)
            scrollTrigger: false, // Set a custom triggering element. Can be an HTML string or jQuery object
            //scrollTarget: false, // Set a custom target element for scrolling to the top
            scrollText: '<i class="fa fa-angle-up"></i>', // Text for element, can contain HTML
            scrollTitle: false, // Set a custom <a> title if required.
            scrollImg: false, // Set true to use image
            activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
            zIndex: 2147483647 // Z-Index for the overlay
        });
    });
    $("#saveNewPassword").click(function () {
        var isFormValid = $("#changePassForm").valid();

        if (isFormValid)
        {
            //get value from inputs
            var oldPass = $('#oldPassword').val();
            var newPass = $('#newPassword').val();
            var url = '/user/changePassword';
            $.post(url,
                    {
                        oldPassword: oldPass,
                        newPassword: newPass
                    }, function (res) {
                var data = jQuery.parseJSON(res);
                $('#alertDanger').hide();
                $('#alertSuccess').show();
                $('#alertSuccess').html(data.Msg);

                //reset form
                $('#oldPassword').val("");
                $('#newPassword').val("");
                $('#reEnterNewPassword').val("");
            })
                    .fail(function (res)
                    {
                        var data = jQuery.parseJSON(res.responseText);
                        $('#alertSuccess').hide();
                        $('#alertDanger').show();
                        $('#alertDanger').html(data.Msg);
                    });
        }
    });

    $("#changePassForm").validate({
        rules: {

            "oldPassword": {
                required: true
            },
            "newPassword": {
                required: true,
                validatePassword: true,
            },
            "reEnterPass": {
                equalTo: "#newPassword"
            }
        }
    });

    $(function () {
        var start = moment();
        var end = moment();
        function cb(start, end) {
            const params = new Proxy(new URLSearchParams(window.location.search), {
                get: (searchParams, prop) => searchParams.get(prop),
            });
            var startTime = params.startTime;
            var endTime = params.endTime;
            if(startTime == null || endTime == null)
            {
                $('#reportrange span').html(start.format('YYYY-MM-DD') + ' -> ' + end.format('YYYY-MM-DD'));
            }
            else
            {
                $('#reportrange span').html(startTime + ' -> ' + endTime);
            }

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
        }, cb);
        cb(start, end);
    });

    $('#reportrange').on('apply.daterangepicker', function (ev, picker) {
        window.location.href = window.location.pathname + "?" + $.param({'startTime': picker.startDate.format('YYYY-MM-DD'), 'endTime': picker.endDate.format('YYYY-MM-DD')})
    });
});
