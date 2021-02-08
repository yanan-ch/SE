% 雅可比迭代法求解线性方程组；
% A为方程组的系数矩阵；
% b为方程组的右端项；
% delta为精度要求，默认1e-5；
% max为最大迭代次数，默认100；
% x为方程组的解；
% k为迭代次数；
% flag = 'failure'表示失败,flag = 'success'表示成功
function [x, k, flag] = Jacobi(A, b, delta, max)
    n = length(A);
    k = 0;
    x = zeros(n, 1);
    y = zeros(n, 1);
    flag = 'success';
    while 1
        for i = 1:n
            y(i) = b(i);
            for j = 1:n
                if j ~= i
                    y(i) = y(i) - A(i, j) * x(j);
                end
            end
            if abs(A(i, i)) < 1e-10 | k == max
                flag = 'failure';
                return;
            end
            y(i) = y(i) / A(i, i);
        end
        if norm(y - x, inf) < delta
            break;
        end
        x = y;
        k = k + 1;
    end
end