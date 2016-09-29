package com.vhsrobotics.adhoc.serial;

import java.util.ArrayList;
import java.util.List;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPort;

public class SerialProcessor {
	private static List<SerialFile> files;
	private static SerialPort serialPort;
	private static String input;
	static {
		files = new ArrayList<SerialFile>();
		input = new String();
		
		openSerialPort();
		attachShutdownHook();
		setSerialListener();
	}
	
	public static void addSerialFile (SerialFile s) {
		files.add(s);
	}
	
	private static void processIn(SerialPortEvent e) {
		String temp = "";
		try {
			temp = serialPort.readString(e.getEventValue());
		} catch (SerialPortException e1) {
			e1.printStackTrace();
		}
		if (temp.contains("\n")) {
			input += temp.substring(0, temp.indexOf("\n"));
			updateIn(input);
			input = temp.substring(temp.indexOf("\n") + 1);
		} else {
			input += temp;
		}
	}
	
	private static void updateIn (String in) {
		String name = in.split("|")[0];
		for (SerialFile f : files) if (f.getID().equals(name)) f.set(in.split("|")[1]);
	}
	
	private static void updateOut (SerialFile f) {
		try {
			serialPort.writeString(f.getID() + "|" + f.get() + "\n");
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sync () {
		for (SerialFile f : files) {
			updateOut(f);
		}
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
					processIn(serialPortEvent);
				}
			});
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
}
