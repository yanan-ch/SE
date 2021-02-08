@echo off
set xv_path=D:\\Vivado\\Vivado\\2015.3\\bin
call %xv_path%/xsim ALU_1_wrapper_behav -key {Behavioral:sim_1:Functional:ALU_1_wrapper} -tclbatch ALU_1_wrapper.tcl -log simulate.log
if "%errorlevel%"=="0" goto SUCCESS
if "%errorlevel%"=="1" goto END
:END
exit 1
:SUCCESS
exit 0
