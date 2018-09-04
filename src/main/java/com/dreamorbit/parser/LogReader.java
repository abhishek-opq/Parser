package com.dreamorbit.parser;

import java.util.List;

public class LogReader {
	
	
	
	
	
	
	public LogData getLogData(String line){
		 line="2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0";
		LogData logData=new LogData();
		
		String[] str=line.split("|");
		for(String st:str) {
			System.out.println(st);
		}

		logData.setDate(str[0]);
		logData.setIp(str[1]);
		logData.setRequestMethod(str[2]);
		logData.setResponseCode(str[3]);
		logData.setBrowserDetails(str[4]);
		
		return logData;
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(new LogReader().getLogData(""));
	}

}
