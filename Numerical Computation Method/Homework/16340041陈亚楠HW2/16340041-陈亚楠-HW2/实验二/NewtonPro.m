function [ans, iter] = NewtonPro(a, x, eps)

MAX = 100;

format long;

if nargin == 0
    a = 115;
    x = 10.0;
    eps = 10e-6;
elseif nargin == 1
    x = floor(sqrt(a));
    eps = 10e-6;
elseif nargin == 2
    eps = 10e-6;
end

ans  =[];
iter = 1;
c = 2*x;

while iter < MAX
    iter = iter + 1;
    ans = [ans, x];
    x = x - (x*x-a)/c;
    if abs(x*x - a) < eps
        ans = [ans, x];
        return;
    end
end

end