package com.dreamorbit.parser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dreamorbit.parser.JDBCUtil;
import com.dreamorbit.parser.LogDataRequest;
import com.dreamorbit.parser.ParserConstant;
import com.dreamorbit.parser.util.ResultObject;

public class ParserDAOImpl implements ParserDAO {
	private final Log logger = LogFactory.getLog(getClass());
	

	public ResultObject createNewTable(String ip) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultObject bulkInsert(List<String> logList) {
		logger.info("Going to persist logs to parser,log table ..... ");
		ResultObject ro = new ResultObject(ParserConstant.ERROR_CODE);
		int count = 0;
		try {
			Connection connection=JDBCUtil.getConnection();
			connection.setAutoCommit(true);

			String compiledQuery = "insert into parser.log (createdate,ip,request,responsecode,browser) values (?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(compiledQuery);
			logger.info("Creating batch for bulk insert .. \n\n ");
			for (String log : logList) {
				count++;
				String[] str = log.split("\\|");

				preparedStatement.setString(1, str[0]);
				preparedStatement.setString(2, str[1]);
				preparedStatement.setString(3, str[2]);
				preparedStatement.setString(4, str[3]);
				preparedStatement.setString(5, str[4]);
				preparedStatement.addBatch();
				logger.info("Adding line no " + count + " to batch");

			}
			logger.info("Added  " + count + " logs to batch .  Persisting ....");
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

	
	@Override
	public ResultObject getLogData(LogDataRequest logDateRequest) {
		String sql = " SELECT createdate,ip,request,responsecode,browser, COUNT(*) " + " FROM log where createdate >? and createdate <? " + " GROUP BY ip "
				+ " HAVING COUNT(*) >= ? ";
		List<String> logList=new ArrayList<String>();
		ResultObject resultObject=new ResultObject(ParserConstant.ERROR_CODE);
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		try {
			connection=JDBCUtil.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, logDateRequest.getStartdate());
			preparedStatement.setString(1, logDateRequest.getEndDate());
			preparedStatement.setString(1, logDateRequest.getThresold());
			rs=preparedStatement.executeQuery();
			while(rs.next()) {
				//2017-01-01 00:00:11.763|192.168.234.82|"GET / HTTP/1.1"|200|"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"
				String str=new String();
				String  date=rs.getString("createdate");
				str=str+date+ParserConstant.PIPELINE_DELIMETER;
				String ip=rs.getString("ip");
				str=str+ip+ParserConstant.PIPELINE_DELIMETER;
				String request=rs.getString("request");
				str=str+request+ParserConstant.PIPELINE_DELIMETER;
				String responsecode=rs.getString("responsecode");
				str=str+responsecode+ParserConstant.PIPELINE_DELIMETER;
				String browser=rs.getString("browser");
				logList.add(str);
				
			}
			
			resultObject.setObject(logList);
			resultObject.setCode(ParserConstant.SUCCESS_CODE);
			
			
			
			return resultObject;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			resultObject.setException(e);
		}finally {
			connection.close();
			preparedStatement.close();
			rs.close();
		}
		
		return null;
	}

}
