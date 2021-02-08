#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define M 3
#define K 2
#define N 3
int A[M][K] = {{1, 4}, {2, 5}, {3, 6}};
int B[K][N] = {{8, 7, 6}, {5, 4, 3}};
int C[M][N] = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

struct value {
    int row;
    int col;
};
void* multiply(void* param);

int main(int argc, char const *argv[]) {
    pthread_t tid[M][N];
    pthread_attr_t attr;

    pthread_attr_init(&attr);

    for(int i = 0; i < M; ++i) {
        for(int j = 0; j < N; ++j) {
            struct value *data = (struct value *)malloc(sizeof(struct value));
            data -> row = i;
            data -> col = j;
            pthread_create(&tid[i][j], &attr, multiply, (void*)data);
        }
    }

    for(int i = 0; i < M; i++) {
        for(int j = 0; j < N; j++) {
            pthread_join(tid[i][j], NULL);
        }
    }

    for(int i = 0; i < M; i++) {
        for(int j = 0; j < N; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }
    return 0;
}

void *multiply(void *param) {
    struct value *mul = (struct value*)param;
    for(int i = 0; i < M; i++) {
        C[mul -> row][mul -> col] += 
                A[mul -> row][i] * B[i][mul -> col];
    }
}