package com.dreamorbit.parser.validator;

import com.ef.LogDataRequest;
import com.ef.ParserConstant;

public class LogDataRequestValidator {
	
	public static String validate(LogDataRequest request) {
		String startDate=request.getStartdate();
		String duration=request.getDuration();
		String thresold=request.getThresold();
		
	if(null!=startDate && !startDate.trim().matches("([0-9]{4})-([0-9]{2})-([0-9]{2}).([0-9]{2}):([0-9]{2}):([0-9]{2})")) {
		return ParserConstant.INVALID_DATE_FORMAT;
	}else if(null==startDate) {
		return ParserConstant.START_DATE_REQUIRED;
	}
	if(null!=duration) {
		if(ParserConstant.DAILY.equals(duration.trim()) || ParserConstant.HOURLY.equals(duration.trim()) ) {
			
		}else {
			return ParserConstant.INVALID_DURATION;
		}
	}else {
		return ParserConstant.DURATION_REQUIRED;
	}
	if(null!=thresold) {
		try {
			int thrsld=Integer.parseInt(thresold);
			if(0>thrsld) {
				return ParserConstant.THRESOLD_INVALID;
			}
		}catch(NumberFormatException nfe) {
			return ParserConstant.THRESOLD_NUMBER;
		}
	}else {
		return ParserConstant.THRESOLD_REQUIRED;	
	}
		return "";
	}
	

}
