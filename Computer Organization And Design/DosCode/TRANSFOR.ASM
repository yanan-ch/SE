DATA SEGMENT
SOURCE DB 'After all tomorrow is another day','$'
COUNT EQU $-SOURCE
DATA ENDS
EXTRA SEGMENT
DEST DB COUNT DUP ('$');不可以为？
EXTRA ENDS
CODE SEGMENT
ASSUME CS:CODE, DS:DATA,ES:DATA
BEGIN:
        MOV AX,DATA
        MOV DS,AX
        MOV AX,EXTRA
        MOV ES,AX;ES附加段寄存器
        MOV CX,COUNT
	ADD CX,-01H;将SOURCE中除了最后一位外改为大写后送给DEST
        LEA SI,SOURCE
        LEA DI,DEST
        AG:
                LODSB;把DS:SI指向的存储单元中的数据装入AL或AX，然后根据DF标志增减SI
                AND AL,0DFH;将字母改为大写
                STOSB;把AL或AX中的数据装入ES:DI指向的存储单元，然后根据DF标志增减DI
        LOOP AG
	MOV CX,COUNT
   	MOV AX,EXTRA
   	MOV DS,AX
    	LEA DX,DEST
    	MOV AH,09H;DS：DX，缓冲区首地址，DX偏移量
    	INT 21H
    	MOV AH,4cH
    	INT 21H
CODE ENDS
END BEGIN
