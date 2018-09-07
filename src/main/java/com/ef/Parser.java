package com.ef;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ef.config.ParserConfig;
import com.ef.data.LogDataRequest;
import com.ef.service.LogReaderService;
import com.ef.service.ParserService;
import com.ef.util.ParserConstant;
import com.ef.util.ParserException;
import com.ef.validator.LogDataRequestValidator;

public class Parser {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
	
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ParserConfig.class);
		ParserService parserService=ctx.getBean(ParserService.class);
		LogReaderService logReaderService=ctx.getBean(LogReaderService.class);
		MessageSource messageSource=ctx.getBean(MessageSource.class);
		LogDataRequestValidator validator=ctx.getBean(LogDataRequestValidator.class);
		List<String> logFileList = new ArrayList<String>();
		/**
		 * Creating request object from parameters entered in command prompt
		 */
		
		LogDataRequest logDataRequest=new LogDataRequest();
		try {
			logDataRequest = parserService.initLogData(args);
		} catch (ParserException e) {
			throw new ParserException(e.getMessage());
		}
		/**
		 * Validating input 
		 */
		String errorMessage = validator.validate(logDataRequest);
		if (!errorMessage.isEmpty()) {
			throw new ParserException(errorMessage);
		}
		
		/**
		 * Reading logs from log file 
		 */
		String endDate = getEndDate(logDataRequest.getStartdate(), logDataRequest.getDuration(),
				logDataRequest.getThresold());
		logDataRequest.setEndDate(endDate);
		try {
		 logFileList = logReaderService.readLog(logDataRequest.getFilePath());
		}catch(ParserException pe) {
			throw new ParserException(pe.getMessage());
		}catch(Exception e) {
			throw new ParserException(e.getMessage());
		}
		/**
		 * Inserting log data list into log table
		 */
		 
		try {
			String message= parserService.insertLogData(logFileList);
		} catch (ParserException e) {
			throw new ParserException(e.getMessage());
		}
		/**
		 * Getting out log data as per the input
		 */
		
		
		
		List<String> logDataList;
		try {
			logDataList = parserService.getLogData(logDataRequest);
		} catch (ParserException e) {
			throw new ParserException(e.getMessage());
		}
		printLog(
				"<==========================================LOG DATA BEGINS==========================================================>\n\n");
		if (null != logDataList && !logDataList.isEmpty()) {
			for (String logData : logDataList) {
				printLog(logData);
			}
		} else {
			try {
				throw new ParserException(messageSource.getMessage("RECORD.NOT.FOUND", null,null));
			} catch (NoSuchMessageException e) {
				throw new ParserException(e.getMessage());
			} catch (ParserException e) {
				throw new ParserException(e.getMessage());
			}
		}
		printLog("\n\n");
		printLog(
				"<==========================================LOG DATA ENDS============================================================> \n\n");
		/**
		 * Storing log results
		 */
		if (null != logDataList && !logDataList.isEmpty()) {
			String messageStore;
			try {
				messageStore = parserService.storeLogResult(logDataList, logDataRequest.getDuration());
			} catch (ParserException e) {
				throw new ParserException(e.getMessage());
			}
			printLog(messageStore);
		}
		
		
	}

	
	
	/**
	 * 
	 * @param date
	 * @param duration
	 * @param thresold
	 * @return String
	 * Calculating end date based on start date entered and duration
	 */
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
	
	private static void printLog(String str) {
		System.out.println(str);
	}
	

}
