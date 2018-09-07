package com.ef.service;

import java.util.List;
/**
 * 
 * @author abhishek.kumar
 *
 */
public interface LogReaderService {
	/**
	 * Reading log file
	 * @param filePath
	 * @return
	 */
	public List<String> readLog(String filePath);

}
