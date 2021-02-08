#include "buffer.h"
#include <stdio.h>

// 缓冲区定义
buffer_item buffer[BUFFER_SIZE];

int head = 0;
int rear = 0;
int count = 0;

int insert_item(buffer_item item)
{
    if (count <= BUFFER_SIZE) {
        buffer[rear] = item;
        rear = (rear + 1) % BUFFER_SIZE;
        count++;
        return 0;
    } else {
        return -1;
    }
}

int remove_item(buffer_item item) 
{
    if (count == 0) {
        return -1;
    } else {
        item = buffer[head];
        head = (head + 1) % BUFFER_SIZE;
        count--;
        return 0;
    }
}