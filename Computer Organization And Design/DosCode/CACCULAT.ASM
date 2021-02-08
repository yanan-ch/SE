DATA SEGMENT
strAdd  DB 'Result for add: ','$'
strSub DB 'Result for sub: ','$'
strMul  DB 'Result for mul: ','$'
strDiv DB 'Result for div: ','$'
DATA ENDS
CODE SEGMENT
ASSUME CS:CODE
MAIN PROC FAR
	PUSH DS
	MOV AX,0
	PUSH AX
	getFirstNum:
		MOV AH,1
		INT 21H
		CMP AL,20H;空格，代表第二个数字将输入
		JE storeFirstNum
		SUB AL,30H;得到数字而非ascii码
		JL EXIT;若输入的非数字则退出
		CMP AL,9
		JG EXIT
		CBW
		XCHG AX,BX
		MOV CX,10
		MUL CX
		XCHG AX,BX
		ADD BX,AX
		JMP getFirstNum
	storeFirstNum:
		MOV DI,BX
		MOV BX,0
	getSecondNum: 
		MOV AH,1
		INT 21H
		CMP AL,0DH;回车则计算并输出结果,call cacullate
		JE CACULLATE
		SUB AL,30H
		JL EXIT;
		CMP AL,9
		JG EXIT
		CBW;将答案再次存在BX中
		XCHG AX,BX
		MOV CX,10
		MUL CX
		XCHG AX,BX
		ADD BX,AX
		JMP getSecondNum
	CACULLATE:
		PUSH DI
		PUSH BX
		CALL myAdd
		POP BX
		POP DI
		PUSH DI
		PUSH BX
		CALL mySub
		POP BX
		POP DI
		PUSH DI
		PUSH BX
		CALL myMul
		POP BX
		POP DI
		PUSH DI
		PUSH BX
		CALL myDiv
		POP BX
		POP DI
	EXIT:
		RET
	MAIN ENDP
	;结果分别存在DI和BX中
myAdd PROC NEAR
	MOV AX,DATA
	MOV DS,AX
	MOV AH,09H
	LEA DX,strAdd
	INT 21H
	ADD BX,DI
	CALL OUTPUT
	RET
myAdd ENDP
mySub PROC NEAR
	MOV AX,DATA
	MOV DS,AX
	MOV AH,09H
	LEA DX,strSub
	INT 21H
	MOV AX,DI
	SUB AX,BX
	MOV BX,AX
	CALL OUTPUT
	RET
mySub ENDP
myDiv PROC NEAR;/
	MOV AX,DATA
	MOV DS,AX
	MOV AH,09H
	LEA DX,strDiv
	INT 21H
	MOV AX,DI
	MOV DX,0
	DIV BX
	MOV BX,AX
    CALL OUTPUT
	RET
myDiv ENDP
myMul PROC NEAR
	MOV AX,DATA
	MOV DS,AX
	MOV AH,09H
	LEA DX,strMul
	INT 21H
    MOV AX,DI
    MUL BX
    MOV BX,AX
    CALL OUTPUT
    RET
myMul ENDP

OUTPUT PROC NEAR
	CALL OUTPUTB
	CALL OUTPUTO
	CALL OUTPUTD
	CALL OUTPUTH
	RET
OUTPUT ENDP
OUTPUTB PROC NEAR
	PUSH BX
	MOV CX,16
	L: ROL BX,1;
	JC NEZERO
	MOV DL,30H
	JMP OUTPB
	NEZERO:
		MOV DL,31H
	OUTPB:
		MOV AH,2
		INT 21H
	LOOP L
	MOV DL,'B'
	MOV AH,2
	INT 21H
	MOV DL,' '
	MOV AH,2
	INT 21H
	POP BX
	RET
OUTPUTB ENDP

OUTPUTO PROC NEAR
	PUSH BX
	MOV CX,32768
	CALL octDiv
	MOV CX,4096
	CALL octDiv
	MOV CX,512
	CALL octDiv
	MOV CX,64
	CALL octDiv
	MOV CX,8
	CALL octDiv
	MOV CX,1
	CALL octDiv
	MOV DL,'O'
	MOV AH,2
	INT 21H
	MOV DL,' '
	MOV AH,2
	INT 21H
	POP BX
	RET
OUTPUTO ENDP

octDiv PROC NEAR
    MOV AX,BX;双字节除法：DX AX除BX=AX(商)，DX(余数) 
    MOV DX,0
    DIV CX
    MOV BX,DX
    ADD AL,30H
    MOV DL,AL
    MOV AH,2
    INT 21H
    RET
octDiv ENDP

OUTPUTD PROC NEAR
	PUSH BX
	MOV CX,10000
    CALL decDiv
    MOV CX,1000
    CALL decDiv
    MOV CX,100
    CALL decDiv
    MOV CX,10
    CALL decDiv
    MOV CX,1
    CALL decDiv
	MOV DL,'D'
	MOV AH,2
	INT 21H
	MOV DL,' '
	MOV AH,2
	INT 21H
	POP BX
    RET
OUTPUTD ENDP
  
decDiv PROC NEAR
    MOV AX,BX;双字节除法：DX AX除BX=AX(商)，DX(余数) 
    MOV DX,0
    DIV CX
    MOV BX,DX
    ADD AL,30H
    MOV DL,AL
    MOV AH,2
    INT 21H
    RET
decDiv ENDP

OUTPUTH PROC NEAR
	PUSH BX
	MOV CL,4
	MOV CH,4
	NEXT:
		ROL BX,CL
		MOV AL,BL
		AND AL,0FH
		ADD AL,30H
		CMP AL,3AH;9
		JL OUTH
		ADD AL,07H;A~F,加7
	OUTH:
		MOV DL,AL
		MOV AH,02H
		INT 21H
	DEC CH
	CMP CH,0
	JA NEXT
	MOV DL,'H'
	MOV AH,2
	INT 21H
	MOV DL,0DH
    MOV AH,2
    INT 21H
    MOV DL,0AH
    MOV AH,2
    INT 21H
	POP BX
	RET
OUTPUTH ENDP
CODE ENDS
END MAIN