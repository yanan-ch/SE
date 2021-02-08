// (c) Copyright 1995-2017 Xilinx, Inc. All rights reserved.
// 
// This file contains confidential and proprietary information
// of Xilinx, Inc. and is protected under U.S. and
// international copyright and other intellectual property
// laws.
// 
// DISCLAIMER
// This disclaimer is not a license and does not grant any
// rights to the materials distributed herewith. Except as
// otherwise provided in a valid license issued to you by
// Xilinx, and to the maximum extent permitted by applicable
// law: (1) THESE MATERIALS ARE MADE AVAILABLE "AS IS" AND
// WITH ALL FAULTS, AND XILINX HEREBY DISCLAIMS ALL WARRANTIES
// AND CONDITIONS, EXPRESS, IMPLIED, OR STATUTORY, INCLUDING
// BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY, NON-
// INFRINGEMENT, OR FITNESS FOR ANY PARTICULAR PURPOSE; and
// (2) Xilinx shall not be liable (whether in contract or tort,
// including negligence, or under any other theory of
// liability) for any loss or damage of any kind or nature
// related to, arising under or in connection with these
// materials, including for any direct, or any indirect,
// special, incidental, or consequential loss or damage
// (including loss of data, profits, goodwill, or any type of
// loss or damage suffered as a result of any action brought
// by a third party) even if such damage or loss was
// reasonably foreseeable or Xilinx had been advised of the
// possibility of the same.
// 
// CRITICAL APPLICATIONS
// Xilinx products are not designed or intended to be fail-
// safe, or for use in any application requiring fail-safe
// performance, such as life-support or safety devices or
// systems, Class III medical devices, nuclear facilities,
// applications related to the deployment of airbags, or any
// other applications that could lead to death, personal
// injury, or severe property or environmental damage
// (individually and collectively, "Critical
// Applications"). Customer assumes the sole risk and
// liability of any use of Xilinx products in Critical
// Applications, subject only to applicable laws and
// regulations governing limitations on product liability.
// 
// THIS COPYRIGHT NOTICE AND DISCLAIMER MUST BE RETAINED AS
// PART OF THIS FILE AT ALL TIMES.
// 
// DO NOT MODIFY THIS FILE.


// IP VLNV: xilinx.com:XUP:decode138:1.0
// IP Revision: 2

(* X_CORE_INFO = "decode138,Vivado 2015.3" *)
(* CHECK_LICENSE_TYPE = "ALU_1_decode138_0_0,decode138,{}" *)
(* CORE_GENERATION_INFO = "ALU_1_decode138_0_0,decode138,{x_ipProduct=Vivado 2015.3,x_ipVendor=xilinx.com,x_ipLibrary=XUP,x_ipName=decode138,x_ipVersion=1.0,x_ipCoreRevision=2,x_ipLanguage=VERILOG,x_ipSimLanguage=MIXED}" *)
(* DowngradeIPIdentifiedWarnings = "yes" *)
module ALU_1_decode138_0_0 (
  A0,
  A1,
  A2,
  E1,
  E2_n,
  E3_n,
  Y0_n,
  Y1_n,
  Y2_n,
  Y3_n,
  Y4_n,
  Y5_n,
  Y6_n,
  Y7_n
);

input wire A0;
input wire A1;
input wire A2;
input wire E1;
input wire E2_n;
input wire E3_n;
output wire Y0_n;
output wire Y1_n;
output wire Y2_n;
output wire Y3_n;
output wire Y4_n;
output wire Y5_n;
output wire Y6_n;
output wire Y7_n;

  decode138 inst (
    .A0(A0),
    .A1(A1),
    .A2(A2),
    .E1(E1),
    .E2_n(E2_n),
    .E3_n(E3_n),
    .Y0_n(Y0_n),
    .Y1_n(Y1_n),
    .Y2_n(Y2_n),
    .Y3_n(Y3_n),
    .Y4_n(Y4_n),
    .Y5_n(Y5_n),
    .Y6_n(Y6_n),
    .Y7_n(Y7_n)
  );
endmodule
