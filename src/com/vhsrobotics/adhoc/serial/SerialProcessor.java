package com.vhsrobotics.adhoc.serial;

import java.util.ArrayList;
import java.util.List;

import jssc.src.java.jssc.SerialPortEvent;
import jssc.src.java.jssc.SerialPortEventListener;
import jssc.src.java.jssc.SerialPortException;
import jssc.src.java.jssc.SerialPortList;
import jssc.src.java.jssc.SerialPort;

public class SerialProcessor {
	private static List<SerialFile> files;
	private static SerialPort serialPort;
	static {
		files = new ArrayList<SerialFile>();
		
		openSerialPort();
		attachShutdownHook();
		setSerialListener();
	}
	
	public static void addSerialFile (SerialFile s) {
		files.add(s);
	}
	
	public static void stateChanged (SerialPortEvent e) {
		String temp = "";
		String name;
		try {
			temp = serialPort.readString(e.getEventValue());
			name = temp.split("|")[0];
			for (SerialFile f : files) if (f.getID().equals(name)) f.set(temp.split("|")[1]);
		} catch (SerialPortException e1) {
			e1.printStackTrace();
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
					stateChanged(serialPortEvent);
				}
			});
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
}
