package com.vhsrobotics.adhoc.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Util {
	
	private static String input;
	private static SerialPort serialPort;
	
	public static void init() {
		input = "";
		
		openSerialPort();
		attachShutdownHook();
		setSerialListener();
		
		Sys.connect();
	}
	
	private static void collectInput(SerialPortEvent e) {
		String temp = "";
		try {
			temp = serialPort.readString(e.getEventValue());
		} catch (SerialPortException e1) {
			e1.printStackTrace();
		}
		while (temp.indexOf(Symbol.ETB) >= 0) {
			input += temp.substring(0, temp.indexOf(Symbol.ETB));
			decodeInput(input);
			input = "";
			temp = temp.substring(temp.indexOf(Symbol.ETB) + 1);
		}
		input += temp;
	}
	
	private static void decodeInput (String input) {
		String temp = input.substring(0, input.indexOf(Symbol.US));
		if (temp.equalsIgnoreCase(MessageType.SYSTEM)) {
			Sys.runCommand(input.substring(input.indexOf(Symbol.US) + 1));
		} else if (temp.equalsIgnoreCase(MessageType.VARIABLE)) {
			SerialProcessor.updateIn(input.substring(input.indexOf(Symbol.US) + 1));
		} else {
			System.err.println("Invalid Message: " + input);
		}
	}
	
	public static void sendValue (SerialFile f) {
		try {
			serialPort.writeString(MessageType.VARIABLE + Symbol.US + f.getID() + Symbol.US + f.get() + Symbol.ETB);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendCommand (String c) {
		try {
			serialPort.writeString(MessageType.SYSTEM + Symbol.US + c + Symbol.ETB);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
	public class Symbol {
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
	
	private static void openSerialPort () {
		serialPort = new SerialPort(SerialPortList.getPortNames()[0]);
		try {
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_115200, 
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
		System.out.println("Serial port at " + serialPort.getPortName() + " is opened: " + serialPort.isOpened());
	}
	
	private static void attachShutdownHook () {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (serialPort != null) {
					try {
						serialPort.closePort();
						System.out.println("Serial port closed.");
					} catch (SerialPortException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	private static void setSerialListener () {
		try {
			serialPort.addEventListener(new SerialPortEventListener() {
				public void serialEvent(SerialPortEvent serialPortEvent) {
					collectInput(serialPortEvent);
				}
			});
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
}
