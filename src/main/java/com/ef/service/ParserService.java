package com.ef.service;

import java.util.List;

import com.ef.data.LogDataRequest;
import com.ef.util.ParserException;

public interface ParserService {
	public LogDataRequest initLogData(String[] arg)throws ParserException;
	public  String insertLogData(List<String> logList)throws ParserException;
	public  List<String> getLogData(LogDataRequest logDataRequest)throws ParserException;
	public  String storeLogResult(List<String> logList, String duration)throws ParserException;
	

}
