#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/wait.h>
extern int errno;

#define MAX_SEQUENCE 10
typedef struct {
    int fib_sequence[MAX_SEQUENCE];
    int sequence_size;
} shared_data;

int main() {
    // the identifier for the shared memory segment
    int segment_id;
    // a pointer to the shared memory segment
    shared_data* shared_memory;
    pid_t pid;

    // check
    printf("input a sequence_size no more than 10:\n");
    int n = 0;
    scanf("%d", &n);
    while(n > MAX_SEQUENCE) {
        printf("error sequence_size, input again:\n");
        scanf("%d", &n);
    }

    // al1ocate a shared memory segment 
    segment_id = shmget(IPC_PRIVATE, 80, S_IRUSR | S_IWUSR);
    if(segment_id < 0) {
        printf("shmget error: %s\n", strerror(errno));
        return -1;
    }

    // attach the shared memory segment 
    shared_memory = (shared_data*)shmat(segment_id, NULL, 0);
    if(shared_memory == (void*)-1) {
        printf("shmat error: %s\n", strerror(errno));
        return -1;
    }

    // assignment
    shared_memory -> sequence_size = n;

    // fork
    if((pid = fork()) < 0) {
        printf("fork error: %s\n", strerror(errno));
        return -1;
    } else if(pid == 0) {
        printf("child process:\n");
        shared_memory -> fib_sequence[0] = 0;
        shared_memory -> fib_sequence[1] = 1;
        for(int i = 2; i < shared_memory -> sequence_size; i++) {
            shared_memory -> fib_sequence[i] = 
                shared_memory -> fib_sequence[i - 1] + shared_memory -> fib_sequence[i - 2];
        }
    } else if(pid > 0) {
        wait(NULL);
        printf("parent process:\n");
        for (int i = 0; i < shared_memory -> sequence_size; i++)
        {
            printf("%d ", shared_memory -> fib_sequence[i]);
        }
        printf("\n");
    }

    // now detach the shared memory segment
    if(shmdt(shared_memory) < 0) {
        printf("shmdt error: %s\n", strerror(errno));
        return -1;
    }
    
    // now remove the shared memory segment
    if(shmctl(segment_id, IPC_RMID, NULL) < 0) {
        printf("shmctl error: %s\n", strerror(errno));
        return -1;
    }
    return 0;
}
