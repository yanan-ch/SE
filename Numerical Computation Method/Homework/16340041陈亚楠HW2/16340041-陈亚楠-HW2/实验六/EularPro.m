function [x, y] = EularPro(init, left, right, h)

x = left:h:right;

y = zeros(size(x));
y(1) = init;

for i=1:length(x)-1
    y(i+1) = y(i) + h*(y(i) - 2*x(i)/y(i));
    yp = y(i+1);
    y(i+1) = y(i)+h/2*( (y(i)-2*x(i)/y(i)) + (yp-2*x(i+1)/yp) );
    yc = y(i+1);
    y(i+1) = (yp+yc)/2;    
end

end

