var start = false;
var end = false;
var knock = false;

window.onload = function() {
	init();
	document.getElementById("myCanvas").onmouseout = function() {
		reset()
	};
	document.getElementById("myCanvas").onmousemove = function() {
		result(event);
	};
}

function reset() {
	init();
	start = false;
	end = false;
	knock = false;
}

function result(event) {
	var x = event.clientX - document.getElementById("myCanvas").offsetLeft;
	var y = event.clientY - document.getElementById("myCanvas").offsetTop;
	/*var coor = "(" + x + "," + y + ")";
	document.getElementById("hint").innerHTML = coor;*/

	if(isOnUp(x, y) === true) {
		if(start === true) {
			drawUpMaze("#FF0000");
			document.getElementById("hint").innerHTML = "You Lose for knocking the wall and restart!";
			knock = true;
			end = true;
		} else {
			document.getElementById("hint").innerHTML = "You haven't started the game!";
		}
	} else if(isOnDown(x, y) === true) {
		if(start === true) {
			drawDownMaze("#FF0000");
			document.getElementById("hint").innerHTML = "You Lose for knocking the wall and restart!";
			knock = true;
			end = true;
		} else {
			document.getElementById("hint").innerHTML = "You haven't started the game!";
		}
	} else {
		drawUpMaze("#EDEDED");
		drawDownMaze("#EDEDED");
		if(isOnStart(x, y) === true) {
			start = true;
			knock = false;
			end = false;
			document.getElementById("hint").innerHTML = "You start a new game!";
		} else if(isOnEnd(x, y) === true) {
			end = true;
			if(start === true && knock === false) {
				document.getElementById("hint").innerHTML = "You Win!";
			} else {
				document.getElementById("hint").innerHTML = "Don't cheat, you should start form the 'S'" 
																+ " and move to the 'E' inside the maze!";
			}
		} else {
			document.getElementById("hint").innerHTML = "";
		}
	}
}

function init() {
	drawUpMaze("#EDEDED");
	drawDownMaze("#EDEDED");
	drawStart();
	drawEnd();
}

function drawUpMaze(color) {
	var canvas = document.getElementById("myCanvas");
	var ctx = canvas.getContext("2d");
	ctx.beginPath();
	ctx.moveTo(0, 0);
	ctx.lineTo(550, 0);
	ctx.lineTo(550, 200);
	ctx.lineTo(420, 200);
	ctx.lineTo(420, 50);
	ctx.lineTo(130, 50);
	ctx.lineTo(130, 200);
	ctx.lineTo(0, 200);
	ctx.closePath();
	ctx.fillStyle = color;
	ctx.strokeStyle = "#000000";
	ctx.fill();
	ctx.stroke();
}

function drawDownMaze(color) {
	var canvas = document.getElementById("myCanvas");
	var ctx = canvas.getContext("2d");
	ctx.beginPath();
	ctx.moveTo(0, 250);
	ctx.lineTo(180, 250);
	ctx.lineTo(180, 100);
	ctx.lineTo(370, 100);
	ctx.lineTo(370, 250);
	ctx.lineTo(550, 250);
	ctx.lineTo(550, 300);
	ctx.lineTo(0, 300);
	ctx.closePath();
	ctx.fillStyle = color;
	ctx.strokeStyle = "#000000";
	ctx.fill();
	ctx.stroke();
}

function drawStart() {
	var canvas = document.getElementById("myCanvas");
	var ctx = canvas.getContext("2d");
	ctx.beginPath();
	ctx.fillStyle = "#82FF80";
	ctx.strokeStyle = "#000000";
	ctx.rect(0, 204, 42, 42);
	ctx.fill();
	ctx.stroke();
	ctx.closePath();
	ctx.fillStyle = "#000000";
	ctx.font = "40px Verdana bolder";
	ctx.fillText("S", 10, 241);
}

function drawEnd() {
	var canvas = document.getElementById("myCanvas");
	var ctx = canvas.getContext("2d");

	ctx.beginPath();
	ctx.fillStyle = "#8684FF";
	ctx.strokeStyle = "#000000";
	ctx.rect(508, 204, 42, 42);	
	ctx.fill();
	ctx.stroke();
	ctx.closePath();
	ctx.fillStyle = "#000000";
	ctx.font = "40px Verdana bolder";
	ctx.fillText("E", 518, 241);
}

function isOnStart(x, y) {
	if(x >= 0 && x <= 42 && y >= 204 && y <= 246) {
		return true;
	} else {
		return false;
	}
}

function isOnEnd(x, y) {
	if(x >= 508 && x <= 550 && y >= 204 && y <= 246) {
		return true;
	} else {
		return false;
	}
}

function isOnUp(x, y) {
	if( (x >= 0 && x <= 130 && y >= 0 && y <= 200) 
			|| (x >= 130 && x <= 420 && y >= 0 && y <= 50) 
			|| (x >= 420 && x <= 550 && y >= 0 && y <= 200) ) {
		return true;
	} else {
		return false;
	}
}

function isOnDown(x, y) {
	if( (x >= 0 && x <= 550 && y >= 250 && y <= 300) 
			|| (x >= 180 && x <= 370 && y >= 100 && y <= 250) ) {
		return true;
	} else {
		return false;
	}
}

function isInmaze() {
	if(x >= 0 && x <= 550 && y >= 0 && y <= 300) {
		return true;
	} else {
		return false;
	}
}