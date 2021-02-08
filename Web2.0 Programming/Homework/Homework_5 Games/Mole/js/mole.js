//draw moles
for(var i = 0; i < 60; i++) {
	var inputEle = document.createElement("input");
	inputEle.setAttribute("type", "radio");
	inputEle.setAttribute("name", "radio");
	inputEle.id = i;
	var mole = document.getElementById("moles").appendChild(inputEle);
	mole.setAttribute("class", "moleBefore");
}

var molesList = document.getElementsByTagName("input");
var mode = 1;//0 is start, 1 is stop
var random = 0;
var time_left = 30;
var time_var;
var score = 0;

window.onload = function() {
	init();
	document.getElementById("play").onclick = function() {
		switchMode()
		randomGenerate();
		molesList[random].checked = true;
	};
	for(var i = 0; i < 60; i++) {
		molesList[i].onclick = function() {
			if(mode == 1) {
				for(var j = 0; j < 60; j++) {
					molesList[j].checked = false;
				}
			} else {
				if(random == this.id) {
					score++;
					document.getElementById("score").innerHTML = score;
                    molesList[random].checked = false;
                    randomGenerate();
                    molesList[random].checked = true;
				} else {
					score--;
                	molesList[this.id].checked = false;
                	molesList[random].checked = true;
                	document.getElementById("score").innerHTML = score;
            	}
			}
		}
	}	
}

function init() {
	for(var i = 0; i < 60; i++) {
		molesList[i].checked = false;
	}
	mode = 1;
	time_left = 30;
	score = 0;
	document.getElementById("time").innerHTML = time_left;
	document.getElementById("ifOver").innerHTML = "Game Over";
	document.getElementById("score").innerHTML = score;
}

function switchMode() {
	if(mode == 1) {
        document.getElementById("ifOver").innerHTML = "Playing";
        mode = 0;
        timeCount();
    } else {
    	gameOver();
    	mode = 1;
    }
}

function timeCount() {
	document.getElementById("time").innerHTML = time_left;
	time_left -= 1;
	time_var = setTimeout(timeCount, 1000);
	if(time_left < -1) {
		gameOver();
	}
}

function randomGenerate() {
	random = Math.floor(Math.random() * 60);
}

function gameOver() {
	alert("Game Over.\nYour score is:" + score);
	clearTimeout(time_var);
	init();
}