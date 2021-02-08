$(document).ready( function() {
    styleHandle();
    $("#signup_username").on("blur", usernameJudge);
    $("#signup_id").on("blur", idJudge);
    $("#signup_tele").on("blur", teleJudge);
    $("#signup_email").on("blur", emailJudge);
    $("#signup_submit").click(checkAll);
    $("#refresh").click(reset);
});

function usernameJudge() {
    var content = $("#signup_username").val();
    var error = $("#nameError");
    if(username.length < 6 || username.length > 18) {
       error.html("Username should be 6-18 characters");
       return false;
    }
    var regex = /^[a-z]{1}[0-9_a-z]{2,11}$/;
    if(!regex.test(content)) {
        error.html("6-18 letters,numbers,underlines, begin with letters");
        return false;
    }
    return true;
}

function idJudge() {
    var content = $("#signup_id").val();
    var error = $("#idError");
    var regex = /[1-9]\d{7}/;
    if(!regex.test(content)) {
        error.html("8 numbers,can not begin with 0");
        return false;
    }
    return true;
}

function teleJudge() {
    var content = $("#signup_tele").val();
    var error = $("#teleError");
    var regex = /[1-9]\d{10}/;
    if(!regex.test(content)) {
        error.html("11 numbers,can not begin with 0");
        return false;
    }
    return true;
}

function emailJudge() {
    var content = $("#signup_email").val();
    var error = $("#emailError");
    var regex = /^([\w-_]+(?:\.[\w-_]+)*)@((?:[a-z0-9]+(?:-[a-zA-Z0-9]+)*)+\.[a-z]{2,6})$/;
    if(!regex.test(content)) {
        error.html("an illegal mail format");
        return false;
    }
    return true;
}

function checkAll() {
    if(usernameJudge() && idJudge() && teleJudge() && emailJudge()) {
        return true;
    } else {
        return false;
    }
}

function reset() {
    $("#refresh").click(function() {
        $(".item_input").html("");
        $(".error").html("");
        return true;
    })
}
function styleHandle() {
    $(".item_input").on("focus blur", function(e) {
        $(this).parents(".items").toggleClass("focused", (e.type === "focus" || this.value.length > 0));
    }).trigger("blur");

    $("#moveleft").click(function() {
        $("#textbox").animate({
            "marginLeft": "0" //moves left
        });
        $(".container").animate({
            "marginLeft": "100%" //moves right
        });
    });

    $("#moveright").click(function() {
        $("#textbox").animate({
            "marginLeft": "50%" //moves right
        });
        $(".container").animate({
            "marginLeft": "0" //moves right
        });
    })
}