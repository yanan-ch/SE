% 列主元消去法解线性方程组;
% A为系数矩阵;
% b为方程组的右端项;
% x为方程组的解;
% det为系数矩阵的行列式的值;
% flag = 'failure'表示失败,flag = 'success'表示成功
function[x, det, flag] = Pivot(A, b)
    [n, m] = size(A);
    nb = length(b);
    if n ~= m
        disp('error')
        return;
    end
    if m ~= nb
        disp('error')
        return;
    end
    % 初始化
    x = zeros(n, 1);
    det = 1;
    flag = 'success';
    for k = 1:n - 1
        % 选主元
        max = 0;
        for i = k:n
            if abs(A(i, k)) > max
                max = abs(A(i, k));
                r = i;
            end
        end
        if max < 1e-10
            flag = 'failure';
            return;
        end
        % 交换两行
        if r > k
            for j = k:n
                z = A(k, j);
                A(k, j) = A(r, j);
                A(r, j) = z;
            end
            z = b(k);
            b(k) = b(r);
            b(r) = z;
            det = -det;
        end
        % 消元运算
        for i = k + 1:n
            m = A(i, k) / A(k, k);
            for j = k + 1:n
                A(i, j) = A(i, j) - m * A(k, j);
            end
            b(i) = b(i) - m * b(k);
        end
        det = det * A(k, k);        
    end
    det = det * A(n, n);
    % 回代过程
    if abs(A(n, n) < 1e-10)
        flag = 'failure';
        return;
    end
    for k = n:-1:1
        for j = k + 1:n
            b(k) = b(k) - A(k, j) * x(j);
        end
        x(k) = b(k) / A(k, k);
    end
    x(k) = b(k) / A(k, k);
end