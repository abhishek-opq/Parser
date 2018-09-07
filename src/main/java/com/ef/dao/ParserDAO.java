package com.ef.dao;

import java.util.List;

import com.ef.data.LogDataRequest;
import com.ef.util.ParserException;
import com.ef.util.ResultObject;
/**
 * 
 * @author abhishek.kumar
 *
 */
public interface ParserDAO {
	
	/**
	 * This method does a bulk insert for the lines read from log file
	 * @param logList
	 * @return
	 * @throws ParserException
	 */
	public ResultObject bulkInsert(List<String> logList)throws ParserException;
	
	/**
	 *  This method fetches the log data as per given input date and threshold
	 * @param logDateRequest
	 * @return
	 * @throws ParserException
	 */
	public ResultObject getLogData(LogDataRequest logDateRequest)throws ParserException;
	/**
	 * This method persists the log data which are fetched based on entered
	 * startdate and threshold
	 * @param logList
	 * @param duration
	 * @return
	 * @throws ParserException
	 */
	public ResultObject storeResults(List<String> logList,String duration)throws ParserException;


}
