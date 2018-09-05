package com.dreamorbit.parser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dreamorbit.parser.util.ResultObject;
import com.ef.JDBCUtil;
import com.ef.LogDataRequest;
import com.ef.ParserConstant;

public class ParserDAOImpl implements ParserDAO {
	// private final Log logger = LogFactory.getLog(getClass());
	

	public ResultObject createNewTable(String ip) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ResultObject bulkInsert(List<String> logList,String tableName) {
		printLog("Going to persist logs to parser,log table ..... ");
		
		ResultObject ro = new ResultObject(ParserConstant.ERROR_CODE);
		int count = 0;
		String insertQuery =null;
		if(null!=logList && !logList.isEmpty()) {
			createLogTable();
		}
		
		try {
			Connection connection=JDBCUtil.getConnection();
			connection.setAutoCommit(false);
			if(null==tableName) {
			 insertQuery = "insert into parser.log (createdate,ip,request,responsecode,browser) values (?,?,?,?,?)";
			}else {
				insertQuery = "insert into parser."+tableName+" (createdate,ip,request,responsecode,browser) values (?,?,?,?,?)";
			}
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			printLog("Creating batch for bulk insert .. \n\n ");
			for (String log : logList) {
				count++;
				String[] str = log.split("\\|");

				preparedStatement.setString(1, str[0]);
				preparedStatement.setString(2, str[1]);
				preparedStatement.setString(3, str[2]);
				preparedStatement.setString(4, str[3]);
				preparedStatement.setString(5, str[4]);
				preparedStatement.addBatch();
				

			}
			printLog("Added  " + count + " logs to batch .  Persisting ....");
			long start = System.currentTimeMillis();
			int[] inserted = preparedStatement.executeBatch();
			connection.commit();
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
			preparedStatement.setString(2, logDateRequest.getEndDate());
			preparedStatement.setString(3, logDateRequest.getThresold());
			rs=preparedStatement.executeQuery();
			
			while(rs.next()) {
				
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
				str=str+browser;
				logList.add(str);
				
			}
			
			resultObject.setObject(logList);
			resultObject.setCode(ParserConstant.SUCCESS_CODE);
			
			
			
			return resultObject;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			resultObject.setException(e);
			resultObject.setMessage(e.getMessage());
		}finally {
			JDBCUtil.closeConnection(connection);
			JDBCUtil.closeStatement(preparedStatement);
			JDBCUtil.closeResultSet(rs);
		}
		
		return null;
	}
	
	private void createLogTable() {
		String dropTableSQL="DROP TABLE IF EXISTS log";
		String createTableSQL="create table log (createdate Timestamp,ip varchar(50),request varchar(50),responsecode varchar(10),browser varchar(300))";
		Connection connection=null;
		PreparedStatement ps=null;
		try {
			connection=JDBCUtil.getConnection();
			connection.setAutoCommit(false);
			ps=connection.prepareStatement(dropTableSQL);
			printLog("Droping log table "+dropTableSQL);
			ps.execute();
			ps.close();
			ps=connection.prepareStatement(createTableSQL);
			printLog("Creating log table "+createTableSQL);
			ps.execute();
			connection.commit();
		}catch(SQLException e) {
			try {
			connection.rollback();
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			
				JDBCUtil.closeConnection(connection);
				JDBCUtil.closeStatement(ps);
				
			
		}
		
	}
	

	private void createLogResultTable(String tableName) {
		String dropTableSQL="DROP TABLE IF EXISTS "+tableName;
		String createTableSQL="create table  "+tableName+"  (createdate Timestamp,ip varchar(50),request varchar(50),responsecode varchar(10),browser varchar(300))";
		Connection connection=null;
		PreparedStatement ps=null;
		try {
			connection=JDBCUtil.getConnection();
			connection.setAutoCommit(false);
			ps=connection.prepareStatement(dropTableSQL);
			printLog("Droping log table "+dropTableSQL);
			ps.execute();
			ps.close();
			ps=connection.prepareStatement(createTableSQL);
			printLog("Creating log table "+createTableSQL);
			ps.execute();
			connection.commit();
		}catch(SQLException e) {
			try {
			connection.rollback();
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			
				JDBCUtil.closeConnection(connection);
				JDBCUtil.closeStatement(ps);
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String getUniqueTableName() {
		String sql="select max(id) from log_table_name";
		String sqlInsert="insert into log_table_name (tablename) values (?)";
		Connection connection=null;
		ResultSet rs=null;
		String table_name=ParserConstant.TABLE_NAME;
		PreparedStatement ps=null;
		try {
			connection=JDBCUtil.getConnection();
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()) {
				int id=rs.getInt(1)+1;
				table_name=table_name+id;
			}
			ps.close();
			ps=connection.prepareStatement(sqlInsert);
			ps.setString(1, table_name);
			ps.executeUpdate();
			
			
			
		}catch(SQLException e) {
			try {
			connection.rollback();
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			JDBCUtil.closeConnection(connection);
			JDBCUtil.closeStatement(ps);
			JDBCUtil.closeResultSet(rs);
		}
		return table_name;
	}

	public ResultObject storeResults(List<String> logList) {
		String tableName=getUniqueTableName();
		createLogResultTable(tableName);
		ResultObject resultObject=bulkInsert(logList, tableName);
		resultObject.setMessage("Result LOGS have been stored into table "+tableName);
		return resultObject;
	}

	
	
	public static void printLog(String str) {
		System.out.println(str);
	}
	
	
	
	
	

}
