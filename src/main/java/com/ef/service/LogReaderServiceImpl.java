package com.ef.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ef.util.ParserException;

/**
 * 
 * @author abhishek.kumar 
 * This class is responsible to read all the lines from
 *         log file
 */
@Service
public class LogReaderServiceImpl implements LogReaderService {
	private static final String encoding = "UTF-8";

	/**
	 * Reading log file
	 * 
	 * @param filePath
	 * @return
	 */
	public List<String> readLog(String filePath) {

		BufferedReader reader = null;
		

		List<String> logDataList = new ArrayList<String>();
		try {

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), encoding));

			for (String line; (line = reader.readLine()) != null;) {
				
				logDataList.add(line);

			}

		} catch (FileNotFoundException fnfe) {
			throw new ParserException(fnfe.getMessage());

		} catch (Exception e) {
			throw new ParserException(e.getMessage());
		} finally {
			if (null != reader)
				try {
					reader.close();
				} catch (IOException e) {

					throw new ParserException(e.getMessage());
				}
		}
		return logDataList;

	}

}
