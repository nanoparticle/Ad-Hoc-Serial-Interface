package com.vhsrobotics.adhoc.serial;

import java.util.ArrayList;
import java.util.List;

public class SerialProcessor {
	private static List<SerialFile> files;
	
	static {
		files = new ArrayList<SerialFile>();
		
		Util.init();
	}
	
	public static void addSerialFile (SerialFile s) {
		files.add(s);
	}
	
	public static void updateIn (String in) {
		String name = in.split(String.valueOf(Util.Symbol.US))[0];
		for (SerialFile f : files) if (f.getID().equals(name)) f.updateIn(in.split(String.valueOf(Util.Symbol.US))[1]);
	}
	
	public static void updateOut (SerialFile f) {
		if (Sys.isConnected()) Util.sendValue(f);
	}
	
	public static void sync () {
		for (SerialFile f : files) updateOut(f);
	}
	
	public static void connect () {
		Sys.connect();
	}
	
	public static void disconnect () {
		Sys.disconnect();
	}
}
