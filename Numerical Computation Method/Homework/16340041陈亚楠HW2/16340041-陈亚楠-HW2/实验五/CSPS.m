function ans = CSPS(left, right, steps)

syms x;
f(x) = sin(x)/x;

lv = sin(left)/left;
if isnan(lv)
    lv = limit(f(x),x,left,'right');
end
rv = sin(right)/right;
if isnan(rv)
    rv = limit(f(x),x,right,'left');
end

h = (right-left)/steps;
sum = lv+rv+4*f(left+h/2);
for i=1 : steps-1
    sum = sum + 4*f(left+i*h+h/2) + 2*f(left+i*h);
end
ans = sum * h / 6;

vpa(ans,6);

end

