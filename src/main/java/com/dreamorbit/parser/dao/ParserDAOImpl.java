package com.dreamorbit.parser.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.dreamorbit.parser.JDBCUtil;
import com.dreamorbit.parser.ParserConstant;
import com.dreamorbit.parser.util.ResultObject;

public class ParserDAOImpl implements ParserDAO {

	static Connection connection;
	static {
		try {
			connection = JDBCUtil.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultObject createNewTable(String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultObject bultInsert(List<String> logList) {
		ResultObject ro=new ResultObject(ParserConstant.ERROR_CODE);
		try {
			
			connection.setAutoCommit(true);

			String compiledQuery = "insert into parser.log (createdate,ip,request,responsecode,browser) values (?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(compiledQuery);
			for (String log : logList) {
				String[] str = log.split("\\|");

				preparedStatement.setString(1, str[0]);
				preparedStatement.setString(2, str[1]);
				preparedStatement.setString(3, str[2]);
				preparedStatement.setString(4, str[3]);
				preparedStatement.setString(5, str[4]);
				preparedStatement.addBatch();
				
				
			}

			long start = System.currentTimeMillis();
			int[] inserted = preparedStatement.executeBatch();
			long end = System.currentTimeMillis();
System.out.println(inserted);
			System.out.println("total time taken to insert the batch = " + (end - start) + " ms");

			preparedStatement.close();
			connection.close();
			ro.setCode(ParserConstant.SUCCESS_CODE);

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				ex = ex.getNextException();
			}
			throw new RuntimeException("Error");
		}
		return ro;
	}

	public ResultObject getLogData(Date date, double duration, long thersold) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultObject getLogData(java.sql.Date date, double duration, long thersold) {
		// TODO Auto-generated method stub
		return null;
	}

}
