package com.ef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ef.util.ParserConstant;
import com.ef.util.ParserException;
/**
 * 
 * @author abhishek.kumar
 *
 */
public class LogReader {

	public List<String> readLog(String filePath) {
		printLog("Going to Read access.log ....");
		String encoding = "UTF-8";

		BufferedReader reader = null;
		int count = 0;

		List<String> logDataList = new ArrayList<String>();
		try {

			reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(filePath)), encoding));

			for (String line; (line = reader.readLine()) != null;) {
				count++;
				logDataList.add(line);

			}
			printLog("Total lines read : " + count);
		} catch (FileNotFoundException fnfe) {
			throw new ParserException(fnfe.getMessage()+" | "+ParserConstant.FILE_PATH_REQUIRED);

		} catch (Exception e) {
			throw new ParserException(e.getMessage());
		} finally {
			if (null != reader)
				try {
					reader.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
		}
		return logDataList;

	}

	private static void printLog(String str) {
		System.out.println(str);
	}

}
