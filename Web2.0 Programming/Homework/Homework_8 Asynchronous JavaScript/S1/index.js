$(document).ready(function() {	
	$("#button").hover(initMenu, clearMenu);
});

function init() {
	$(".btn").removeClass("disable").attr("sign", "enable");
	$("#info").addClass("disable").attr("sign", "disable");
	$(".unread").html(" ").hide();
	$("#info").html(" ");
}

function initMenu() {
	init();
	$(".btn").click(btnClickListener);
	$("#info").click(infoClickListener);
}

function btnClickListener() {
	if($(this).find("span").html() == " " && isClicked() == true) {
		$(this).find("span").show().html("...");
		disableOtherBtn();
		sendRequest($(this));
		$(this).attr("sign", "disable").addClass("disable");
		enableOtherBtn($(this));
	}
}

function isClicked() {
	$(".btn").each(function() {
		if($(this).find("span").html() == "...") {
			return false;
		}
	});
	return true;
}

function disableOtherBtn() {
	$(".btn").each(function() {
		if($(this).find("span").html() == " ") {
			$(this).addClass("disable");
		}
	});
}

function sendRequest(aim) {
	$.get("/", function(random) {
		aim.find("span").html(random);
	});
}

function enableOtherBtn(except) {
	$(".btn").each(function() {
		if($(this).attr("sign") == "enable") {
			$(this).removeClass("disable");
		}
	});
}

function infoClickListener() {
	if(allBtnsDisable() == true) {
		$("#info").attr("sign", "enable");	
		$("#info").removeClass("disable");
		calculate();
	}
}

function allBtnsDisable() {
	$(".btn").each(function() {
		if($(this).find("span").html() == " ") {
			return false;
		}
	});
	return true;
}

function calculate() {
	var sum = 0;
	$(".unread").each(function() {
		sum += parseInt($(this).html(), 10);
	})
	$("#info").html(sum);
}

function clearMenu() {
	init();
}