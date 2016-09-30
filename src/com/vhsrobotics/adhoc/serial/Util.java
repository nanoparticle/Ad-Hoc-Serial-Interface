package com.vhsrobotics.adhoc.serial;

import jssc.SerialPortEvent;
import jssc.SerialPortException;

public class Util {
	
	private static void collectInput(SerialPortEvent e) {
		String temp = "";
		try {
			temp = serialPort.readString(e.getEventValue());
		} catch (SerialPortException e1) {
			e1.printStackTrace();
		}
		if (temp.contains(String.valueOf(Symbols.ETB))) {
			input += temp.substring(0, temp.indexOf("\n"));
			updateIn(input);
			input = temp.substring(temp.indexOf("\n") + 1);
		} else {
			input += temp;
		}
	}
	
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
