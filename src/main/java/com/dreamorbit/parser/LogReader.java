package com.dreamorbit.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.dreamorbit.parser.dao.ParserDAO;
import com.dreamorbit.parser.dao.ParserDAOImpl;

public class LogReader {
	
	
	public List<String> readLog() {

		String encoding = "UTF-8";
		int maxlines = 100;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		List<String> logDataList=new ArrayList<String>();
		try {

			reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File("D:/Parser/ex/access.log")), encoding));

			for (String line; (line = reader.readLine()) != null;) {
				
					logDataList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return logDataList;

	}
	
	public LogData getLogData(String line){
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
	}
	
	
	
	
	public static void main(String[] args) {
		List<String> logDataList=	new LogReader().readLog();
		ParserDAO parserDAO=new ParserDAOImpl();
		parserDAO.bultInsert(logDataList);
	}

}
