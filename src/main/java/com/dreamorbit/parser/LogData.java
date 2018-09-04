package com.dreamorbit.parser;

public class LogData {
	private String date;
	private String ip;
	private String requestMethod;
	private String responseCode;
	private String browserDetails;
	
	public LogData() {
		// TODO Auto-generated constructor stub
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getBrowserDetails() {
		return browserDetails;
	}

	public void setBrowserDetails(String browserDetails) {
		this.browserDetails = browserDetails;
	}

	@Override
	public String toString() {
		return "LogData [date=" + date + ", ip=" + ip + ", requestMethod=" + requestMethod + ", responseCode="
				+ responseCode + ", browserDetails=" + browserDetails + "]";
	}

	
}
