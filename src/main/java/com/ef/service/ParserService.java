package com.ef.service;

import java.util.List;

import com.ef.data.LogDataRequest;
import com.ef.util.ParserException;

public interface ParserService {
	/**
	 * This mthod takes the input and initialize LogDataRequest
	 * 
	 * @param arg
	 * @return
	 * @throws ParserException
	 */
	public LogDataRequest initLogData(String[] arg) throws ParserException;

	/**
	 * This method inserts all the lines of log file into log table
	 * 
	 * @param logList
	 * @return
	 * @throws ParserException
	 */
	public String insertLogData(List<String> logList) throws ParserException;

	/**
	 * This method fetches the log data as per given input date and threshold
	 * 
	 * @param logDataRequest
	 * @return
	 * @throws ParserException
	 */
	public List<String> getLogData(LogDataRequest logDataRequest) throws ParserException;

	/**
	 * This method persists the log data which are fetched based on entered
	 * startdate and threshold
	 * 
	 * @param logList
	 * @param duration
	 * @return
	 * @throws ParserException
	 */
	public String storeLogResult(List<String> logList, String duration) throws ParserException;

}
