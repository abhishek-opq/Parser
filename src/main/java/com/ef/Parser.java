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
/**
 * 
 * @author abhishek.kumar
 *
 */
public class Parser {
	static ParserDAO parserDAO = ParserDAOFactory.getParserDAO();


	public static void main(String[] args) {

		
		
		LogDataRequest logDataRequest = initLogData(args);
		String errorMessage = LogDataRequestValidator.validate(logDataRequest);
		if (!errorMessage.isEmpty()) {
			throw new ParserException(errorMessage);
		}
		String message = insertLogData(logDataRequest.getFilePath());
		printLog(message);
		String endDate = getEndDate(logDataRequest.getStartdate(), logDataRequest.getDuration(),
				logDataRequest.getThresold());
		logDataRequest.setEndDate(endDate);

		List<String> logDataList = getLogData(logDataRequest);
		printLog(
				"<==========================================LOG DATA BEGINS==========================================================>\n\n");
		if (null != logDataList && !logDataList.isEmpty()) {
			for (String logData : logDataList) {
				printLog(logData);
			}
		} else {
			throw new ParserException(ParserConstant.RECORD_NOT_FOUND);
		}
		printLog("\n\n");
		printLog(
				"<==========================================LOG DATA ENDS============================================================> \n\n");
		if (null != logDataList && !logDataList.isEmpty()) {
			String messageStore = storeLogResult(logDataList, logDataRequest.getDuration());
			printLog(messageStore);
		}

	}

	private static LogDataRequest initLogData(String[] arg) {
		if(arg.length<4) {
			throw new ParserException(ParserConstant.VALID_COMMAND);
		}
		if (4 != arg.length && !arg[0].contains("=") && !arg[1].contains("=") && !arg[2].contains("=") && !arg[3].contains("=")) {
			throw new ParserException(ParserConstant.VALID_COMMAND);
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

	private static String insertLogData(String filePath) {
		List<String> logList = new LogReader().readLog(filePath);
		ResultObject ro = parserDAO.bulkInsert(logList);
		String message = null;
		if (null != ro && ParserConstant.SUCCESS_CODE == ro.getCode()) {
			message = " Log data stored ";
		} else {
			throw new ParserException(ro.getException().getMessage());
		}
		return message;
	}

	private static List<String> getLogData(LogDataRequest logDataRequest) {
		ResultObject ro = parserDAO.getLogData(logDataRequest);
		List<String> logList = null;
		if (null != ro && ParserConstant.SUCCESS_CODE == ro.getCode()) {
			logList = (List<String>) ro.getObject();
		} else {
			throw new ParserException(ro.getException().getMessage());
		}
		return logList;
	}

	private static String storeLogResult(List<String> logList, String duration) {
		ResultObject ro = parserDAO.storeResults(logList, duration);
		return ro.getMessage();
	}

	private static void printLog(String str) {
		System.out.println(str);
	}

}
