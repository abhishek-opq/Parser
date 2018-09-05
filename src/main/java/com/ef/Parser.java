package com.ef;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ef.dao.ParserDAO;
import com.ef.dao.ParserDAOFactory;
import com.ef.data.LogDataRequest;
import com.ef.util.ParserConstant;
import com.ef.util.ParserException;
import com.ef.util.ResultObject;
import com.ef.validator.LogDataRequestValidator;

public class Parser {
	static ParserDAO parserDAO = ParserDAOFactory.getParserDAO();
	//private static final Log logger = LogFactory.getLog("App");
	public static void main(String[] args) {

		String[] logDataArray = { "--2017-01-01.13:00:00", "-- hourly", "--200" };
		String message=insertLogData();
		printLog(message);
		LogDataRequest logDataRequest = initLogData(logDataArray);
		String errorMessage = LogDataRequestValidator.validate(logDataRequest);
		if (!errorMessage.isEmpty()) {
			throw new ParserException(errorMessage);
		}

		String endDate=getEndDate(logDataRequest.getStartdate(), logDataRequest.getDuration(), logDataRequest.getThresold());
		logDataRequest.setEndDate(endDate);
		
		
		List<String> logDataList=getLogData(logDataRequest);
		printLog("<==========================================LOG DATA==========================================================>\n\n");
		if(null!=logDataList && !logDataList.isEmpty()) {
			for(String logData:logDataList) {
				printLog(logData);
			}
		}else {
			throw new ParserException(ParserConstant.RECORD_NOT_FOUND);
		}
		printLog("\n\n");
		printLog("<==========================================LOG DATA ENDS==========================================================> \n\n");
		if(null!=logDataList && !logDataList.isEmpty()) {
		String messageStore=storeLogResult(logDataList);
		printLog(messageStore);
		}
		
		

	}

	private static LogDataRequest initLogData(String[] arg) {
		if (3 != arg.length) {
			throw new ParserException("Invalid input");
		}
		String inputString = "";
		for (String input : arg) {

			inputString = inputString + input;
		}

		String[] inputArray = inputString.split("--");

		LogDataRequest logDataRequest = new LogDataRequest();
		logDataRequest.setStartdate(inputArray[1]);
		logDataRequest.setDuration(inputArray[2]);
		logDataRequest.setThresold(inputArray[3]);

		return logDataRequest;
	}

	private static String getEndDate(String date, String duration, String thresold) {

		LocalDateTime endDate = null;
		LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
		
		int thrsld = Integer.parseInt(thresold);
		if (ParserConstant.DAILY.equals(duration.trim())) {
			endDate = localDateTime.plusDays(1);
		}
		if (ParserConstant.HOURLY.equals(duration.trim())) {
			endDate = localDateTime.plusHours(1);

		}

		return String.valueOf(endDate);

	}
	
	private static String insertLogData() {
		List<String> logList=new LogReader().readLog();
		ResultObject ro=parserDAO.bulkInsert(logList,null);
		String message=null;
		if(null!=ro && ParserConstant.SUCCESS_CODE==ro.getCode()) {
			message=" Log data stored ";
		}else {
			throw new ParserException(ro.getException().getMessage());
		}
		return message;
	}

	private static  List<String> getLogData(LogDataRequest logDataRequest){
		ResultObject ro=parserDAO.getLogData(logDataRequest);
		List<String> logList=null;
		if(null!=ro && ParserConstant.SUCCESS_CODE==ro.getCode()) {
			logList= (List<String>)ro.getObject();
			}
		else {
			throw new ParserException(ro.getException().getMessage());
		}
		return logList;
		}
	
	
	private static String storeLogResult(List<String> logList) {
		ResultObject ro=parserDAO.storeResults(logList);
		return ro.getMessage();
	}
	

	private static void printLog(String str) {
		System.out.println(str);
	}
	
}
