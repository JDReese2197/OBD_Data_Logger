package com.jamesreese.carpi.model;

import java.time.LocalDate;
import java.time.LocalTime;

/*
 * 	POJO Class to hold log information when pulled from CSV file
 */

public class DataLog {
	private int id;
	private LocalDate date;
	private LocalTime time;
	private double value;
	private String dataType;
	
	public DataLog() {}
	
	/**************************************************************************************************
	 * 									GETTERS AND SETTERS
	 **************************************************************************************************/

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = Math.round(value * 100) / 100;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Log " + id + ": " + date + " " + time + " - " + value + " " + dataType;
	}
}
