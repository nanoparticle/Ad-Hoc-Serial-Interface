package com.vhsrobotics.adhoc.serial;

public class Util {
	
	public static void decodeInput () {
		
	}
	
	public class Symbols {
		public static final char ETB = 0x17; // End-Of-Transmission-Block
		public static final char US = 0x1F; // Unit Seperator
		//https://en.wikipedia.org/wiki/C0_and_C1_control_codes
		//https://en.wikipedia.org/wiki/End-of-Transmission_character
		//https://en.wikipedia.org/wiki/End-of-Transmission-Block_character
		//https://en.wikipedia.org/wiki/Delimiter#ASCII_delimited_text
		//https://en.wikipedia.org/wiki/C0_and_C1_control_codes#Field_separators
	}
	
	public class MessageType {
		public static final String SYSTEM = "system";
		public static final String VARIABLE = "variable";
	}
}
