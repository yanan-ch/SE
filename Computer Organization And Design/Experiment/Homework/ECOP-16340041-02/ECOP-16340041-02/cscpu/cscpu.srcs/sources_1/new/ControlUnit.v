`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/28 22:09:16
// Design Name: 
// Module Name: ControlUnit
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


module ControlUnit(
    input zero, // 判断beq,bne是否跳转
    input [5:0] curOP, // 当前操作码
    output reg PCWre, //与PC更改有关
    output reg ALUSrcA, 
    output reg ALUSrcB,
    output reg DBDataSrc,
    output reg InsMemRW,
    output reg RegWre,
    output reg nRD,
    output reg nWR,
    output reg [2:0] ALUOp,
    output reg [1:0] PCSrc,
    output reg ExtSel,
    output reg RegDst
    );
    //
    initial begin
        InsMemRW = 0;
    end
    always @(curOP or zero) begin
        //初始化
        PCSrc = 2'b00;
        PCWre = 1;
        ALUSrcA = 0;
        ALUSrcB = 0;
        DBDataSrc = 0;
        InsMemRW = 0;
        nRD = 0;
        nWR = 0;
        ExtSel = 1;
        PCWre = 1;
        RegDst = 1;
        ALUOp = 3'b000;
        RegWre = 1;
        case (curOP)
            6'b000000 : begin //add
                RegWre = 1;
                RegDst = 1;
                ALUOp = 3'b000;
            end
            6'b000001 : begin //addi
                RegWre = 1;
                RegDst = 0;
                ALUSrcB = 1;
                ALUOp = 3'b000;
                end
            6'b000010 : begin //sub          
                RegDst = 1;
                RegWre = 1;
                ALUOp = 3'b001;
                end
            6'b010000 : begin //ori
                ALUSrcB = 1;
                RegWre = 1;
                RegDst = 0;
                ExtSel = 0;
                ALUOp = 3'b011;
                end
            6'b010001 : begin //and
                RegWre = 1;
                RegDst = 1;
                ALUOp = 3'b100;
                end
            6'b010010 : begin//or
                RegWre = 1;
                RegDst = 1;
                ALUOp = 3'b011;
                end
            6'b011000 : begin //sll
                ALUSrcA = 1;
                RegWre = 1;
                RegDst = 1;
                ALUOp = 3'b010;
                end
            6'b011011 : begin //slti
                RegWre = 1;
                RegDst = 1;
                ALUOp = 3'b110;
                end
            6'b100110 : begin //sw
                ALUSrcB = 1;
                RegWre = 0;
                nWR = 1;
                ALUOp = 3'b000;
                end
            6'b100111 : begin //lw
                ALUSrcB = 1;
                DBDataSrc = 1;
                RegWre = 1;
                nRD = 1;
                RegDst = 0;
                ALUOp = 3'b000;
                end
            6'b110000 : begin //beq
                RegWre = 0;
                PCSrc = (zero)? 2'b01: 2'b00;
                ALUOp = 3'b001;
                end
            6'b110001 : begin //bne
                RegWre = 0;
                PCSrc = (zero)? 2'b00: 2'b01;
                ALUOp = 3'b001;
                end
            6'b111000 : begin //j
                RegWre = 0;
                PCSrc = 2'b10;
                end
            6'b111111 : begin //halt
                PCWre = 0;
                RegWre = 0;
                end
            default : begin
                RegWre = 0;
                PCWre = 0;
                end 
        endcase
    end
endmodule
