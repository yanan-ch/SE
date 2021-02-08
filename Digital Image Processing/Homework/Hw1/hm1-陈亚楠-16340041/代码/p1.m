clear
img = imread('river.jpg');
[row, column] = size(img);
figure
subplot(3, 2, 1)
imshow(img)
title('Source image')
subplot(3, 2, 2)
imhist(img)
title('Source image histogram')

%统计不同灰度级像素数�??
%预存不同灰度级像素数量的向量
%（灰度�?�，数量�??
tmp = zeros(1, 256);
for i = 1:row
    for j = 1:column
        tmp(img(i, j) + 1) = tmp(img(i, j) + 1) + 1;
    end
end
%计算不同灰度级出现的概率
%（灰度�?�，概率�??
for i = 1:256
    tmp(i) = tmp(i) / (row * column * 1.0);
end
%计算直方图变换函数的值，即求概率�??
%预存直方图变换函数�?�的向量
%（灰度�?�，概率和）
sum = zeros(1, 256);
sum(1) = tmp(1);
for i = 2:256
    sum(i) = sum(i - 1) + tmp(i);
end
%将直方图变换函数值取�??
%（灰度�?�，概率和取整）
sum = round(255 .* sum);
%对图像进行均衡化
for i = 1:row
    for j = 1:column
        img(i, j) = sum(img(i, j) + 1);
    end
end
subplot(3, 2, 3)
imshow(img)
title('Equalizing images')
imwrite(img, 'p1result.jpg')
subplot(3, 2, 4)
imhist(img)
title('Equalizing histogram')
img = imread('river.jpg');
subplot(3, 2, 5)
img_tmp = histeq(img, 256);
imshow(img_tmp)
title('Equalizing images with histeq()')
subplot(3, 2, 6)
imhist(img_tmp)
title('Equalizing histogram with histeq()')