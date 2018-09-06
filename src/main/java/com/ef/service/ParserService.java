package com.ef.service;

import java.util.List;

import com.ef.data.LogDataRequest;

public interface ParserService {
	public LogDataRequest initLogData(String[] arg);
	public  String insertLogData(List<String> logList);
	public  List<String> getLogData(LogDataRequest logDataRequest);
	public  String storeLogResult(List<String> logList, String duration);
	

}
