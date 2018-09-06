package com.ef.data;

import java.io.Serializable;
/**
 * 
 * @author abhishek.kumar
 *
 */
public class LogDataRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startdate;
	private String endDate;
	private String duration;
	private String thresold;
	private String filePath;
	
	
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


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	@Override
	public String toString() {
		return "LogDataRequest [startdate=" + startdate + ", duration=" + duration + ", thresold=" + thresold + "]";
	}


	

	
}
