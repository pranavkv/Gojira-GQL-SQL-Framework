package com.gojira.gql.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class GojiraDBPool implements DBPool {

	private static GojiraDBPool instance = null;
	private static InitialContext context;
	private static BasicDataSource datasource;

	private GojiraDBPool() {

	}

	public static void initTomcatDataSource() {

		try {
			context = new InitialContext();
			datasource = (BasicDataSource) context.lookup("java:comp/env/jdbc/kgi");
		} catch (NamingException e) {
			System.out.println(e);
		}
	}

	public static void initDataSource(PropertiesConfiguration properties) {

		datasource = new BasicDataSource();
		datasource.setDriverClassName(properties.getString("db.driver_class"));
		datasource.setUrl(properties.getString("db.kgi.url"));
		datasource.setUsername(properties.getString("db.username"));
		datasource.setPassword(properties.getString("db.password"));

	}

	public static GojiraDBPool getInstance() {

		if (null == instance)
			instance = new GojiraDBPool();

		return instance;
	}

	/**
	 * function to create a connection
	 * 
	 * @return {@link java.sql.Connection}
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {

		try {
			Connection c = datasource.getConnection();
			System.out.println("Opening datasource connection : " + System.identityHashCode(c));
			return c;
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void closeConnection(Connection connetion) {
		try {
			connetion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(PreparedStatement ps) {
		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(CallableStatement cs) {
		try {
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void releasePool() {

		try {

			if (datasource != null)
				datasource.close();

		} catch (SQLException e) {
		}

	}
}
