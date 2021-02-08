`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:41:31
// Design Name: 
// Module Name: SelectWriReg
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


module SelectWriReg(
    input RegDst,
    input [4:0] rt,
    input [4:0] rd,
    output reg [4:0] WriReg
    );
    always@(RegDst or rt or rd)begin
    if(RegDst)
        WriReg = rd;
    else 
        WriReg = rt;
    end
endmodule
