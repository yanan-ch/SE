//Copyright 1986-2015 Xilinx, Inc. All Rights Reserved.
//--------------------------------------------------------------------------------
//Tool Version: Vivado v.2015.3 (win64) Build 1368829 Mon Sep 28 20:06:43 MDT 2015
//Date        : Tue Nov 14 11:36:33 2017
//Host        : USERCHI-NINI98 running 64-bit major release  (build 9200)
//Command     : generate_target ALU_1_wrapper.bd
//Design      : ALU_1_wrapper
//Purpose     : IP block netlist
//--------------------------------------------------------------------------------
`timescale 1 ps / 1 ps

module ALU_1_wrapper
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

  wire A;
  wire B;
  wire C;
  wire GND;
  wire POW;
  wire S0;
  wire S1;
  wire S2;
  wire Y1;
  wire Y2;

  ALU_1 ALU_1_i
       (.A(A),
        .B(B),
        .C(C),
        .GND(GND),
        .POW(POW),
        .S0(S0),
        .S1(S1),
        .S2(S2),
        .Y1(Y1),
        .Y2(Y2));
endmodule
