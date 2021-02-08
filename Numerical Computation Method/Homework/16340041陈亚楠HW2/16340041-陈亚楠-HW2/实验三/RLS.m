function [rsteps, results] = RLS(phi, y, m, n)
    rsteps = ones(1, m);
    results = ones(n, m);
    p = eye(n) * 100000;
    result = zeros(n, 1);

    for index = 1 : m
        k = p * phi(index, :)' / (1 + phi(index, :) * p * phi(index, :)');
        result = result + k * (y(index, 1) - phi(index, :) * result);
        rsteps(1, index) = index;
        results(:, index) = result;
        p = (eye(n) - k * phi(index, :)) * p;
    end
end