package com.dreamorbit.parser.dao;

import java.sql.Date;
import java.util.List;

import com.dreamorbit.parser.util.ResultObject;

public interface ParserDAO {
	
	public ResultObject createNewTable(String ip);
	public ResultObject bultInsert(List<String> logList);
	public ResultObject getLogData(Date date,double duration,long thersold);

}
