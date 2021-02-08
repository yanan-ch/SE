`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:31:44
// Design Name: 
// Module Name: Extend
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


module Extend(
    input ExtSel,
    input [15:0] imme,
    output reg [31:0] extend_num
    );
    always@(ExtSel or imme)
    begin
        extend_num[15:0] = imme;
        if(imme[15]==0|ExtSel==0) 
            extend_num[31:16]=16'h0000;
        else
            extend_num[31:16]=16'hffff;
    end
endmodule
