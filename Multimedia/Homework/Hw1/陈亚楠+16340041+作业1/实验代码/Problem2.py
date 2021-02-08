from PIL import Image

colorTable = []
colorLUT = []

# 获取原图像的所有颜色
def getColorTable(image):
    width = image.width
    height = image.height
    for x in range(width):
        for y in range(height):
            r, g, b = image.getpixel((x, y))
            cube = (r,g,b)
            colorTable.append(cube)

# 根据r, g, b的排序函数
def sortByR(cube):
    return cube[0]

def sortByG(cube):
    return cube[1]

def sortByB(cube):
    return cube[2]

# 中位切分算法
def medianCut(slice, times):
    length = len(slice)
    if times >= 8:
        rSum = 0
        gSum = 0
        bSum = 0
        for i in range(length):
            rSum += slice[i][0]
            gSum += slice[i][1]
            bSum += slice[i][2]
        newCube = (rSum / length, gSum / length, bSum / length)
        colorLUT.append(newCube)
        return
    if times % 3 == 0:
        slice.sort(key=sortByR)
    elif times % 3 == 1:
        slice.sort(key=sortByG)
    else:
        slice.sort(key=sortByB)
    medianCut(slice[:length//2], times+1)
    medianCut(slice[length//2:], times+1)

# 计算距离
def getDis(cube1, cube2):
    r = cube1[0] - cube2[0]
    g = cube1[1] - cube2[1]
    b = cube1[2] - cube2[2]
    return r*r + g*g + b*b

# 获取最短欧式距离的颜色
def getNewColor(cube):
    length = len(colorLUT)
    index = 0
    dis = getDis(cube, colorLUT[0])
    for i in range(length):
        newDis = getDis(cube, colorLUT[i])
        if newDis < dis:
            index = i
            dis = newDis
    return colorLUT[index]

# 生成新的8位彩色图像
def toNewImage(image):
    width = image.width
    height = image.height
    img = Image.new("RGB", (width, height))
    for x in range(width):
        for y in range(height):
            r, g, b = image.getpixel((x, y))
            cube = (r,g,b)
            colorCube = getNewColor(cube)
            img.putpixel((x, y), (int(colorCube[0]), int(colorCube[1]), int(colorCube[2])))
    return img

def main():
    times = 0
    img = Image.open("redapple.jpg")
    getColorTable(img)
    medianCut(colorTable, times)
    resImage = toNewImage(img)
    resImage.save("p2resImg.jpg", "JPEG")

main()
