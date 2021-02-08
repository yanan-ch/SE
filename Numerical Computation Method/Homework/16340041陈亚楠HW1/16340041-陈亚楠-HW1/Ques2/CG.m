% 共轭梯度法求解线性方程组；
% A为方程组的系数矩阵；
% b为方程组的右端项；
% delta为精度要求，默认1e-5；
% max为最大迭代次数，默认100；
% x为方程组的解；
% k为迭代次数；
% flag = 'failure'表示失败,flag = 'success'表示成功
function [x, k, flag] = CG(A, b, x0, delta, max)
    n = length(A);
    k = 0;
    x = zeros(n, 1);
    flag = 'success';
    r0 = b - A * x0;
    p0 = r0;
    while 1
        a0 = dot(r0, r0) / dot(p0, A * p0);
        x1 = x0 + a0 * p0;
        r1 = r0 - a0 * A * p0;
        b0 = dot(r1, r1) / dot(r0, r0);
        p1 = r1 + b0 * p0;
        if dot(p0, A * p0) == 0
            flag = 'success';
            break;
        end
        if abs(p0) < 1e-10 | k == max
            flag = 'failure';
            break;
        end
        x0 = x1;
        r0 = r1;
        p0 = p1;
        k = k + 1;    
    end
    x = x0;
end