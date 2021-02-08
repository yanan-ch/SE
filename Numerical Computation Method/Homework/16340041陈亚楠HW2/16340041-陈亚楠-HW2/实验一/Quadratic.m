function ans = Quadratic()

format long;

x = [.32, .34, .36];
y = sin(x);
x0 = .35;

ans = (x0-x(2)) * (x0-x(3)) / (x(1)-x(2)) / (x(1)-x(3)) * y(1);
ans = ans + (x0-x(1)) * (x0-x(3)) / (x(2)-x(1)) / (x(2)-x(3)) * y(2);
ans = ans + (x0-x(1)) * (x0-x(2)) / (x(3)-x(1)) / (x(3)-x(2)) * y(3);
ans = [ans; sin(.35)];

end