`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/28 19:12:53
// Design Name: 
// Module Name: PC
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


module PC(
    input clk, input reset, input PCWre, input [31:0] nextPC, output reg [31:0] curPC
    );
    always @(posedge clk or negedge reset) begin
        if(reset == 1'b0)
            curPC <= 0; // 初始化PC
        else if(PCWre)
            curPC <= nextPC; //下条指令的地址
    end
endmodule
