DATA SEGMENT
    SRC DB 'lower to upper masm', 0DH, '$'
    COUNT EQU $-SRC
DATA ENDS
EXTRA SEGMENT
    DES DB COUNT DUP ('$')
EXTRA ENDS
CODE SEGMENT
    ASSUME CS:CODE, DS:DATA, ES:EXTRA
    BEGIN:
        MOV AX, DATA
        MOV DS, AX
        MOV AX, EXTRA
        MOV ES, AX
        MOV CX, COUNT
        ADD CX, -01H
        LEA SI, SRC
        LEA DI, DES
        AGAIN:
            LODSB
            AND AL, 0DFH
            STOSB
            LOOP AGAIN
        MOV CX, COUNT
        MOV AX, EXTRA
        MOV DS, AX
        LEA DX, DES
        MOV AH, 09H
        INT 21H
        MOV AH, 4CH
        INT 21H
CODE ENDS
    END BEGIN