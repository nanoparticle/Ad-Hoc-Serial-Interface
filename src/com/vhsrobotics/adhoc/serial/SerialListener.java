package com.vhsrobotics.adhoc.serial;

public interface SerialListener {
	public abstract void stateChanged (String id, String newVal);
}
