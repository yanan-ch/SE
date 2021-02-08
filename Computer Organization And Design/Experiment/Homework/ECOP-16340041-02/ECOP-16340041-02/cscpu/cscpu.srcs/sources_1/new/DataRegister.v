`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/29 01:19:59
// Design Name: 
// Module Name: DataRegister
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


module DataRegister(
    input [31:0] address,
    input [31:0] writeData, // [31:24], [23:16], [15:8], [7:0]
    input nRD, // Ϊ1����������Ϊ0,�������̬
    input nWR, // Ϊ1��д��Ϊ0���޲���
    input clk,
    output wire [31:0] Dataout
    );
    reg [7:0] ram [0:60]; // �洢�����������reg����
    // ��
    assign Dataout[7:0] = (nRD==1)?ram[address + 3]:8'bz; // z Ϊ����̬
    assign Dataout[15:8] = (nRD==1)?ram[address + 2]:8'bz;
    assign Dataout[23:16] = (nRD==1)?ram[address + 1]:8'bz;
    assign Dataout[31:24] = (nRD==1)?ram[address ]:8'bz;
    // д
    always@( negedge clk ) begin // �õ�ƽ�źŴ���д�洢��������
    if( nWR==1 ) begin
    ram[address] = writeData[31:24];
    ram[address+1] = writeData[23:16];
    ram[address+2] = writeData[15:8];
    ram[address+3] = writeData[7:0];
    end
    end
endmodule
