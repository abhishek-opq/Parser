package com.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ef.dao.ParserDAO;
import com.ef.data.LogDataRequest;
import com.ef.util.ParserConstant;
import com.ef.util.ParserException;
import com.ef.util.ResultObject;


@Service
@PropertySource("message.properties")
public class ParserServiceImpl implements ParserService{
	@Autowired
	ParserDAO parserDAO;
	@Autowired
	private Environment env;
	public  String insertLogData(List<String> logList) {
		
		ResultObject ro = parserDAO.bulkInsert(logList);
		String message = null;
		if (null != ro && ParserConstant.SUCCESS_CODE == ro.getCode()) {
			message = " Log data stored ";
		} else {
			throw new ParserException(ro.getException().getMessage());
		}
		
		return message;
	}
	
	
	public  List<String> getLogData(LogDataRequest logDataRequest) {
		ResultObject ro = parserDAO.getLogData(logDataRequest);
		List<String> logList = null;
		if (null != ro && ParserConstant.SUCCESS_CODE == ro.getCode()) {
			logList = (List<String>) ro.getObject();
		} else {
			throw new ParserException(ro.getException().getMessage());
		}
		return logList;
	}
	
	
	public String storeLogResult(List<String> logList, String duration) {
		ResultObject ro = parserDAO.storeResults(logList, duration);
		return ro.getMessage();
	}
	
	public LogDataRequest initLogData(String[] arg) {
		if(arg.length<4) {
			throw new ParserException(env.getProperty("VALID.COMMAND"));
		}
		if (4 != arg.length && !arg[0].contains("=") && !arg[1].contains("=") && !arg[2].contains("=") && !arg[3].contains("=")) {
			throw new ParserException(env.getProperty("VALID.COMMAND"));
		}
		String inputString = "";
		for (String input : arg) {

			inputString = inputString + input;
		}

		String[] inputArray = inputString.split("--");

		LogDataRequest logDataRequest = new LogDataRequest();
		logDataRequest.setFilePath(inputArray[1].split("=")[1]);
		logDataRequest.setStartdate(inputArray[2].split("=")[1]);
		logDataRequest.setDuration(inputArray[3].split("=")[1]);
		logDataRequest.setThresold(inputArray[4].split("=")[1]);
		/**/

		return logDataRequest;
	}
	

}
