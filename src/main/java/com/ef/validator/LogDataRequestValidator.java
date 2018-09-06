package com.ef.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ef.data.LogDataRequest;
import com.ef.util.ParserConstant;
/**
 * 
 * @author abhishek.kumar
 *
 */
@Component
public class LogDataRequestValidator {
@Autowired
	MessageSource messageSOurce;
	public  String validate(LogDataRequest request) {
		String startDate = request.getStartdate();
		String duration = request.getDuration();
		String thresold = request.getThresold();
		String filePath=request.getFilePath();
		if(null==filePath) {
			return messageSOurce.getMessage("FILE.PATH.REQUIRED", null,null);
		}
		
		
		if (null != startDate
				&& !startDate.trim().matches("([0-9]{4})-([0-9]{2})-([0-9]{2}).([0-9]{2}):([0-9]{2}):([0-9]{2})")) {
			
			return messageSOurce.getMessage("INVALID.DATE.FORMAT", null,null);
		} else if (null == startDate) {
			
			return messageSOurce.getMessage("START.DATE.REQUIRED", null,null);
		}
		if (null != duration) {
			if (ParserConstant.DAILY.equals(duration.trim()) || ParserConstant.HOURLY.equals(duration.trim())) {

			} else {
				
				return messageSOurce.getMessage("INVALID.DURATION", null,null);
			}
		} else {
			
			return messageSOurce.getMessage("DURATION.REQUIRED", null,null);
		}
		if (null != thresold) {
			try {
				int thrsld = Integer.parseInt(thresold);
				if (0 > thrsld) {
					
					return messageSOurce.getMessage("THRESOLD.INVALID", null,null);
				}
			} catch (NumberFormatException nfe) {
				
				return messageSOurce.getMessage("THRESOLD.NUMBER", null,null);
			}
		} else {
			
			return messageSOurce.getMessage("THRESOLD.REQUIRED", null,null);
		}
		return "";
	}

}
