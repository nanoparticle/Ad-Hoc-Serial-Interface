package com.vhsrobotics.adhoc.serial;

public class Sys {
	private static boolean isConnected = false;
	
	public static void connect () {
		Util.sendCommand(Command.CONNECT);
	}
	
	public static void runCommand (String c) {
		if (c.equalsIgnoreCase(Command.CONNECT)) {
			Util.sendCommand(Command.ACK);
			if (!isConnected) Util.sendCommand(Command.CONNECT);
		} else if (c.equalsIgnoreCase(Command.ACK)) {
			isConnected = true;
			SerialProcessor.sync();
		} else if (c.equalsIgnoreCase(Command.DISCONNECT)) {
			isConnected = false;
		} else {
			System.err.println("Invalid Command: " + c);
		}
	}
	
	public static boolean isConnected () {
		return isConnected;
	}
	
	public class Command {
		public static final String CONNECT = "connect";
		public static final String DISCONNECT = "disconnect";
		public static final String ACK = "ack"; //acknowledgement
	}
}
