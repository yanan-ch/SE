% 雅可比迭代法的验证程序
A = [4 1 -1; 1 -5 -1; 2 -1 -6];
b = [13 -8 -2]';
delta = 1e-5;
max = 100;
[x, k, flag] = Jacobi(A, b, delta, max)