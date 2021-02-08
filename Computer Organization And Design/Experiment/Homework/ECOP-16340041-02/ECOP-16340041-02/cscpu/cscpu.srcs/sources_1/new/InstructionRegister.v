`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2018/05/28 20:05:39
// Design Name: 
// Module Name: InstructionRegister
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


module InstructionRegister(
    input rd, input [31:0] iaddr, output reg [31:0] dataOut
    ); // ָ��洢��ģ�飬ֻ���������洢ָ��
    // rd ��ʹ���ź�
    // iaddr ָ��洢��ָ���ַ����˿�
    // dataOut ָ��洢��ָ������˿�
    reg [31:0] dataOut;
    reg [7:0] rom [99:0]; // �洢�����������reg���ͣ��洢���洢��Ԫ8λ���ȣ���100���洢��Ԫ
    initial begin // �������ݵ��洢��rom��ע�⣺����ʹ�þ���·��
        $readmemb ("D:/SingleCycleCPU/rom_data.txt", rom);
    end

    always @(rd or iaddr) begin
        if (rd == 0) begin // Ϊ0�����洢��д��dataOut��������ݴ洢ģʽ
        // ���ݵĸ��ֽڱ����ڴ洢���ĵ͵�ַ��
            dataOut[31:24] = rom[iaddr];
            dataOut[23:16] = rom[iaddr+1];
            dataOut[15:8] = rom[iaddr+2];
            dataOut[7:0] = rom[iaddr+3];
        end
    end
endmodule
