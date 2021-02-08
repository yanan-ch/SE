function [x, y] = BackwardEular(init, left, right, h)

MAX = 100;
eps = 10e-8;

x = left:h:right;

y = zeros(size(x));
y(1) = init;

for i=1:length(x)-1
    tmp = y(i) + h*(y(i) - 2*x(i)/y(i));
    prev = tmp;
    for j=1:MAX
        y(i+1) = y(i) + h*(tmp - 2*x(i+1)/tmp);
        if abs(y(i+1) - prev) < eps
            disp(j);    
            break;
        end
        prev = y(i+1);
    end
end

end

