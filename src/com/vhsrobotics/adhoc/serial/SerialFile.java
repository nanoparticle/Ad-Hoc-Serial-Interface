package com.vhsrobotics.adhoc.serial;

public class SerialFile {
	private String id;
	private String value;
	private SerialListener listener;
	
	public SerialFile (String identifier, Object defaultVal) {
		id = identifier;
		value = defaultVal.toString();
		SerialProcessor.addSerialFile(this);
	}
	
	public String getID () {
		return id;
	}
	
	public SerialFile set (Object o) {
		value = o.toString();
		if (listener != null)  listener.stateChanged(value);
		return this;
	}
	
	public String get () {
		return value;
	}
	
	public String getString () {
		return value;
	}
	public Double getDouble () {
		return Double.valueOf(value);
	}
	
	public int getInt () {
		return Integer.valueOf(value);
	}
	
	public boolean getBoolean () {
		return Boolean.valueOf(value);
	}
	
	public SerialFile setListener (SerialListener l) {
		listener = l;
		return this;
	}
	public SerialListener getListener () {
		return listener;
	}
}
