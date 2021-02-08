#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

int fibonacci[100] = {0};

void* fibonacciFun(void* param);
int fun(int n);

int main(int argc, char *argv[])
{
    if (argc != 2) {
        fprintf(stderr, "usage: a.out <integer value>\n");
        return -1;
    }
    if (atoi(argv[1]) < 0) {
        fprintf(stderr, "%d must be >= 0\n", atoi(argv[1]));
        return -1;
    }

    pthread_t tid;
    pthread_attr_t attr;

    pthread_attr_init(&attr);
    pthread_create(&tid, &attr, fibonacciFun, argv[1]);
    pthread_join(tid, NULL);

    for (int i = 0; i < atoi(argv[1]); i++)
    {
        printf("%d ", fibonacci[i]);
    }

    printf("\n");
    return 0;
}

void* fibonacciFun(void* param) 
{
    int n = atoi(param);
    fun(n);
    ptread_exit(0);
}

int fun(int n) {
    if (n <= 1) {
        fibonacci[n] = n;
        return fibonacci[n];
    }
    else {
        fibonacci[n] = fun(n - 1) + fun(n - 2);
        return fibonacci[n];
    }   
}