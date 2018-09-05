package com.ef.dao;

import java.util.List;

import com.ef.data.LogDataRequest;
import com.ef.util.ResultObject;

public interface ParserDAO {
	
	
	public ResultObject bulkInsert(List<String> logList,String tableName);
	public ResultObject getLogData(LogDataRequest logDateRequest);
	
	public ResultObject storeResults(List<String> logList);

}
