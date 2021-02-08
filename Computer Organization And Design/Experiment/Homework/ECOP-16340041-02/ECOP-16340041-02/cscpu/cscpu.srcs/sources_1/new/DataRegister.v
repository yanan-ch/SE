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
    input nRD, // 为1，正常读；为0,输出高组态
    input nWR, // 为1，写；为0，无操作
    input clk,
    output wire [31:0] Dataout
    );
    reg [7:0] ram [0:60]; // 存储器定义必须用reg类型
    // 读
    assign Dataout[7:0] = (nRD==1)?ram[address + 3]:8'bz; // z 为高阻态
    assign Dataout[15:8] = (nRD==1)?ram[address + 2]:8'bz;
    assign Dataout[23:16] = (nRD==1)?ram[address + 1]:8'bz;
    assign Dataout[31:24] = (nRD==1)?ram[address ]:8'bz;
    // 写
    always@( negedge clk ) begin // 用电平信号触发写存储器，个例
    if( nWR==1 ) begin
    ram[address] = writeData[31:24];
    ram[address+1] = writeData[23:16];
    ram[address+2] = writeData[15:8];
    ram[address+3] = writeData[7:0];
    end
    end
endmodule
