//Copyright 1986-2015 Xilinx, Inc. All Rights Reserved.
//--------------------------------------------------------------------------------
//Tool Version: Vivado v.2015.3 (win64) Build 1368829 Mon Sep 28 20:06:43 MDT 2015
//Date        : Tue Nov 14 11:36:33 2017
//Host        : USERCHI-NINI98 running 64-bit major release  (build 9200)
//Command     : generate_target ALU_1.bd
//Design      : ALU_1
//Purpose     : IP block netlist
//--------------------------------------------------------------------------------
`timescale 1 ps / 1 ps

(* CORE_GENERATION_INFO = "ALU_1,IP_Integrator,{x_ipVendor=xilinx.com,x_ipLibrary=BlockDiagram,x_ipName=ALU_1,x_ipVersion=1.00.a,x_ipLanguage=VERILOG,numBlks=9,numReposBlks=9,numNonXlnxBlks=0,numHierBlks=0,maxHierDepth=0,synth_mode=Global}" *) (* HW_HANDOFF = "ALU_1.hwdef" *) 
module ALU_1
   (A,
    B,
    C,
    GND,
    POW,
    S0,
    S1,
    S2,
    Y1,
    Y2);
  input A;
  input B;
  input C;
  input GND;
  input POW;
  input S0;
  input S1;
  input S2;
  output Y1;
  output Y2;

  wire A_1;
  wire B_1;
  wire C_1;
  wire GND_1;
  wire POW_1;
  wire S0_1;
  wire S1_1;
  wire S2_1;
  wire decode138_0_Y1_n;
  wire decode138_0_Y2_n;
  wire decode138_0_Y3_n;
  wire decode138_0_Y4_n;
  wire decode138_0_Y5_n;
  wire decode138_0_Y6_n;
  wire decode138_0_Y7_n;
  wire four_2_input_and_gate_0_Y1;
  wire four_2_input_and_gate_0_Y2;
  wire four_2_input_and_gate_0_Y3;
  wire four_2_input_or_gate_0_Y1;
  wire four_2_input_or_gate_0_Y2;
  wire mux_8_to_1_0_Q;
  wire mux_8_to_1_0_Q_n;
  wire mux_8_to_1_1_Q;
  wire six_not_gate_0_Y1;
  wire six_not_gate_0_Y2;
  wire xup_nand4_0_y;
  wire xup_nand4_1_y;
  wire xup_nand4_2_y;

  assign A_1 = A;
  assign B_1 = B;
  assign C_1 = C;
  assign GND_1 = GND;
  assign POW_1 = POW;
  assign S0_1 = S0;
  assign S1_1 = S1;
  assign S2_1 = S2;
  assign Y1 = mux_8_to_1_0_Q;
  assign Y2 = mux_8_to_1_1_Q;
  ALU_1_decode138_0_0 decode138_0
       (.A0(A_1),
        .A1(B_1),
        .A2(C_1),
        .E1(POW_1),
        .E2_n(GND_1),
        .E3_n(GND_1),
        .Y1_n(decode138_0_Y1_n),
        .Y2_n(decode138_0_Y2_n),
        .Y3_n(decode138_0_Y3_n),
        .Y4_n(decode138_0_Y4_n),
        .Y5_n(decode138_0_Y5_n),
        .Y6_n(decode138_0_Y6_n),
        .Y7_n(decode138_0_Y7_n));
  ALU_1_four_2_input_and_gate_0_0 four_2_input_and_gate_0
       (.A1(A_1),
        .A2(A_1),
        .A3(six_not_gate_0_Y1),
        .A4(GND_1),
        .B1(B_1),
        .B2(six_not_gate_0_Y2),
        .B3(B_1),
        .B4(GND_1),
        .Y1(four_2_input_and_gate_0_Y1),
        .Y2(four_2_input_and_gate_0_Y2),
        .Y3(four_2_input_and_gate_0_Y3));
  ALU_1_four_2_input_or_gate_0_0 four_2_input_or_gate_0
       (.A1(A_1),
        .A2(four_2_input_and_gate_0_Y2),
        .A3(GND_1),
        .A4(GND_1),
        .B1(B_1),
        .B2(four_2_input_and_gate_0_Y3),
        .B3(GND_1),
        .B4(GND_1),
        .Y1(four_2_input_or_gate_0_Y1),
        .Y2(four_2_input_or_gate_0_Y2));
  ALU_1_mux_8_to_1_0_0 mux_8_to_1_0
       (.A0(S0_1),
        .A1(S1_1),
        .A2(S2_1),
        .D0(four_2_input_and_gate_0_Y1),
        .D1(four_2_input_or_gate_0_Y1),
        .D2(six_not_gate_0_Y1),
        .D3(six_not_gate_0_Y2),
        .D4(four_2_input_or_gate_0_Y2),
        .D5(xup_nand4_0_y),
        .D6(xup_nand4_0_y),
        .D7(GND_1),
        .Q(mux_8_to_1_0_Q),
        .Q_n(mux_8_to_1_0_Q_n),
        .S_n(GND_1));
  ALU_1_mux_8_to_1_1_0 mux_8_to_1_1
       (.A0(S0_1),
        .A1(S1_1),
        .A2(S2_1),
        .D0(mux_8_to_1_0_Q_n),
        .D1(mux_8_to_1_0_Q_n),
        .D2(mux_8_to_1_0_Q_n),
        .D3(mux_8_to_1_0_Q_n),
        .D4(mux_8_to_1_0_Q_n),
        .D5(xup_nand4_1_y),
        .D6(xup_nand4_2_y),
        .D7(GND_1),
        .Q(mux_8_to_1_1_Q),
        .S_n(GND_1));
  ALU_1_six_not_gate_0_0 six_not_gate_0
       (.A1(A_1),
        .A2(B_1),
        .A3(GND_1),
        .A4(GND_1),
        .A5(GND_1),
        .A6(GND_1),
        .Y1(six_not_gate_0_Y1),
        .Y2(six_not_gate_0_Y2));
  ALU_1_xup_nand4_0_0 xup_nand4_0
       (.a(decode138_0_Y1_n),
        .b(decode138_0_Y2_n),
        .c(decode138_0_Y4_n),
        .d(decode138_0_Y7_n),
        .y(xup_nand4_0_y));
  ALU_1_xup_nand4_1_0 xup_nand4_1
       (.a(decode138_0_Y3_n),
        .b(decode138_0_Y5_n),
        .c(decode138_0_Y6_n),
        .d(decode138_0_Y7_n),
        .y(xup_nand4_1_y));
  ALU_1_xup_nand4_2_0 xup_nand4_2
       (.a(decode138_0_Y1_n),
        .b(decode138_0_Y2_n),
        .c(decode138_0_Y3_n),
        .d(decode138_0_Y7_n),
        .y(xup_nand4_2_y));
endmodule
