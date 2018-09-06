package com.ef.util;
/**
 * 
 * @author abhishek.kumar
 *
 */
public interface ParserConstant {
	String URL = "jdbc:mysql://127.0.0.1:3306/parser";
	String USERNAME = "root";
	String PASSWORD = "root";
	String SQL_EXCEPTION = "";
	String CNFE_EXCEPTION = "";
	String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	String FILE_PATH = "E:\\Rater_ALL\\TEST_0t_SEP\\workspace\\test_files\\access.log";
	long SUCCESS_CODE = 100;
	long ERROR_CODE = -100;
	String INVALID_DATE_FORMAT = "Date format is invalid";
	String START_DATE_REQUIRED = "Please enter start date";
	String DAILY = "daily";
	String HOURLY = "hourly";
	String INVALID_DURATION = "Duration can either be daily or hourly";
	String DURATION_REQUIRED = "Please enter duration ";

	String THRESOLD_NUMBER = "Thresold should be a number ";
	String THRESOLD_INVALID = "Thresold can not be negative";
	String THRESOLD_REQUIRED = "Please enter thresold ";
	String FILE_PATH_REQUIRED = "Please enter file path suffixed with file name ";
	String RECORD_NOT_FOUND = "No Records Found ! ";
	String PIPELINE_DELIMETER = "|";
	String TABLE_NAME = "LOG_RESULT_TABLE";
	int HOURLY_HIT = 200;
	int DAILY_HIT = 500;
	String VALID_COMMAND="A valid input should look like  --accesslog=E:/test_files/access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100";

}
