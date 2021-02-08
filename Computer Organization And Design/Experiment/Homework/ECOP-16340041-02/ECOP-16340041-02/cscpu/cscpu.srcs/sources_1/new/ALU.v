`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:23:49
// Design Name: 
// Module Name: ALU
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


module ALU(
        input [2:0] ALUopcode,
        input [31:0] rega,
        input [31:0] regb,
        output zero,
        output reg [31:0] result,
        output sign
    );
    assign zero = (result==0)?1:0;
    assign sign = result[31];
    always @( ALUopcode or rega or regb ) begin
        case (ALUopcode)
        3'b000 : result = rega + regb;
        3'b001 : result = rega - regb;
        3'b010 : result = regb << rega;
        3'b011 : result = rega | regb;
        3'b100 : result = rega & regb;
        3'b101 : result = (rega < regb)?1:0; // �޷��űȽ�
        3'b110 : begin // �����űȽ�
        if (rega<regb &&( rega[31] == regb[31]) )
        result = 1;
        else if ( rega[31] == 1 && regb[31]==0) result = 1;
        else result = 0;
        end
        3'b111 : result = rega ^ regb;
        endcase
    end
endmodule
