`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:33:14
// Design Name: 
// Module Name: PCSelect
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


module PCSelect(
    input [31:0] curPC,
    output reg [31:0] nextPC,
    input [1:0] PCSrc,
    input [31:0] extend_imme,
    input [25:0] addr
    );
    reg [31:0] PC0;
    reg [31:0] PC1;
    reg [31:0] PC2;
    always@(PCSrc or curPC or extend_imme or addr)
    begin
    PC0 = curPC + 4;
    PC1 = (extend_imme << 2)+ PC0;
    PC2[31:28] = PC0[31:28];
    PC2[27:2] = addr[25:0];
    PC2[1:0] = 2'b00;
    case(PCSrc)
    2'b00 :  nextPC = PC0;
    2'b01 :  nextPC = PC1;
    2'b10 :  nextPC = PC2;
    2'b11 : nextPC = 32'bz;
    endcase
    end
endmodule
