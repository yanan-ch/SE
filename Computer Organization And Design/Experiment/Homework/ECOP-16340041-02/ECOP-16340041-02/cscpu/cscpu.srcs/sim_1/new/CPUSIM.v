`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:57:07
// Design Name: 
// Module Name: CPUSIM
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module CPUSIM();
    // input
    reg clock;
    reg reset;
   wire [31:0]curPC;
    wire [31:0]nextPC;
   wire [4:0]rs;
   wire [31:0]ReadData1;
   wire [4:0]rt;
    wire [31:0]ReadData2;
    wire [31:0]ALU_result;
   wire [31:0]DB;
   wire [5:0]curOp;
   wire [31:0]dataOut;
   wire [1:0]PCSrc;
   wire [31:0] rega;
   wire [31:0] regb;
   wire [2:0]ALUopcode;	
   wire ExtSel;
    // output
    top uut(.clk(clock), .reset(reset), .curPC(curPC), .nextPC(nextPC), .rs(rs), .ReadData1(ReadData1), .rt(rt), .ReadData2(ReadData2),.ALU_result(ALU_result),.DB(DB), .curOP(curOp),
     .dataOut(dataOut),.PCSrc(PCSrc),.rega(rega),.regb(regb),.ALUopcode(ALUopcode), .ExtSel(ExtSel) );
     initial begin
         // Initialize Inputs
         clock = 0;
         reset = 0;
         #50; // 刚开始设置pc为0
             clock = !clock;
         #50;
             reset = 1;
       forever #50 begin // 产生时钟信号
             clock = !clock;
         end
     end
endmodule
