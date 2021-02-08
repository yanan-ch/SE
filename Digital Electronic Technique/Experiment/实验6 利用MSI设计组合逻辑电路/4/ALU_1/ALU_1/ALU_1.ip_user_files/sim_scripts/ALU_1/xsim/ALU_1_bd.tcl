
################################################################
# This is a generated script based on design: ALU_1
#
# Though there are limitations about the generated script,
# the main purpose of this utility is to make learning
# IP Integrator Tcl commands easier.
################################################################

################################################################
# Check if script is running in correct Vivado version.
################################################################
set scripts_vivado_version 2015.3
set current_vivado_version [version -short]

if { [string first $scripts_vivado_version $current_vivado_version] == -1 } {
   puts ""
   puts "ERROR: This script was generated using Vivado <$scripts_vivado_version> and is being run in <$current_vivado_version> of Vivado. Please run the script in Vivado <$scripts_vivado_version> then open the design in Vivado <$current_vivado_version>. Upgrade the design by running \"Tools => Report => Report IP Status...\", then run write_bd_tcl to create an updated script."

   return 1
}

################################################################
# START
################################################################

# To test this script, run the following commands from Vivado Tcl console:
# source ALU_1_script.tcl

# If you do not already have a project created,
# you can create a project using the following command:
#    create_project project_1 myproj -part xc7a35tcpg236-1

# CHECKING IF PROJECT EXISTS
if { [get_projects -quiet] eq "" } {
   puts "ERROR: Please open or create a project!"
   return 1
}



# CHANGE DESIGN NAME HERE
set design_name ALU_1

# If you do not already have an existing IP Integrator design open,
# you can create a design using the following command:
#    create_bd_design $design_name

# Creating design if needed
set errMsg ""
set nRet 0

set cur_design [current_bd_design -quiet]
set list_cells [get_bd_cells -quiet]

if { ${design_name} eq "" } {
   # USE CASES:
   #    1) Design_name not set

   set errMsg "ERROR: Please set the variable <design_name> to a non-empty value."
   set nRet 1

} elseif { ${cur_design} ne "" && ${list_cells} eq "" } {
   # USE CASES:
   #    2): Current design opened AND is empty AND names same.
   #    3): Current design opened AND is empty AND names diff; design_name NOT in project.
   #    4): Current design opened AND is empty AND names diff; design_name exists in project.

   if { $cur_design ne $design_name } {
      puts "INFO: Changing value of <design_name> from <$design_name> to <$cur_design> since current design is empty."
      set design_name [get_property NAME $cur_design]
   }
   puts "INFO: Constructing design in IPI design <$cur_design>..."

} elseif { ${cur_design} ne "" && $list_cells ne "" && $cur_design eq $design_name } {
   # USE CASES:
   #    5) Current design opened AND has components AND same names.

   set errMsg "ERROR: Design <$design_name> already exists in your project, please set the variable <design_name> to another value."
   set nRet 1
} elseif { [get_files -quiet ${design_name}.bd] ne "" } {
   # USE CASES: 
   #    6) Current opened design, has components, but diff names, design_name exists in project.
   #    7) No opened design, design_name exists in project.

   set errMsg "ERROR: Design <$design_name> already exists in your project, please set the variable <design_name> to another value."
   set nRet 2

} else {
   # USE CASES:
   #    8) No opened design, design_name not in project.
   #    9) Current opened design, has components, but diff names, design_name not in project.

   puts "INFO: Currently there is no design <$design_name> in project, so creating one..."

   create_bd_design $design_name

   puts "INFO: Making design <$design_name> as current_bd_design."
   current_bd_design $design_name

}

puts "INFO: Currently the variable <design_name> is equal to \"$design_name\"."

if { $nRet != 0 } {
   puts $errMsg
   return $nRet
}

##################################################################
# DESIGN PROCs
##################################################################



# Procedure to create entire design; Provide argument to make
# procedure reusable. If parentCell is "", will use root.
proc create_root_design { parentCell } {

  if { $parentCell eq "" } {
     set parentCell [get_bd_cells /]
  }

  # Get object for parentCell
  set parentObj [get_bd_cells $parentCell]
  if { $parentObj == "" } {
     puts "ERROR: Unable to find parent cell <$parentCell>!"
     return
  }

  # Make sure parentObj is hier blk
  set parentType [get_property TYPE $parentObj]
  if { $parentType ne "hier" } {
     puts "ERROR: Parent <$parentObj> has TYPE = <$parentType>. Expected to be <hier>."
     return
  }

  # Save current instance; Restore later
  set oldCurInst [current_bd_instance .]

  # Set parent object as current
  current_bd_instance $parentObj


  # Create interface ports

  # Create ports
  set A [ create_bd_port -dir I -type data A ]
  set B [ create_bd_port -dir I -type data B ]
  set C [ create_bd_port -dir I -type data C ]
  set GND [ create_bd_port -dir I -type data GND ]
  set POW [ create_bd_port -dir I -type data POW ]
  set S0 [ create_bd_port -dir I -type data S0 ]
  set S1 [ create_bd_port -dir I -type data S1 ]
  set S2 [ create_bd_port -dir I -type data S2 ]
  set Y1 [ create_bd_port -dir O -type data Y1 ]
  set Y2 [ create_bd_port -dir O -type data Y2 ]

  # Create instance: decode138_0, and set properties
  set decode138_0 [ create_bd_cell -type ip -vlnv xilinx.com:XUP:decode138:1.0 decode138_0 ]

  # Create instance: four_2_input_and_gate_0, and set properties
  set four_2_input_and_gate_0 [ create_bd_cell -type ip -vlnv xilinx.com:XUP:four_2_input_and_gate:1.0 four_2_input_and_gate_0 ]

  # Create instance: four_2_input_or_gate_0, and set properties
  set four_2_input_or_gate_0 [ create_bd_cell -type ip -vlnv xilinx.com:XUP:four_2_input_or_gate:1.0 four_2_input_or_gate_0 ]

  # Create instance: mux_8_to_1_0, and set properties
  set mux_8_to_1_0 [ create_bd_cell -type ip -vlnv xilinx.com:xup:mux_8_to_1:1.0 mux_8_to_1_0 ]

  # Create instance: mux_8_to_1_1, and set properties
  set mux_8_to_1_1 [ create_bd_cell -type ip -vlnv xilinx.com:xup:mux_8_to_1:1.0 mux_8_to_1_1 ]

  # Create instance: six_not_gate_0, and set properties
  set six_not_gate_0 [ create_bd_cell -type ip -vlnv xilinx.com:XUP:six_not_gate:1.0 six_not_gate_0 ]

  # Create instance: xup_nand4_0, and set properties
  set xup_nand4_0 [ create_bd_cell -type ip -vlnv xilinx.com:xup:xup_nand4:1.0 xup_nand4_0 ]

  # Create instance: xup_nand4_1, and set properties
  set xup_nand4_1 [ create_bd_cell -type ip -vlnv xilinx.com:xup:xup_nand4:1.0 xup_nand4_1 ]

  # Create instance: xup_nand4_2, and set properties
  set xup_nand4_2 [ create_bd_cell -type ip -vlnv xilinx.com:xup:xup_nand4:1.0 xup_nand4_2 ]

  # Create port connections
  connect_bd_net -net A_1 [get_bd_ports A] [get_bd_pins decode138_0/A0] [get_bd_pins four_2_input_and_gate_0/A1] [get_bd_pins four_2_input_and_gate_0/A2] [get_bd_pins four_2_input_or_gate_0/A1] [get_bd_pins six_not_gate_0/A1]
  connect_bd_net -net B_1 [get_bd_ports B] [get_bd_pins decode138_0/A1] [get_bd_pins four_2_input_and_gate_0/B1] [get_bd_pins four_2_input_and_gate_0/B3] [get_bd_pins four_2_input_or_gate_0/B1] [get_bd_pins six_not_gate_0/A2]
  connect_bd_net -net C_1 [get_bd_ports C] [get_bd_pins decode138_0/A2]
  connect_bd_net -net GND_1 [get_bd_ports GND] [get_bd_pins decode138_0/E2_n] [get_bd_pins decode138_0/E3_n] [get_bd_pins four_2_input_and_gate_0/A4] [get_bd_pins four_2_input_and_gate_0/B4] [get_bd_pins four_2_input_or_gate_0/A3] [get_bd_pins four_2_input_or_gate_0/A4] [get_bd_pins four_2_input_or_gate_0/B3] [get_bd_pins four_2_input_or_gate_0/B4] [get_bd_pins mux_8_to_1_0/D7] [get_bd_pins mux_8_to_1_0/S_n] [get_bd_pins mux_8_to_1_1/D7] [get_bd_pins mux_8_to_1_1/S_n] [get_bd_pins six_not_gate_0/A3] [get_bd_pins six_not_gate_0/A4] [get_bd_pins six_not_gate_0/A5] [get_bd_pins six_not_gate_0/A6]
  connect_bd_net -net POW_1 [get_bd_ports POW] [get_bd_pins decode138_0/E1]
  connect_bd_net -net S0_1 [get_bd_ports S0] [get_bd_pins mux_8_to_1_0/A0] [get_bd_pins mux_8_to_1_1/A0]
  connect_bd_net -net S1_1 [get_bd_ports S1] [get_bd_pins mux_8_to_1_0/A1] [get_bd_pins mux_8_to_1_1/A1]
  connect_bd_net -net S2_1 [get_bd_ports S2] [get_bd_pins mux_8_to_1_0/A2] [get_bd_pins mux_8_to_1_1/A2]
  connect_bd_net -net decode138_0_Y1_n [get_bd_pins decode138_0/Y1_n] [get_bd_pins xup_nand4_0/a] [get_bd_pins xup_nand4_2/a]
  connect_bd_net -net decode138_0_Y2_n [get_bd_pins decode138_0/Y2_n] [get_bd_pins xup_nand4_0/b] [get_bd_pins xup_nand4_2/b]
  connect_bd_net -net decode138_0_Y3_n [get_bd_pins decode138_0/Y3_n] [get_bd_pins xup_nand4_1/a] [get_bd_pins xup_nand4_2/c]
  connect_bd_net -net decode138_0_Y4_n [get_bd_pins decode138_0/Y4_n] [get_bd_pins xup_nand4_0/c]
  connect_bd_net -net decode138_0_Y5_n [get_bd_pins decode138_0/Y5_n] [get_bd_pins xup_nand4_1/b]
  connect_bd_net -net decode138_0_Y6_n [get_bd_pins decode138_0/Y6_n] [get_bd_pins xup_nand4_1/c]
  connect_bd_net -net decode138_0_Y7_n [get_bd_pins decode138_0/Y7_n] [get_bd_pins xup_nand4_0/d] [get_bd_pins xup_nand4_1/d] [get_bd_pins xup_nand4_2/d]
  connect_bd_net -net four_2_input_and_gate_0_Y1 [get_bd_pins four_2_input_and_gate_0/Y1] [get_bd_pins mux_8_to_1_0/D0]
  connect_bd_net -net four_2_input_and_gate_0_Y2 [get_bd_pins four_2_input_and_gate_0/Y2] [get_bd_pins four_2_input_or_gate_0/A2]
  connect_bd_net -net four_2_input_and_gate_0_Y3 [get_bd_pins four_2_input_and_gate_0/Y3] [get_bd_pins four_2_input_or_gate_0/B2]
  connect_bd_net -net four_2_input_or_gate_0_Y1 [get_bd_pins four_2_input_or_gate_0/Y1] [get_bd_pins mux_8_to_1_0/D1]
  connect_bd_net -net four_2_input_or_gate_0_Y2 [get_bd_pins four_2_input_or_gate_0/Y2] [get_bd_pins mux_8_to_1_0/D4]
  connect_bd_net -net mux_8_to_1_0_Q [get_bd_ports Y1] [get_bd_pins mux_8_to_1_0/Q]
  connect_bd_net -net mux_8_to_1_0_Q_n [get_bd_pins mux_8_to_1_0/Q_n] [get_bd_pins mux_8_to_1_1/D0] [get_bd_pins mux_8_to_1_1/D1] [get_bd_pins mux_8_to_1_1/D2] [get_bd_pins mux_8_to_1_1/D3] [get_bd_pins mux_8_to_1_1/D4]
  connect_bd_net -net mux_8_to_1_1_Q [get_bd_ports Y2] [get_bd_pins mux_8_to_1_1/Q]
  connect_bd_net -net six_not_gate_0_Y1 [get_bd_pins four_2_input_and_gate_0/A3] [get_bd_pins mux_8_to_1_0/D2] [get_bd_pins six_not_gate_0/Y1]
  connect_bd_net -net six_not_gate_0_Y2 [get_bd_pins four_2_input_and_gate_0/B2] [get_bd_pins mux_8_to_1_0/D3] [get_bd_pins six_not_gate_0/Y2]
  connect_bd_net -net xup_nand4_0_y [get_bd_pins mux_8_to_1_0/D5] [get_bd_pins mux_8_to_1_0/D6] [get_bd_pins xup_nand4_0/y]
  connect_bd_net -net xup_nand4_1_y [get_bd_pins mux_8_to_1_1/D5] [get_bd_pins xup_nand4_1/y]
  connect_bd_net -net xup_nand4_2_y [get_bd_pins mux_8_to_1_1/D6] [get_bd_pins xup_nand4_2/y]

  # Create address segments

  # Perform GUI Layout
  regenerate_bd_layout -layout_string {
   guistr: "# # String gsaved with Nlview 6.5.5  2015-06-26 bk=1.3371 VDI=38 GEI=35 GUI=JA:1.6
#  -string -flagsOSRD
preplace port A -pg 1 -y 390 -defaultsOSRD
preplace port B -pg 1 -y 410 -defaultsOSRD
preplace port C -pg 1 -y 210 -defaultsOSRD
preplace port GND -pg 1 -y 430 -defaultsOSRD
preplace port Y1 -pg 1 -y 320 -defaultsOSRD
preplace port Y2 -pg 1 -y 470 -defaultsOSRD
preplace port S0 -pg 1 -y 640 -defaultsOSRD
preplace port S1 -pg 1 -y 620 -defaultsOSRD
preplace port POW -pg 1 -y 230 -defaultsOSRD
preplace port S2 -pg 1 -y 600 -defaultsOSRD
preplace inst four_2_input_or_gate_0 -pg 1 -lvl 3 -y 480 -defaultsOSRD
preplace inst four_2_input_and_gate_0 -pg 1 -lvl 2 -y 460 -defaultsOSRD
preplace inst xup_nand4_0 -pg 1 -lvl 3 -y 300 -defaultsOSRD
preplace inst mux_8_to_1_0 -pg 1 -lvl 4 -y 580 -defaultsOSRD
preplace inst xup_nand4_1 -pg 1 -lvl 4 -y 210 -defaultsOSRD
preplace inst six_not_gate_0 -pg 1 -lvl 1 -y 510 -defaultsOSRD
preplace inst mux_8_to_1_1 -pg 1 -lvl 5 -y 480 -defaultsOSRD
preplace inst xup_nand4_2 -pg 1 -lvl 4 -y 70 -defaultsOSRD
preplace inst decode138_0 -pg 1 -lvl 2 -y 220 -defaultsOSRD
preplace netloc xup_nand4_0_y 1 3 1 710
preplace netloc decode138_0_Y7_n 1 2 2 410 220 710
preplace netloc decode138_0_Y2_n 1 2 2 420 60 NJ
preplace netloc four_2_input_or_gate_0_Y1 1 3 1 690
preplace netloc A_1 1 0 3 20 390 180 340 440
preplace netloc four_2_input_or_gate_0_Y2 1 3 1 680
preplace netloc mux_8_to_1_1_Q 1 5 1 NJ
preplace netloc S0_1 1 0 5 NJ 630 NJ 630 NJ 630 730 420 NJ
preplace netloc decode138_0_Y6_n 1 2 2 450 210 NJ
preplace netloc mux_8_to_1_0_Q 1 4 2 NJ 320 NJ
preplace netloc GND_1 1 0 5 40 410 190 590 460 600 700 380 940
preplace netloc mux_8_to_1_0_Q_n 1 4 1 930
preplace netloc six_not_gate_0_Y1 1 1 3 180 650 NJ 650 NJ
preplace netloc decode138_0_Y5_n 1 2 2 440 200 NJ
preplace netloc six_not_gate_0_Y2 1 1 3 210 640 NJ 640 NJ
preplace netloc four_2_input_and_gate_0_Y1 1 2 2 420 690 NJ
preplace netloc S1_1 1 0 5 NJ 620 NJ 620 NJ 620 720 410 NJ
preplace netloc four_2_input_and_gate_0_Y2 1 2 1 N
preplace netloc xup_nand4_1_y 1 4 1 900
preplace netloc four_2_input_and_gate_0_Y3 1 2 1 N
preplace netloc S2_1 1 0 5 NJ 610 NJ 610 NJ 610 670 390 NJ
preplace netloc xup_nand4_2_y 1 4 1 930
preplace netloc decode138_0_Y1_n 1 2 2 470 40 NJ
preplace netloc B_1 1 0 3 30 400 200 580 NJ
preplace netloc decode138_0_Y4_n 1 2 1 460
preplace netloc POW_1 1 0 2 NJ 230 NJ
preplace netloc decode138_0_Y3_n 1 2 2 430 190 670
preplace netloc C_1 1 0 2 NJ 210 NJ
levelinfo -pg 1 0 110 310 570 820 1010 1100 -top 0 -bot 740
",
}

  # Restore current instance
  current_bd_instance $oldCurInst

  save_bd_design
}
# End of create_root_design()


##################################################################
# MAIN FLOW
##################################################################

create_root_design ""


