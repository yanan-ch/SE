nodes = load('soc-Epinions1.txt');
maxSize = max(max(nodes)) + 1;
sparseM = sparse(maxSize, maxSize);
delta = 0.0001;
beta = 0.85;
edgeSum = zeros(maxSize, 1);
for i = 1:size(nodes, 1)
    edgeSum(nodes(i, 1) + 1) = edgeSum(nodes(i, 1) + 1) + 1;
end
for i = 1:size(nodes, 1)
    sparseM(nodes(i, 2) + 1, nodes(i, 1) + 1) = 1 / edgeSum(nodes(i, 1) + 1);
end
num = size(sparseM, 2);
v = rand(num, 1);
v = v ./ norm(v, 1); 
vlast = ones(num,1) * inf;
while(max(abs(v - vlast)) > delta)
    vlast = v;
    v = beta * sparseM * v + (1 - beta) / num * ones(num, 1);
end;
node = 0:75887;
plot(node,v);
[maximum, pos] = max(v);
disp(maximum);
disp(pos);