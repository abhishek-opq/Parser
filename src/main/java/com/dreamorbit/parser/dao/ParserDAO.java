package com.dreamorbit.parser.dao;

import java.util.List;

import com.dreamorbit.parser.util.ResultObject;
import com.ef.LogDataRequest;

public interface ParserDAO {
	
	
	public ResultObject bulkInsert(List<String> logList,String tableName);
	public ResultObject getLogData(LogDataRequest logDateRequest);
	
	public ResultObject storeResults(List<String> logList);

}
