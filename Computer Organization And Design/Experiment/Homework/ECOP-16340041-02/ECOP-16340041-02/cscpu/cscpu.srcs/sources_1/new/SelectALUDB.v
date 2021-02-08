`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:37:54
// Design Name: 
// Module Name: SelectALUDB
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


module SelectALUDB(
    input [31:0] Read_data2,
    input [31:0] imme_num,
    input ALUSrcB,
    output reg [31:0] regb
    );
    always@(Read_data2 or imme_num or ALUSrcB)
    if(ALUSrcB)
     regb = imme_num;
    else
     regb = Read_data2;
endmodule
