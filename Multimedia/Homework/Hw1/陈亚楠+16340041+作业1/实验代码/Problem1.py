from PIL import Image
import math
import imageio
import os

nobelImg = Image.open("诺贝尔.jpg")
lenaImg = Image.open("lena.jpg")
xMax = nobelImg.width
yMax = nobelImg.height
rMax = math.sqrt(math.pow(xMax/2, 2) + math.pow(yMax/2, 2))
factor = 0

while factor <= 10:
    for x in range(xMax):
        for y in range(yMax):
            # 计算图片切换半径
            radius = math.sqrt(math.pow(x - xMax/2, 2) + math.pow(y - yMax/2, 2))
            if radius < rMax * factor / 10:
                nobelImg.putpixel((x,y), lenaImg.getpixel((x, y)))
    outName = str(factor) + ".jpg"
    r, g, b = nobelImg.split()
    # 生成缩略图
    r.thumbnail((100, 100))
    # 保存为图片序列
    r.save(outName, "JPEG") 
    factor += 1    

# 生成.gif
'''
frames = []
path = 'D:\\Problem1Sequence'
gifName = 'p1gif.gif'
imgs = os.listdir(path)
imageList = [os.path.join(path, j) for j in imgs]
for img in imageList:
    frames.append(imageio.imread(img))
    imageio.mimsave(gifName, frames, 'GIF', duration = 0.3)
'''