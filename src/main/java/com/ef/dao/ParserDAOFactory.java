package com.ef.dao;
/**
 * 
 * @author abhishek.kumar
 *
 */
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
