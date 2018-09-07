package com.ef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ef.dao.ParserDAO;
import com.ef.data.LogDataRequest;
import com.ef.util.ParserConstant;
import com.ef.util.ParserException;
import com.ef.util.ResultObject;

/**
 * 
 * @author abhishek.kumar
 *
 */

@Service
@PropertySource("message.properties")
public class ParserServiceImpl implements ParserService {
	@Autowired
	ParserDAO parserDAO;
	@Autowired
	private Environment env;

	/**
	 * This method inserts all the lines of log file into log table
	 * 
	 * @param logList
	 * @return
	 * @throws ParserException
	 */
	public String insertLogData(List<String> logList) throws ParserException {

		ResultObject ro = parserDAO.bulkInsert(logList);
		String message = null;
		if (null != ro && ParserConstant.SUCCESS_CODE == ro.getCode()) {
			message = " Log data stored ";
		} else {
			throw new ParserException(ro.getException().getMessage());
		}

		return message;
	}

	/**
	 * This method fetches the log data as per given input date and threshold
	 * 
	 * @param logDataRequest
	 * @return
	 * @throws ParserException
	 */
	public List<String> getLogData(LogDataRequest logDataRequest) throws ParserException {
		ResultObject ro = parserDAO.getLogData(logDataRequest);
		List<String> logList = null;
		if (null != ro && ParserConstant.SUCCESS_CODE == ro.getCode()) {
			logList = (List<String>) ro.getObject();
		} else {
			throw new ParserException(ro.getException().getMessage());
		}
		return logList;
	}

	/**
	 * This method persists the log data which are fetched based on entered
	 * startdate and threshold
	 * 
	 * @param logList
	 * @param duration
	 * @return
	 * @throws ParserException
	 */
	public String storeLogResult(List<String> logList, String duration) throws ParserException {
		ResultObject ro = parserDAO.storeResults(logList, duration);
		return ro.getMessage();
	}

	/**
	 * This mthod takes the input and initialize LogDataRequest
	 * 
	 * @param arg
	 * @return
	 * @throws ParserException
	 */
	public LogDataRequest initLogData(String[] arg) throws ParserException {
		if (arg.length < 4) {
			throw new ParserException(env.getProperty("VALID.COMMAND"));
		}
		if (4 != arg.length && !arg[0].contains("=") && !arg[1].contains("=") && !arg[2].contains("=")
				&& !arg[3].contains("=")) {
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
