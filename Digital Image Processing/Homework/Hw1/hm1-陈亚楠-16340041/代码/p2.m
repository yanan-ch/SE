clear
imgOrigin = imread('EightAM.png');
imgStd = imread('LENA.png');
[rowOrigin, colOrigin] = size(imgOrigin);
[rowStd, colStd] = size(imgStd);
histOrigin = imhist(imgOrigin) / (rowOrigin * colOrigin);
histStd = imhist(imgStd) / (rowStd * colStd);
%预存图像累积直方图的向量
vecOrigin = [];
vecStd = [];
%计算概率和
for i = 1:256
    vecOrigin = [vecOrigin sum(histOrigin(1:i))];
    vecStd = [vecStd sum(histStd(1:i))];
end
%得到映射关系
for i = 1:256
    tmp{i} = vecStd - vecOrigin(i);
    tmp{i} = abs(tmp{i});
    [mid index(i)] = min(tmp{i});
end
%映射到新的图像
img = zeros(rowOrigin, colOrigin);
for i = 1: rowOrigin
    for j = 1:colOrigin
        img(i, j) = index(imgOrigin(i, j) + 1) - 1;
    end
end
img = uint8(img);

figure
subplot(3, 2, 1)
imshow(imgOrigin)
title('Source image')
subplot(3, 2, 2)
imhist(imgOrigin)
title('Source image histogram')
subplot(3, 2, 3)
imshow(imgStd)
title('Standard image')
subplot(3, 2, 4)
imhist(imgStd)
title('Standard image histogram')
subplot(3, 2, 5)
imshow(img)
title('After histogram matching')
imwrite(img, 'p2result.jpg')
subplot(3, 2, 6)
imhist(img)
title('matching histogram')