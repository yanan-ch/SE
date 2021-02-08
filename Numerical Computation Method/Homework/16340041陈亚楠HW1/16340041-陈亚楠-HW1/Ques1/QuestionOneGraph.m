x = [10 50 100 200];
y1 = [0 0 0 0];
y2 = [0 0 0 0];
for i = 1:4
    A = rand(x(i), x(i));
    b = rand(x(i), 1);
    t0 = cputime;    
    % 高斯消元法
    r1 = Gauss(A, b);
    t1 = cputime - t0;
    % 列主元消去法
    r2 = Pivot(A, b);
    t2 = cputime - t0 - t1;
    y1(i) = t1;
    y2(i) = t2;
end

plot(x, y1, x, y2)
xlabel('x: n')
ylabel('y: time')
title('Figure of Question one','FontSize',12)
legend('Gauss','Pivot')