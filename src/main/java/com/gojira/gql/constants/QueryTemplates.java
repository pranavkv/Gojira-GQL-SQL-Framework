package com.gojira.gql.constants;

public class QueryTemplates {

	public static final String INSERT = "INSERT INTO %s(%s) VALUES(%s)";
	
	public static final String DELETE = "DELETE FROM %s WHERE %s";
	
	public static final String UPDATE = "UPDATE %s SET %s WHERE %s=?";
	
	public static final String SELECT_ALL = "SELECT * FROM %s WHERE %s=?";
	
	public static final String SELECT = "SELECT * FROM %s WHERE ";
}
