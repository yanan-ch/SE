% 高斯塞德尔迭代法的验证程序
A = [10 -2 -2; -2 10 -1; -1 -2 3];
b = [1 0.5 1]';
delta = 1e-5;
max = 100;
[x, k, flag] = GaussSeidel(A, b, delta, max)