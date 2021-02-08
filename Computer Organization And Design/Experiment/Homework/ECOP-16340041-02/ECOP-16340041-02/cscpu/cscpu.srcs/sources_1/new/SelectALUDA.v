`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:35:35
// Design Name: 
// Module Name: SelectALUDA
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


module SelectALUDA(
    input [4:0] sa,
    input [31:0] Read_data1,
    input ALUSrcA,
    output reg [31:0] rega
    );
    always@(sa or Read_data1 or ALUSrcA)
    if(ALUSrcA)begin
    rega[4:0] = sa;
    rega[31:5] = 27'b0;
    end
    else
     rega = Read_data1;
endmodule
