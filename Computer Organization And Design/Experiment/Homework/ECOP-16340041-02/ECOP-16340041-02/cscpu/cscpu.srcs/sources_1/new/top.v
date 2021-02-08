`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:43:42
// Design Name: 
// Module Name: top
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

module top(
    input clk,
    input reset,
    output wire [31:0]curPC,
    output wire [31:0]nextPC,
    output wire [4:0]rs,
    output wire [31:0]ReadData1,
    output wire [4:0]rt,
    output wire [31:0]ReadData2,
    output wire [31:0]ALU_result,
    output wire [31:0]DB, // 写回寄存器的值
    output wire [5:0]curOP,
    output wire [31:0]dataOut,
    output wire [1:0]PCSrc,
   	output wire [31:0]rega,
    output wire [31:0]regb,
    output wire [2:0]ALUopcode,
    output wire ExtSel
    );

    wire zero;
  	wire PCWre,ALUSrcA,ALUSrcB,DBDataSrc,InsMemRW,RegWre,RD,WR; 	
  	wire ExtSel,RegDst;
  	wire [31:0]Mem_dataout;  	
  	wire [31:0]extend_num;
  	wire [25:0]addr;
  	wire [4:0]rdreg;
  	wire [4:0]sa;
  	wire [4:0]WriReg;
  	wire [15:0]imme;

    assign curOP = dataOut[31:26];
    assign rs = dataOut[25:21];
    assign rt = dataOut[20:16];
    assign rdreg = dataOut[15:11];
    assign addr = dataOut[25:0];
    assign sa =dataOut[10:6];
    assign imme = dataOut[15:0];

    InstructionRegister U6(.rd(InsMemRW), .iaddr(curPC), .dataOut(dataOut));
  	PC U1(.clk(clk), .reset(reset), .PCWre(PCWre), .nextPC(nextPC), .curPC(curPC));
  	ALU U2(.ALUopcode(ALUopcode), .rega(rega), .regb(regb), .zero(zero), .result(ALU_result), .sign(sign));
  	ControlUnit U3(.PCWre(PCWre), .ALUSrcA(ALUSrcA), .ALUSrcB(ALUSrcB), .DBDataSrc(DBDataSrc), .InsMemRW(InsMemRW), .RegWre(RegWre), .nRD(RD), 
	  			.nWR(WR), .ALUOp(ALUopcode), .PCSrc(PCSrc), .ExtSel(ExtSel), .RegDst(RegDst), .zero(zero), .curOP(curOP));
	DataRegister U4(.address(ALU_result), .writeData(ReadData2), .nRD(RD), .nWR(WR), .Dataout(Mem_dataout), .clk(clk));
	Extend U5(.ExtSel(ExtSel), .imme(imme), .extend_num(extend_num));
    
    PCSelect U7(.curPC(curPC), .nextPC(nextPC), .PCSrc(PCSrc), .extend_imme(extend_num), .addr(addr));
    RegFile U8(.CLK(clk), .RST(reset), .RegWre(RegWre), .ReadReg1(rs), .ReadReg2(rt), .WriteReg(WriReg), .WriteData(DB), .ReadData1(ReadData1), .ReadData2(ReadData2));
    SelectALUDB U9(.Read_data2(ReadData2), .imme_num(extend_num), .ALUSrcB(ALUSrcB), .regb(regb));
    SelectALUDA U10(.Read_data1(ReadData1), .sa(sa), .ALUSrcA(ALUSrcA), .rega(rega));
    SelectDB U11(.DBDataSrc(DBDataSrc), .ALU_result(ALU_result), .Mem_dataout(Mem_dataout), .DB(DB));
    SelectWriReg U12(.RegDst(RegDst), .rt(rt), .rd(rdreg), .WriReg(WriReg));
endmodule
