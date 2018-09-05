package com.dreamorbit.parser;

import java.io.Serializable;

public class LogDataRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startdate;
	private String endDate;
	private String duration;
	private String thresold;
	
	
	public LogDataRequest() {
		// TODO Auto-generated constructor stub
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getThresold() {
		return thresold;
	}


	public void setThresold(String thresold) {
		this.thresold = thresold;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	@Override
	public String toString() {
		return "LogDataRequest [startdate=" + startdate + ", duration=" + duration + ", thresold=" + thresold + "]";
	}


	

	
}
