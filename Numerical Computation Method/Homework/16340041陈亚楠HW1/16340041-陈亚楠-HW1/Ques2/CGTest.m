% 共轭梯度法的验证程序
A = [3 1; 1 2];
b = [5 5]';
delta = 1e-5;
x0 = [0 0]';
max = 100;
[x, k, flag] = CG(A, b, x0, delta, max)