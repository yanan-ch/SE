function ans = Linear()

format long;

x = [.34, .36];
y = sin(x);
x0 = .35;

ans = (x(2)-x0)/(x(2)-x(1))*y(1) + (x0-x(1))/(x(2)-x(1))*y(2)
ans = [ans; sin(.35)];

end