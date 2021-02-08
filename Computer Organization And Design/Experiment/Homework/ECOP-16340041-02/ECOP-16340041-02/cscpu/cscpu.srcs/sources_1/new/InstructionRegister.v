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
    ); // 指令存储器模块，只读，用来存储指令
    // rd 读使能信号
    // iaddr 指令存储器指令地址输入端口
    // dataOut 指令存储器指令输出端口
    reg [31:0] dataOut;
    reg [7:0] rom [99:0]; // 存储器定义必须用reg类型，存储器存储单元8位长度，共100个存储单元
    initial begin // 加载数据到存储器rom，注意：必须使用绝对路径
        $readmemb ("D:/SingleCycleCPU/rom_data.txt", rom);
    end

    always @(rd or iaddr) begin
        if (rd == 0) begin // 为0，读存储器写入dataOut。大端数据存储模式
        // 数据的高字节保存在存储器的低地址中
            dataOut[31:24] = rom[iaddr];
            dataOut[23:16] = rom[iaddr+1];
            dataOut[15:8] = rom[iaddr+2];
            dataOut[7:0] = rom[iaddr+3];
        end
    end
endmodule
