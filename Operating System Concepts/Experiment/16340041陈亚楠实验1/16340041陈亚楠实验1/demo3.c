#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>

int main() {
	pid_t pid1, pid2;
	pid1 = fork();
	if(pid1 == 0) {
		printf("b\n");
	} else {
		pid2 = fork();
		if(pid2 == 0) {
			printf("c\n");
		} else {
			printf("a\n");
		}
	}
	return 0;
}