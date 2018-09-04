package com.dreamorbit.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class JDBCUtil {
	
	static Connection connection;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException clnfe) {
			throw new ParserException(ParserConstant.CNFE_EXCEPTION);
		}
	}
	
	public static Connection getConnection()throws SQLException{
		return connection=DriverManager.getConnection(ParserConstant.URL,ParserConstant.USERNAME,ParserConstant.PASSWORD);
	}
	
	public static void closeConnection(Connection connection) {
		try {
			if(null!=connection) {
				connection.close();
			}
		}catch(SQLException e) {
			throw new ParserException(ParserConstant.SQL_EXCEPTION);
		}
		
	}
	
	public static void closeStatement(Statement stmt) {
		try {
			if(null!=stmt) {
				stmt.close();
			}
		}catch(SQLException e) {
			throw new ParserException(ParserConstant.SQL_EXCEPTION);
		}
	}
	

}
