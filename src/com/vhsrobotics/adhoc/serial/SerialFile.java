package com.vhsrobotics.adhoc.serial;

public class SerialFile {
	private String id;
	private volatile String value;
	private SerialListener listener;
	private boolean beenSet = false;
	
	public SerialFile (String identifier, Object defaultVal) {
		id = identifier;
		value = defaultVal.toString();
		SerialProcessor.addSerialFile(this);
	}
	
	public String getID () {
		return id;
	}
	
	public void updateOut (Object o) {
		if(!beenSet) beenSet = true;
		value = o.toString();
		SerialProcessor.updateOut(this);
	}
	
	public void updateIn (Object o) {
		if(!beenSet) beenSet = true;
		value = o.toString();
		if (listener != null)  listener.stateChanged(id, value);
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
	
	public void setListener (SerialListener l) {
		listener = l;
	}
	public SerialListener getListener () {
		return listener;
	}
	
	public boolean beenSet () {
		return beenSet;
	}
}
