package com.dreamorbit.parser.dao;

import java.util.List;

import com.dreamorbit.parser.LogDataRequest;
import com.dreamorbit.parser.util.ResultObject;

public interface ParserDAO {
	
	public ResultObject createNewTable(String ip);
	public ResultObject bulkInsert(List<String> logList);
	public ResultObject getLogData(LogDataRequest logDateRequest);

}
