var imgArr = ["../img/panda.jpg", "../img/Iverson.jpg", "../img/Kobe.jpg", "../img/McGrady.jpg"];
var count = 0;
var mapURL = imgArr[0];
var puzzleArr = new Array(15);
var sign = false;
const puzzlePos = [
    ['0', '0'], ['25%', '0'], ['50%', '0'], ['75%', '0'],
    ['0', '25%'], ['25%', '25%'], ['50%', '25%'], ['75%', '25%'],
    ['0', '50%'], ['25%', '50%'], ['50%', '50%'], ['75%', '50%'],
    ['0', '75%'], ['25%', '75%'], ['50%', '75%'], ['75%', '75%']
];

window.onload = function() {
    document.getElementById("next").addEventListener("click", function() {
        mapURL = imgArr[(++count) % imgArr.length];
        init();
    });
    init();
    document.getElementById('play').addEventListener("click", reset);
    document.getElementById('refresh').addEventListener("click", init);
}

function init() {
    for (var i = 0; i < 15; ++i) {
        puzzleArr[i] = false;
    }
    puzzleArr[15] = true;

    var map = document.getElementById("puzzle");
    while (map.firstChild) {
        map.removeChild(map.firstChild);
    }
    for (var i = 0; i < 16; ++i) {
        var puzzle = document.createElement("li");
        puzzle.setAttribute("id", ("piece" + i));
        puzzle.setAttribute("pos", i);
        if (i < 15) {
            puzzle.style.backgroundImage = "url(" + mapURL + ")";
        }
        map.appendChild(puzzle);
    }

    var puzzle = document.getElementsByTagName("li");
    for (var i = 0; i < 16; ++i) {
        puzzle[i].onclick = function(i) {

            function canGo(newx, newy) {
                if (newx < 0 || newx > 3 || newy < 0 || newy > 3) {
                    return false;
                } else {
                    return puzzleArr[4 * newx + newy];
                }
            }

            function move(nextx, nexty, old_pos, clickId) {
                var new_pos = 4 * nextx + nexty;
                puzzleArr[old_pos] = true;
                puzzleArr[new_pos] = false;

                var p = document.getElementsByTagName("li");
                for (var i = 0; i < 16; ++i) {
                    if (p[i].getAttribute("pos") == new_pos) {
                        p[i].setAttribute("pos", old_pos);
                        p[i].style.left = puzzlePos[old_pos][0];
                        p[i].style.top = puzzlePos[old_pos][1];
                        break;
                    }
                }

                document.getElementById(clickId).style.left = puzzlePos[new_pos][0];
                document.getElementById(clickId).style.top = puzzlePos[new_pos][1];
                document.getElementById(clickId).setAttribute("pos", new_pos);
            }

            return function() {

                if (sign == false) {
                    alert("Please click the Play Button");
                } else {

                    var clickId = puzzle[i].id;
                    var old_pos = parseInt(puzzle[i].getAttribute('pos'));
                    var x = parseInt(old_pos / 4);
                    var y = old_pos % 4;

                    if (canGo(x + 1, y)) {
                        // go down
                        move(x + 1, y, old_pos, clickId);
                    }
                    if (canGo(x - 1, y)) {
                        // go up
                        move(x - 1, y, old_pos, clickId);
                    }
                    if (canGo(x, y - 1)) {
                        // go left
                        move(x, y - 1, old_pos, clickId);
                    }
                    if (canGo(x, y + 1)) {
                        // go right
                        move(x, y + 1, old_pos, clickId);
                    }
                    isWin();
                }
            }
        }(i);
    }

}

function reset() {
    sign = true;
    var puzzles = document.getElementsByTagName("li");
    for (var k = 0; k < 30; ++k) {
        var pos1 = parseInt(Math.random() * 15);
        var pos2 = parseInt(Math.random() * 15);
        if (pos1 != pos2) {
            var dom1, dom2;
            for (var i = 0; i < 16; ++i) {
                if (puzzles[i].getAttribute("pos") == pos1) {
                    dom1 = puzzles[i];
                    var temp = puzzleArr[pos1];
                    puzzleArr[pos1] = puzzleArr[pos2];
                    puzzleArr[pos2] = temp;
                }
                if (puzzles[i].getAttribute("pos") == pos2) {
                    dom2 = puzzles[i];
                }
            }

            dom1.setAttribute('pos', pos2);
            dom2.setAttribute('pos', pos1);
        }
    }


    for (var i = 0; i < 16; ++i) {
        var p = parseInt(puzzles[i].getAttribute('pos'));
        puzzles[i].style.left = puzzlePos[p][0];
        puzzles[i].style.top = puzzlePos[p][1];
    }
}

function isWin() {
    if (sign) {
        var p = document.getElementsByTagName("li");
        var iswin = true;
        for (var i = 0; i < 16; ++i) {
            if (p[i].getAttribute("pos") != i) {
                iswin = false;
                break;
            }
        }
        if (iswin) {
            alert("You Win!");
            sign = false;
        }
    }
}