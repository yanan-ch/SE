`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:39:12
// Design Name: 
// Module Name: SelectDB
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


module SelectDB(
    input DBDataSrc,
    input [31:0] ALU_result,
    input [31:0] Mem_dataout,
    output reg [31:0]DB
    );
    always@(DBDataSrc or ALU_result or Mem_dataout)
    if(DBDataSrc)
        DB = Mem_dataout;
    else
       DB = ALU_result;
endmodule
