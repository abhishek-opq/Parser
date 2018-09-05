package com.ef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ef.util.ParserConstant;
import com.ef.util.ParserException;

public class LogReader {
	
	//private  final Log logger = LogFactory.getLog(getClass());
	public  List<String> readLog() {
		printLog("Going to Read access.log ....");
		String encoding = "UTF-8";
		
		BufferedReader reader = null;
		int count=0;
		
		List<String> logDataList=new ArrayList<String>();
		try {

			reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(ParserConstant.FILE_PATH)), encoding));

			for (String line; (line = reader.readLine()) != null;) {
				count++;
					logDataList.add(line);
					
			}
			printLog("Total lines read : "+count);
		}catch(FileNotFoundException fnfe) {
			throw new ParserException(fnfe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return logDataList;

	}
	
	/*public LogData getLogData(String line){
		 line="2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0";
		LogData logData=new LogData();
		
		String[] str=line.split("\\|");
		for(String st:str) {
			System.out.println(st);
		}

		logData.setDate(str[0]);
		logData.setIp(str[1]);
		logData.setRequestMethod(str[2]);
		logData.setResponseCode(str[3]);
		logData.setBrowserDetails(str[4]);
		
		return logData;
	}*/
	
	
	
	/*
	public static void main(String[] args) {
		List<String> logDataList=	new LogReader().readLog();
		ParserDAO parserDAO=new ParserDAOImpl();
		parserDAO.bultInsert(logDataList);
	}*/
	
	private static void printLog(String str) {
		System.out.println(str);
	}

}
