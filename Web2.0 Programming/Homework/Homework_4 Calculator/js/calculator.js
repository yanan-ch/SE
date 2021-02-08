var display = document.getElementById('screen');
var result = 0;

var nodelist = document.getElementsByTagName('button');
for (var i = 0; i < nodelist.length; i++) {
	nodelist[i].onclick = click_show;
}

function click_show(e) {
	var target = e.target.textContent;
	switch(target) {
		case '0': case '1': case '2': case '3': case '4':
		case '5': case '6': case '7': case '8': case '9':
		case '.': case '+': case '-': case '*': case '/':
		case '(': case ')':
			display.textContent += target;
			break;
		case '←':
			erase();
			break;
		case 'CE':
			clear();
			break;
		case '=':
			equal();
			break;
		default:
			break;
	}
}

function erase() {
	var str = "";
	str = display.textContent;
	display.textContent = str.substring(0,str.length - 1);
}

function clear() {
	display.textContent = "";
}

function equal() {
	calculate();
}

function calculate() {
	var string = "";
	string = eval(display.textContent);
	display.textContent = string;
}