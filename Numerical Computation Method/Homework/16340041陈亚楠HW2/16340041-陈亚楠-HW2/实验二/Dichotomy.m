function [ans, iter] = Dichotomy(a, left, right, eps)

MAX = 100;

format long;

if nargin == 0
    a = 115;
    left = 10.0;
    right = 11.0;
    eps = 10e-6;
elseif nargin == 1
    left = floor(sqrt(a));
    right = ceil(sqrt(a));
    eps = 10e-6;
elseif nargin == 3
    eps = 10e-6;
end

iter = 1;
ans = [];

while iter < MAX
    mid = (left + right)/2;
    iter = iter+1;
    ans = [ans, mid];
    val = mid*mid - a;
    if abs(val) < eps
        ans = [ans, mid];
        return;
    en
    if val > 0
        right = mid;
    elseif val < 0
        left = mid;
    end
end

end