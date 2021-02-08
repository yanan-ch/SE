/* 缓冲区定义：
 * 缓冲区是一个元数据类型为buffer_item的固定大小的数组
*/

// 缓冲区元数据buffer_item定义
typedef int buffer_item;
// 缓冲区大小
#define BUFFER_SIZE 5

int insert_item(buffer_item item);
int remove_item(buffer_item item);