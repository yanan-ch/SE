#include "buffer.h"
#include <pthread.h>
#include <semaphore.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <stdbool.h>

/* 测试数据格式：
 * 线程序号
 * 线程角色
 * 操作开始时间（线程创建后，延迟相应时间(单位为秒)后发出对共享资源的使用申请）
 * 操作持续时间
 * 生产者生产的产品号
*/

#define TESTNUMBER 6

void *producer(void *param);
void *consumer(void *param);
void createPthread();

// 测试数据结构
typedef struct
{
    pthread_t pthreadId;
    int sleepTime;
    int keepTime;
    buffer_item productId;
}data;

sem_t mutex;
sem_t empty;
sem_t full;

pthread_attr_t attr;

int main(int argc, char const *argv[])
{
    // 初始化信号量
    sem_init(&mutex, 0, 1);
    sem_init(&empty, 0, BUFFER_SIZE);
    sem_init(&full, 0, 0);

    pthread_attr_init(&attr);
    //pthread_t pthreadArray[TESTNUMBER];
    // 创建进程
    createPthread();

    sleep(60);

    sem_destroy(&mutex);
    sem_destroy(&empty);
    sem_destroy(&full);

    return 0;
}

void createPthread() {
    pthread_t pthreadId;
    char pthreadRole;
    for(int i = 0; i < TESTNUMBER; i++)
    {
        scanf("%ld %c ", &pthreadId, &pthreadRole);
        data* pthread = malloc(sizeof(data));
        pthread->pthreadId = pthreadId;
        if(pthreadRole == 'C') {
            scanf("%d %d", &pthread->sleepTime, &pthread->keepTime);
            pthread_create(&pthreadId, &attr, consumer, pthread);
        }  
        else if (pthreadRole == 'P') {
            scanf("%d %d %d", &pthread->sleepTime, &pthread->keepTime, &pthread->productId);
            pthread_create(&pthreadId, &attr, producer, pthread);
        } else {
            printf("Invalid input!\n");
            exit(-1);
        }
    }
}

void *producer(void* param) 
{
    data* pthread = (data*)param;
    pthread_t pthreadId = pthread->pthreadId;
    int sleepTime = pthread->sleepTime;
    int keepTime = pthread->keepTime;
    buffer_item productId = pthread->productId;
    free(pthread);
    while(true){
        sleep(sleepTime);
        sem_wait(&empty);
        sem_wait(&mutex);
        if (insert_item(productId)) {
            printf("Error, the buffer is full!\n");
            exit(-1);
        } else {
            printf("Producer pthread %ld produced product %d.\n", pthreadId, productId);
        }
        sleep(keepTime);
        sem_post(&mutex);
        sem_post(&full);
        break;
    }  
}

void *consumer(void *param)
{
    data *pthread = (data *)param;
    pthread_t pthreadId = pthread->pthreadId;
    int sleepTime = pthread->sleepTime;
    int keepTime = pthread->keepTime;
    buffer_item bufferItem;
    free(pthread);
    while(true){
        sleep(sleepTime);
        sem_wait(&full);
        sem_wait(&mutex);
        if (remove_item(bufferItem))
        {
            printf("Error, The buffer is empty!\n");
            exit(-1);
        }
        else
        {
            printf("Consumer pthread %ld consumed product.\n", pthreadId);
        }
        sleep(keepTime);
        sem_post(&mutex);
        sem_post(&empty);
        break;
    }
}