/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
$(document).ready(function () {
    $(".form-select").change(function () {
        window.location.href = "editRole?id=" + $(".form-select").val();
    });

    $('#submitUpdateRole').on('click', function (e) {
        if ($('#form-updaterole').valid())
        {
            $('#myModal').modal('show');
        }
        e.preventDefault();
    });

    $('#btnSubmitAddRole').on('click', function (e) {
        if ($('#frmAddRole').valid())
        {
            $('#myModal').modal('show');
        }
        e.preventDefault();
    });

    $('#btnConfirmUpdateRole').on('click', function () {
        $("input[id*='adminFeatures']:checkbox").each(function () {
            this.disabled = "";
        });
        $('#form-updaterole').submit();
    });

    $('#btnConfirmAddRole').on('click', function (e) {
        $('#frmAddRole').submit();

    });

    $('#btnSelectAllAdmin').on('click', function () {
        var checked = $(this).prop('checked');
        $("input[id*='adminFeatures']:checkbox").each(function () {
            this.checked = checked;
        });
    });

    $('#btnSelectAllMarketing').on('click', function () {
        var checked = $(this).prop('checked');
        $("input[id*='marketingFeatures']:checkbox").each(function () {
            this.checked = checked;
        });
    });

    $('#btnSelectAllSale').on('click', function () {
        var checked = $(this).prop('checked');
        $("input[id*='saleFeatures']:checkbox").each(function () {
            this.checked = checked;
        });
    });

    $('#frmAddRole').validate({
        rules: {
            "roleName": {
                required: true
            },
            "roleID": {
                required: true
            }
        },
        messages: {
            "roleName": {
                required: "Role name must not empty!"
            },
            "roleID": {
                required: "You must choose at least 1 option"
            }
        },
        highlight: function (label) {
            $(label).closest('.error-msg').addClass('error');
        },
        invalidHandler: function (event, validator) {
            window.scrollTo({top: 0, behavior: 'smooth'}); //scroll to top
            toastr.error("Validation error! Please check again before submit");
        },
        errorElement: 'div',
        errorLabelContainer: '.error-msg'
    });

    $('#form-updaterole').validate({
        rules: {
            "roleId": {
                required: true
            },
            "fid": {
                required: true
            }
        },
        messages: {
            "roleId": {
                required: "You must select a role!"
            },
            "fid": {
                required: "You must choose at least 1 option"
            }
        },
        highlight: function (label) {
            $(label).closest('.error-msg').addClass('error');
        },
        invalidHandler: function (event, validator) {
            window.scrollTo({top: 0, behavior: 'smooth'}); //scroll to top
            toastr.error("Validation error! Please check again before submit");
        },
        errorElement: 'div',
        errorLabelContainer: '.error-msg'
    });
});


