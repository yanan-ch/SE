@echo off
set xv_path=D:\\Vivado\\Vivado\\2015.3\\bin
call %xv_path%/xelab  -wto d4d2919c14de4f858504e2b4711208fc -m64 --debug typical --relax --mt 2 -L xil_defaultlib -L unisims_ver -L unimacro_ver -L secureip --snapshot ALU_1_wrapper_behav xil_defaultlib.ALU_1_wrapper xil_defaultlib.glbl -log elaborate.log
if "%errorlevel%"=="0" goto SUCCESS
if "%errorlevel%"=="1" goto END
:END
exit 1
:SUCCESS
exit 0
