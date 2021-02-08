function [ans, iter] = Secant(a, x0, x, eps)

MAX = 100;

format long;

if nargin == 0
    a = 115;
    x0 = 10.0;
    x = 11.0;
    eps = 10e-6;
elseif nargin == 1
    x0 = floor(sqrt(a));
    x = ceil(sqrt(a));
    eps = 10e-6;
elseif nargin == 2
    eps = 10e-6;
end

ans  =[];
iter = 1;
while iter < MAX
    iter = iter + 1;
    ans = [ans, x];
    tmp = x;
    x = x - (x*x-a)/(x+x0);
    x0 = tmp;
    if abs(x*x - a) < eps
        ans = [ans, x];
        return;
    end
end

end