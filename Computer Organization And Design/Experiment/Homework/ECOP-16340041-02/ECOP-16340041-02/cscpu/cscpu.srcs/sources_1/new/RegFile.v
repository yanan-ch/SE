`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/28 21:03:15
// Design Name: 
// Module Name: RegFile
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


module RegFile(CLK,RST,RegWre,ReadReg1,ReadReg2,WriteReg,WriteData,ReadData1,ReadData2);
    input CLK;
    input RST;
    input RegWre;
    input [4:0] ReadReg1,ReadReg2,WriteReg;
    input [31:0] WriteData;
    output [31:0] ReadData1,ReadData2;
     reg [31:0] regFile[1:31];
   // 寄存器定义必须用reg类型
    integer i;
    assign ReadData1 = (ReadReg1 == 0) ? 0 : regFile[ReadReg1]; // 读寄存器数据
    assign ReadData2 = (ReadReg2 == 0) ? 0 : regFile[ReadReg2];
    always @ (negedge CLK or negedge RST) begin // 必须用时钟边沿触发
        if (RST==0) begin
            for(i=1;i<32;i=i+1)
            regFile[i] <= 0;
        end
        else if(RegWre == 1 && WriteReg != 0) // WriteReg != 0，$0寄存器不能修改
             regFile[WriteReg] <= WriteData; // 写寄存器
    end
endmodule
