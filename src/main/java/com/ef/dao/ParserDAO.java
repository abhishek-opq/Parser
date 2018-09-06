package com.ef.dao;

import java.util.List;

import com.ef.data.LogDataRequest;
import com.ef.util.ResultObject;
/**
 * 
 * @author abhishek.kumar
 *
 */
public interface ParserDAO {
	
	
	public ResultObject bulkInsert(List<String> logList);
	public ResultObject getLogData(LogDataRequest logDateRequest);
	public ResultObject storeResults(List<String> logList,String duration);


}
