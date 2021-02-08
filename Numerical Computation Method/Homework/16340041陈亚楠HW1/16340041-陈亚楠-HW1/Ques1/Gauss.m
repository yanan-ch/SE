% 高斯消去法解线性方程组;
% A为方程组的系数矩阵;
% b为方程组的右端项；
% x为方程组的解;
function x = Gauss(A, b)
    augmentedMatrix = [A b];
    n = length(b);
    ra = rank(A);
    rz = rank(augmentedMatrix);
    temp = rz - ra;
    if temp > 0
        disp('failure1')
        return
    end
    if ra == rz
        if ra == n
            x = zeros(n, 1);
            % 消元运算
            for k = 1:n - 1
                for i = k + 1:n
                    m = augmentedMatrix(i, k) / augmentedMatrix(k, k);
                    augmentedMatrix(i, k:n + 1) = augmentedMatrix(i, k:n + 1) - m * augmentedMatrix(k, k:n + 1);
                end
            end
            % 回代过程
            b = augmentedMatrix(1:n, n + 1);
            A = augmentedMatrix(1:n, 1:n);
            x(n) = b(n) / A(n, n);
            for j = n - 1:-1:1
                x(j) = (b(j) - sum(A(j, j + 1:n) * x(j + 1:n))) / A(j, j);
            end
        else
            disp('failure2')
        end
    end   
end