.data
	# string inputed by user
	# string length less than 50
	string: .space 50
	newString: .space 50

	# prompt message
	inputStringMessage: .asciiz "Please input one string:\n"
	confirmInputMessage: .asciiz "The string you input is: "
	digitNumberMessage: .asciiz "The number of digits is: "
	letterNumberMessage: .asciiz "The number of letters is: "
	stringLengthMessage: .asciiz "The length of string is: "
	spaceNumberMessage: .asciiz "The number of spaces is: "
	reverseStringMessage: .asciiz "The reversed string is: "
	inputNewStringMessage: .asciiz "Please input a new string:\n"
	confirmNewStringMessage: .asciiz "The new string you input is: "
	compareMessage: .asciiz "After compare, "
	compareSuccessMessage: .asciiz "Password Right!\n"
	compareFailMessage: .asciiz "Password Error!\n"
	newline: .asciiz "\n"

	# debug message
	inLoop: .asciiz "execute in loop\n"
	inDigit: .asciiz "execute in judge digit\n"
	inLowerLetter: .asciiz "execute in judge lowerletter\n"
	inUpperLetter: .asciiz "execute in judge upperletter\n"
	inSpace: .asciiz "execute in judge space\n"
	inNewLine: .asciiz "execute in judge newline\n"
	inReverse: .asciiz "execute in reverse loop\n"

.text
.globl main
main:
	# print input string message
	la $a0, inputStringMessage
	li $v0, 4
	syscall

	# read string
	la $a0, string
	li $v0, 8
	syscall

	# print confirm input message
	la $a0, confirmInputMessage
	li $v0, 4
	syscall

	# print input string
	la $a0, string
	li $v0, 4
	syscall

	# print a newline
	#la $a0, newline
	#li $v0, 4
	#syscall

	#-----------------------------------------

	# init counter
	# $s0 store the original string
	# $s1 store the number of digit
	# $s2 store the number of letter
	# $s3 store the length of original string
	# $s4 store the number of space
	la $s0, string
	move $s1, $zero
	move $s2, $zero
	move $s3, $zero
	move $s4, $zero

	#get the number of digit, letter, space, length

	# $s7 store the char of string in every loop
	loop:
		# la $a0, inLoop
		# li $v0, 4
		# syscall

		lb $s7, 0($s0)
		# judge if space, digit, letter
		jal isSpace
		jal isDigit
		jal isLowerLetter
		jal isUpperLetter

		addi $s0, $s0, 1
		# judge if string over
		# la $a0, inNewLine
		# li $v0, 4
		# syscall
		bne $s7, 0x0a, loop
		move $ra, $zero

		# length handle
		add $t0, $s1, $s2
		add $s3, $t0, $s4

	jal printDatas

	jal reverseString

	#----------------------------------------

	la $a0, newline
	li $v0, 4
	syscall

	#input a new string
	la $a0, inputNewStringMessage
	li $v0, 4
	syscall

	# read new string
	la $a0, newString
	li $v0, 8
	syscall

	# print confirm new input message
	la $a0, confirmNewStringMessage
	li $v0, 4
	syscall

	# print new string
	la $a0, newString
	li $v0, 4
	syscall

	# compare string and newString
	j compareFuntion

	#-----------------------------------------------

	# judge if space
	isSpace:
		# la $a0, inSpace
		# li $v0, 4
		# syscall
		beq $s7, 0x20, spaceCount
		jr $ra

	spaceCount:
		addi $s4, $s4, 1
		jr $ra

	# judge if digit
	isDigit:
		# la $a0, inDigit
		# li $v0, 4
		# syscall
		# judge if less than or equal to '9'
		bleu $s7, 0x39, ifGreater
		jr $ra

		# judge if greater than or equal to '0'
		ifGreater:
			bgeu $s7, 0x30, digitCount
			jr $ra

	digitCount:
		addi $s1, $s1, 1
		jr $ra

	# judge if lowerletter
	isLowerLetter:
		# la $a0, inLowerLetter
		# li $v0, 4
		# syscall

		# judge if less than or equal to 'z'
		bleu $s7, 0x7a, ifLowerGreater
		jr $ra

		# judge if greater than or equal to 'a'
		ifLowerGreater:
			bgeu $s7, 0x61, letterCount
			jr $ra

	# judge if upperletter
	isUpperLetter:
		# la $a0, inUpperLetter
		# li $v0, 4
		# syscall

		# judge if less than or equal to 'Z'
		ifUpperLess:
			bleu $s7, 0x5a, ifUpperGreater
			jr $ra

		# judge if greater than or equal to 'A'
		ifUpperGreater:
			bgeu $s7, 0x41, letterCount
			jr $ra

	letterCount:
		addi $s2, $s2, 1
		jr $ra

	#print the number of digit, letter, space, length message
	printDatas:
		la $a0, digitNumberMessage
		li $v0, 4
		syscall
		li $v0, 1
	  	la $a0, 0($s1)
	  	syscall
	  	la $a0, newline
		li $v0, 4
		syscall

		la $a0, letterNumberMessage
		li $v0, 4
		syscall
		li $v0, 1
	  	la $a0, 0($s2)
	  	syscall
	  	la $a0, newline
		li $v0, 4
		syscall

		la $a0, spaceNumberMessage
		li $v0, 4
		syscall
		li $v0, 1
	  	la $a0, 0($s4)
	  	syscall
	  	la $a0, newline
		li $v0, 4
		syscall

		la $a0, stringLengthMessage
		li $v0, 4
		syscall
		li $v0, 1
	  	la $a0, 0($s3)
	  	syscall
	  	la $a0, newline
		li $v0, 4
		syscall

		jr $ra

	#--------------------------------------------------

	reverseString:
		# print prompt message
		la $a0, reverseStringMessage
		li $v0, 4
		syscall

		# $t1 counter
		# $t2 as a pointer to the end of string gradually
		move $t1, $zero
		la $t2, string

		# a loop move $t2 to the end of string
		toEndLoop:
			addi $t1, $t1, 1
			addi $t2, $t2, 1
			bne $t1, $s3, toEndLoop
			move $t1, $zero

		# reverse and printf char
		reverseLoop:
			addi $t1, $t1, 1
			addi $t2, $t2, -1
			lb $t3, 0($t2)
			beq $t3, 0x20, reverseLoop
			#print the char
			li $v0, 11
			la $a0, 0($t3)
			syscall
			#judge string if over
			bne $t1, $s3, reverseLoop
			jr $ra

	#---------------------------------------------------

	#compare string and newString
	compareFuntion:
		la $a0, compareMessage
		li $v0, 4
		syscall

		# la $a0, compareSuccessMessage
		# li $v0, 4
		# syscall

		# $s0 store the original string
  		# $s5 store the newstring
  		la $s0, string
  		la $s5, newstring

  		move $t4, $zero
		# $s6 store the length of original string without space
		add $s6, $s1, $s2

		loopA:
			lb $t5, 0($s5)
			loopB:
				lb $t6, 0($s0)
				bne $t6, 0x20, continue
				addi $s0, $s0, 1
				j loopB

			continue:
			bne $t5, $t6, compareFail
			beq $t5, 0x0a, isSubstring
			beq $t6, 0x0a, compareFail
			addi $s0, $s0, 1
			addi $s5, $s5, 1
			addi $t4, $t4, 1
			j loopA

		compareFail:
			la $a0, compareFailMessage
			li $v0, 4
			syscall
			j exitProgram

		compareSuccess:
			la $a0, compareSuccessMessage
			li $v0, 4
			syscall
			j exitProgram

		isSubstring:
			beq $s6, $t4, compareSuccess
			j compareFail

	exitProgram:
		li $v0, 10
		syscall