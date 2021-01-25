(function ($) {
    "use strict";

    // Options for Message
    //----------------------------------------------
    var options = {
        'btn-loading': '<i class="fa fa-spinner fa-pulse"></i>',
        'btn-success': '<i class="fa fa-check"></i>',
        'btn-error': '<i class="fa fa-remove"></i>',
        'msg-success': 'All Good! Redirecting...',
        'msg-error': 'Wrong login credentials!',
        'useAJAX': true,
    };

    // Login Form
    //----------------------------------------------
    // Validation
    $("#login-form").validate({
        rules: {
            lg_username: "required",
            lg_password: "required",
        },
        errorClass: "form-invalid"
    });

    // Form Submission
    $("#login-form").submit(function () {
        remove_loading($(this));

        var form = $(this);
        if (options['useAJAX'] == true) {
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/login_sys/user/signin",
                data: {
                    "username": $("#lg_username").val(),
                    "password": $("#lg_password").val(),
                    "verifyCode": $("#reg_captcha").val()
                },
                dataType: 'json',
                success: data=>{
                    var state = data["state"];
                    if (state){
                        setTimeout(function () {
                            form_success(form);
                        }, 1000);
                        // Store the username into the local storage
                        // localStorage.setItem("username",data["username"]);
                        // Store the username into the session storage
                        sessionStorage.setItem("username",data["username"]);

                        window.alert("Success! Press confirm to redirect to main page.");

                        window.location.replace("http://localhost:8080/login_sys/main.html");
                    }else{
                        window.alert(data["msg"]);
                        setTimeout(function () {
                            form_failed(form);
                        }, 1000);
                    }

                },
            })
        }
        return false;
    });

    // Register Form
    //----------------------------------------------
    // Validation
    $("#register-form").validate({
        rules: {
            reg_username: "required",
            reg_password: {
                required: true,
                minlength: 5
            },
            reg_password_confirm: {
                required: true,
                minlength: 5,
                equalTo: "#register-form [name=reg_password]"
            },
            reg_email: {
                required: true,
                email: true
            },
            reg_agree: "required",
        },
        errorClass: "form-invalid",
        errorPlacement: function (label, element) {
            if (element.attr("type") === "checkbox" || element.attr("type") === "radio") {
                element.parent().append(label); // this would append the label after all your checkboxes/labels (so the error-label will be the last element in <div class="controls"> )
            } else {
                label.insertAfter(element); // standard behaviour
            }
        }
    });


    // Form Submission
    $("#register-form").submit(function () {
        remove_loading($(this));

        var form = $(this);

        if (options['useAJAX'] == true) {
            // Dummy AJAX request (Replace this with your AJAX code)
            // If you don't want to use AJAX, remove this
            if (form.valid()) {
            //     console.log($("#reg_captcha").val());
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/login_sys/user/signup",
                    data: {
                        "username": $("#reg_username").val(),
                        "password": $("#reg_password").val(),
                        "gender": $("input:radio:checked").val(),
                        "email": $("#reg_email").val(),
                        "verifyCode": $("#reg_captcha").val()
                    },
                    dataType: 'json',
                    success: data=>{
                        var state = data["state"];
                        if (state){
                            setTimeout(function () {
                                form_success(form);
                            }, 1000);
                            window.alert("Success! Press confirm to redirect.");
                            window.location.replace("http://localhost:8080/login_sys/signin.html");
                        }else{
                            window.alert(data["msg"]);
                            setTimeout(function () {
                                form_failed(form);
                            }, 1000);
                        }

                    },
                });


            }

            // Cancel the normal submission.
            // prevent the page re-rendering
            return false;
        }
    });

    // Forgot Password Form
    //----------------------------------------------
    // Validation
    $("#forgot-password-form").validate({
        rules: {
            fp_email: "required",
        },
        errorClass: "form-invalid"
    });

    // Form Submission
    $("#forgot-password-form").submit(function () {
        remove_loading($(this));

        if (options['useAJAX'] == true) {
            // Dummy AJAX request (Replace this with your AJAX code)
            // If you don't want to use AJAX, remove this
            dummy_submit_form($(this));

            // Cancel the normal submission.
            // If you don't want to use AJAX, remove this
            return false;
        }
    });

    // Loading
    //----------------------------------------------
    function remove_loading($form) {
        $form.find('[type=submit]').removeClass('error success');
        $form.find('.login-form-main-message').removeClass('show error success').html('');
    }

    function form_loading($form) {
        $form.find('[type=submit]').addClass('clicked').html(options['btn-loading']);
    }

    function form_success($form) {
        $form.find('[type=submit]').addClass('success').html(options['btn-success']);
        $form.find('.login-form-main-message').addClass('show success').html(options['msg-success']);
    }

    function form_failed($form) {
        $form.find('[type=submit]').addClass('error').html(options['btn-error']);
        $form.find('.login-form-main-message').addClass('show error').html(options['msg-error']);
    }

    // Submit Form
    function submit_form($form, url) {
        if ($form.valid()) {
            // anime of loading
            form_loading($form);

            var form = $(this);
            $.ajax({
                type: "GET",
                url: url,
                data: form.serialize(), // serializes the form's elements.
                success: function (data) {
                    alert(data); // show response from the php script.
                }
            });

            setTimeout(function () {
                form_success($form);
            }, 2000);
        }
    }

})(jQuery);