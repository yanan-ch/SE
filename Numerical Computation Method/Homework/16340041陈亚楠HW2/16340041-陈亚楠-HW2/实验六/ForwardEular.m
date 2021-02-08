function [x, y] = ForwardEular(init, left, right, h)

x = left:h:right;

y = zeros(size(x));
y(1) = init;

for i=1:length(x)-1
    y(i+1) = y(i) + h*(y(i) - 2*x(i)/y(i));
end

end

