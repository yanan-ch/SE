#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>

//　定义buffer结构体
#define BUFFER_SIZE 50
typedef struct {
    char buffer[BUFFER_SIZE];
} _command;
_command command;

//　用来存放命令的数组，　命令列表
#define HISTORY_COMMAND_SIZE 50
_command history_command[HISTORY_COMMAND_SIZE];
int command_pointer = 0;

/* 每次输入的命令规定不超过80个字符 */
#define MAX_LINE 80

/* 信号处理函数 */
void handle_SIGINT() {
    write(STDOUT_FILENO, command.buffer, strlen(command.buffer));
    int count = 0;
    //从命令列表里输出命令
    for(int j = command_pointer - 1; j > 0; j--) {
        write(STDOUT_FILENO, history_command[j].buffer, strlen(history_command[j].buffer));
        count++;
        if(count >= 10) {
            break;
        }
    }
    exit(0);
}

/* setup()用于读入下一行输入的命令，并将它分成没有空格的命令和参数存于数组args[]中，
 * 用NULL作为数组结束的标志 
 */
int setup(char inputBuffer[], char *args[], int *background) {
    int length = 0, /* 命令的字符数目 */
        i = 0,      /* 循环变量 */
        start = 0,  /* 命令的第一个字符位置 */
        ct = 0;     /* 下一个参数存入args[]的位置 */

    /* 读入命令行字符，存入inputBuffer */
    length = read(STDIN_FILENO, inputBuffer, MAX_LINE);

    start = -1;
    if (length == 0) {
        return 0;; /* 输入ctrl+d，结束shell程序 */
    }
    if (length < 0) {
        perror("error reading the command");
        return -1; /* 出错时用错误码-1结束shell */
    }

    memcpy(history_command[command_pointer].buffer, inputBuffer, strlen(inputBuffer));
    command_pointer++;

    /* 检查inputBuffer中的每一个字符 */
    for (i = 0; i < length; i++) {
        switch (inputBuffer[i]) {
            case ' ':
            case '\t': /* 字符为分割参数的空格或制表符(tab)'\t'*/
                if (start != -1) {
                    args[ct] = &inputBuffer[start];
                    ct++;
                }
                inputBuffer[i] = '\0'; /* 设置 C string 的结束符 */
                start = -1;
                break;
            case '\n': /* 命令行结束 */
                if (start != -1) {
                    args[ct] = &inputBuffer[start];
                    ct++;
                }
                inputBuffer[i] = '\0';
                args[ct] = NULL; /* 命令及参数结束 */
                break;
            default: /* 其他字符 */
                if (start == -1) {
                    start = i;
                }
                if (inputBuffer[i] == '&') {
                    *background = 1; /* 置命令在后台运行 */
                    inputBuffer[i] = '\0';
                }
        }
    }
    args[ct] = NULL; /* 命令字符数 > 80 */
    return 1;
}

int main(void) {
    char inputBuffer[MAX_LINE];   /* 这个缓存用来存放输入的命令*/
    int background;               /* background==1时，表示在后台运行命令，即在命令后加上'&' */
    char *args[MAX_LINE / 2 + 1]; /* 命令最多40个参数 */

    /* 创建信号处理器 */
    struct sigaction handler;
    handler.sa_handler = handle_SIGINT;
    sigaction(SIGINT, &handler, NULL);

    /* 生成输出消息 */
    strcpy(command.buffer, "Caught Control C\n");

    while (1) { /* 程序在setup中正常结束*/
        background = 0;
        printf("COMMAND->");                  //输出提示符，没有换行，仅将字符串送入输出缓存
                                               //若要输出输出缓存内容用fflush(stdout);头文件stdio.h
        int flag = setup(inputBuffer, args, &background); /* 获取下一个输入的命令 */
        /* 这一步要做:
            (1) 用fork()产生一个子进程
            (2) 子进程将调用execvp()执行命令,即 execvp(args[0],args);
            (3) 如果 background == 0, 父进程将等待子进程结束, 即if(background==0) wait(0);
                否则，将回到函数setup()中等待新命令输入.
        */
       pid_t pid = fork();
       if(pid < 0) {
           printf("fork error");
       } else if(pid == 0) {
           printf("in child process, this process id is %d\n", getpid());
           if(flag == -1 || flag == 0) {
               return 0;
           }
           execvp(args[0], args);
       } else if(pid > 0) {
           if(background == 1) {
               printf("parent process is waiting child process\n");
               wait(NULL);
           } else {
               printf("in parent process, this process id is %d\n", getpid());
               setup(inputBuffer, args, &background);
           }
       }

    }
}