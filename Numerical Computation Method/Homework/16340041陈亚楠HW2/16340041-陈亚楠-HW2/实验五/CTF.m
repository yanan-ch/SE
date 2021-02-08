function ans = CTF(left, right, steps)

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
sum = (lv+rv)/2;
for i=1 : steps-1
    sum = sum + f(left+i*h);
end
ans = sum * h;

vpa(ans, 6);

end

