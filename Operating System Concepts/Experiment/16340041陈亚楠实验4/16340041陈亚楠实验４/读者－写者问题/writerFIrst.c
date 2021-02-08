#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>

#define TESTNUMBER 5

typedef struct
{
    pthread_t pthreadId;
    int sleepTime;
    int keepTime;
} data;

void *writer(void *param);
void *reader(void *param);
void createPthread();

// readMutex确保更新readcount时的互斥
sem_t readMutex, wrt, writeMutex, rd;
int readcount, writecount;

pthread_attr_t attr;

int main(int argc, char const *argv[])
{
    // 初始化
    sem_init(&readMutex, 0, 1);
    sem_init(&wrt, 0, 1);
    sem_init(&writeMutex, 0, 1);
    sem_init(&rd, 0, 1);
    readcount = 0;
    writecount = 0;
    pthread_attr_init(&attr);
    //创建线程
    createPthread();

    sleep(50);

    sem_destroy(&readMutex);
    sem_destroy(&wrt);
    sem_destroy(&writeMutex);
    sem_destroy(&rd);

    return 0;
}

void createPthread()
{
    pthread_t pthreadId;
    char pthreadRole;
    for (int i = 0; i < TESTNUMBER; i++)
    {
        scanf("%ld %c", &pthreadId, &pthreadRole);
        data *pthread = malloc(sizeof(data));
        pthread->pthreadId = pthreadId;
        if (pthreadRole == 'R')
        {
            printf("Create a reader pthread %ld\n", pthreadId);
            scanf("%d %d", &pthread->sleepTime, &pthread->keepTime);
            pthread_create(&pthreadId, &attr, reader, pthread);
        }
        else if (pthreadRole == 'W')
        {
            printf("Create a writer pthread %ld\n", pthreadId);
            scanf("%d %d", &pthread->sleepTime, &pthread->keepTime);
            pthread_create(&pthreadId, &attr, writer, pthread);
        }
        else
        {
            printf("Invalid input!\n");
            exit(-1);
        }
    }
}

void *writer(void *param)
{
    data *pthread = (data *)param;
    pthread_t pthreadId = pthread->pthreadId;
    int sleepTime = pthread->sleepTime;
    int keepTime = pthread->keepTime;
    free(pthread);
    while (true)
    {
        sleep(sleepTime);
        printf("Writer pthread %ld wants to write.\n", pthreadId);
        sem_wait(&writeMutex);
        writecount++;
        if(writecount == 1) {
            sem_wait(&rd);
        }
        sem_post(&writeMutex);

        sem_wait(&wrt);
        printf("Writer pthread %ld is writing.\n", pthreadId);
        sleep(keepTime);
        printf("Writer pthread %ld finishs to write.\n", pthreadId);
        sem_post(&wrt);

        sem_wait(&writeMutex);
        writecount--;
        if (writecount == 0)
        {
            sem_wait(&rd);
        }
        sem_post(&writeMutex);
        break;
    }
}

void *reader(void *param)
{
    data *pthread = (data *)param;
    pthread_t pthreadId = pthread->pthreadId;
    int sleepTime = pthread->sleepTime;
    int keepTime = pthread->keepTime;
    free(pthread);
    while (true)
    {
        sleep(sleepTime);
        printf("Reader pthread %ld wants to read.\n", pthreadId);
        sem_wait(&rd);
        sem_wait(&readMutex);
        readcount++;
        if (readcount == 1)
        {
            sem_wait(&wrt);
        }
        sem_post(&readMutex);
        sem_post(&rd);
        printf("Reader pthread %ld is reading.\n", pthreadId);
        sleep(keepTime);
        printf("Reader pthread %ld finishs to read.\n", pthreadId);
        sem_wait(&readMutex);
        readcount--;
        if (readcount == 0)
        {
            sem_post(&wrt);
        }
        sem_post(&readMutex);
        break;
    }
}