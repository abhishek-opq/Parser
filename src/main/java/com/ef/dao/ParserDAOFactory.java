package com.ef.dao;

public class ParserDAOFactory {
	static ParserDAO parserDAO;
	static {
		parserDAO=new ParserDAOImpl();
	}
	private ParserDAOFactory() {}
	
	public static ParserDAO getParserDAO() {
		return parserDAO;
	}
	

}
