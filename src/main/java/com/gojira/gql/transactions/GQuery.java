package com.gojira.gql.transactions;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.configuration.ConfigurationException;

import com.gojira.gql.common.AppConfig;
import com.gojira.gql.common.ClassSerialzer;
import com.gojira.gql.constants.QueryTemplates;
import com.gojira.gql.db.GojiraDBPool;
import com.gojira.gql.exception.GojiraException;
import com.gojira.gql.exception.INFO_IDS;

public class GQuery {

	private HashMap<String, Object> columnValueMap;

	String gql = "";

	public void Quey() {
		columnValueMap = new HashMap<String, Object>();
	}

	public GqueryMapper select(String table) {
		this.gql = "SELECT * FROM " + table;
		GqueryMapper mapper = new GqueryMapper();
		mapper.setGQL(this.gql);
		return mapper;
	}

	private String getInsertQueryTemplate(String tableName) {

		String queryTemplate = QueryTemplates.INSERT;

		ArrayList<String> preparedValues = new ArrayList<String>();
		ArrayList<String> preparedColumns = new ArrayList<String>();

		for (Entry<String, Object> entry : columnValueMap.entrySet()) {
			preparedColumns.add(entry.getKey());
			preparedValues.add("?");
		}

		String query = String.format(queryTemplate, tableName,
				preparedColumns.stream().collect(Collectors.joining(",")),
				preparedValues.stream().collect(Collectors.joining(",")));
		System.out.println(query);
		return query;
	}

	private String getDeleteQueryTemplate(String tableName) {

		String queryTemplate = QueryTemplates.DELETE;

		ArrayList<String> preparedColumns = new ArrayList<String>();

		for (Entry<String, Object> entry : columnValueMap.entrySet()) {
			preparedColumns.add(entry.getKey() + "=" + "?");
		}

		String query = String.format(queryTemplate, tableName,
				preparedColumns.stream().collect(Collectors.joining(" AND ")));
		System.out.println(query);

		return query;
	}

	public int addAll(ArrayList<Object> classObjList) throws GojiraException {

		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;

		this.columnValueMap = ClassSerialzer.serialize(classObjList.get(0));
		String tableName = ClassSerialzer.getTableName(classObjList.get(0));

		try {

			conn = GojiraDBPool.getInstance().getConnection();
			ps = conn.prepareStatement(getInsertQueryTemplate(tableName));

			for (Object obj : classObjList) {

				this.columnValueMap = ClassSerialzer.serialize(obj);

				for (Entry<String, Object> entry : this.columnValueMap.entrySet()) {
					Object val = entry.getValue();
					if (val.getClass().equals(String.class))
						ps.setString(count, (String) val);
					else if (val.getClass().equals(Integer.class))
						ps.setInt(count, (Integer) val);
					else if (val.getClass().equals(Boolean.class))
						ps.setBoolean(count, (Boolean) val);

					count++;
					ps.addBatch();
				}

			}

			ps.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GojiraException(INFO_IDS.DYNAMIC_MSG, e.getMessage(), e.getErrorCode());
		} finally {
			GojiraDBPool.closeStatement(ps);
			GojiraDBPool.closeConnection(conn);
		}

		return count;
	}

	public boolean add(Object classObj) throws GojiraException {

		Connection conn = null;
		PreparedStatement ps = null;

		this.columnValueMap = ClassSerialzer.serialize(classObj);
		String tableName = ClassSerialzer.getTableName(classObj);

		try {
			conn = GojiraDBPool.getInstance().getConnection();
			ps = conn.prepareStatement(getInsertQueryTemplate(tableName));

			int count = 1;

			for (Entry<String, Object> entry : this.columnValueMap.entrySet()) {
				Object val = entry.getValue();
				if (val.getClass().equals(String.class))
					ps.setString(count, (String) val);
				else if (val.getClass().equals(Integer.class))
					ps.setInt(count, (Integer) val);
				else if (val.getClass().equals(Boolean.class))
					ps.setBoolean(count, (Boolean) val);

				count++;
			}

			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GojiraException(INFO_IDS.DYNAMIC_MSG, e.getMessage(), e.getErrorCode());
		} finally {
			GojiraDBPool.closeStatement(ps);
			GojiraDBPool.closeConnection(conn);
		}
	}

	public boolean delete(Object classObj) throws GojiraException {

		Connection conn = null;
		PreparedStatement ps = null;

		this.columnValueMap = ClassSerialzer.serialize(classObj);
		String tableName = ClassSerialzer.getTableName(classObj);

		try {

			conn = GojiraDBPool.getInstance().getConnection();
			ps = conn.prepareStatement(getDeleteQueryTemplate(tableName));

			int count = 1;

			for (Entry<String, Object> entry : this.columnValueMap.entrySet()) {
				Object val = entry.getValue();
				if (val.getClass().equals(String.class))
					ps.setString(count, (String) val);
				else if (val.getClass().equals(Integer.class))
					ps.setInt(count, (Integer) val);
				else if (val.getClass().equals(Boolean.class))
					ps.setBoolean(count, (Boolean) val);

				count++;
			}

			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GojiraException(INFO_IDS.DYNAMIC_MSG, e.getMessage(), e.getErrorCode());
		} finally {
			GojiraDBPool.closeStatement(ps);
			GojiraDBPool.closeConnection(conn);
		}
	}

	protected Object fetch(GqueryMapper gqueryMapper, Class<?> resultClass) throws GojiraException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Object resultObj = null;
		try {
			resultObj = resultClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		try {

			conn = GojiraDBPool.getInstance().getConnection();
			ps = conn.prepareStatement(gqueryMapper.getGQL());

			int count = 1;

			for (String value : gqueryMapper.preparedValues) {
				ps.setString(count, value);
				count++;
			}

			rs = ps.executeQuery();

			java.sql.ResultSetMetaData metaData = rs.getMetaData();
			int totalColumns = metaData.getColumnCount();
			Class<?> a = resultObj.getClass();

			if (rs.next()) {
				for (int i = 1; i <= totalColumns; i++) {
					try {
						Field field = a.getField(metaData.getColumnName(i));
						field.set(resultObj, rs.getString(metaData.getColumnName(i)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GojiraException(INFO_IDS.DYNAMIC_MSG, e.getMessage(), e.getErrorCode());
		} finally {
			GojiraDBPool.closeStatement(ps);
			GojiraDBPool.closeConnection(conn);
		}

		return resultObj;
	}

	public Object fetch(String nativeSQL, String[] valuesInOrder, Class<?> resultClass) throws GojiraException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Object resultObj = null;
		try {
			resultObj = resultClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		try {

			conn = GojiraDBPool.getInstance().getConnection();
			ps = conn.prepareStatement(nativeSQL);

			int count = 1;

			for (String value : valuesInOrder) {
				ps.setString(count, value);
				count++;
			}

			rs = ps.executeQuery();

			java.sql.ResultSetMetaData metaData = rs.getMetaData();
			int totalColumns = metaData.getColumnCount();
			Class<?> a = resultObj.getClass();

			if (rs.next()) {
				for (int i = 1; i <= totalColumns; i++) {
					try {
						Field field = a.getField(metaData.getColumnName(i));
						field.set(resultObj, rs.getString(metaData.getColumnName(i)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GojiraException(INFO_IDS.DYNAMIC_MSG, e.getMessage(), e.getErrorCode());
		} finally {
			GojiraDBPool.closeStatement(ps);
			GojiraDBPool.closeConnection(conn);
		}

		return resultObj;
	}

	protected List<Object> fetchAll(GqueryMapper gqueryMapper, Class<?> resultClass) throws GojiraException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Object> resultList = new ArrayList<Object>();

		try {

			conn = GojiraDBPool.getInstance().getConnection();
			ps = conn.prepareStatement(gqueryMapper.getGQL());

			int count = 1;

			for (String value : gqueryMapper.preparedValues) {
				ps.setString(count, value);
				count++;
			}

			rs = ps.executeQuery();

			java.sql.ResultSetMetaData metaData = rs.getMetaData();
			int totalColumns = metaData.getColumnCount();

			while (rs.next()) {

				Object resultObj = null;
				try {
					resultObj = resultClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

				Class<?> a = resultObj.getClass();
				for (int i = 1; i <= totalColumns; i++) {
					try {
						Field field = a.getField(metaData.getColumnName(i));
						field.set(resultObj, rs.getString(metaData.getColumnName(i)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				resultList.add(resultObj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GojiraException(INFO_IDS.DYNAMIC_MSG, e.getMessage(), e.getErrorCode());
		} finally {
			GojiraDBPool.closeStatement(ps);
			GojiraDBPool.closeConnection(conn);
		}

		return resultList;
	}

	public List<Object> fetchAll(String nativeSQL, String[] valuesInOrder, Class<?> resultClass)
			throws GojiraException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Object> resultList = new ArrayList<Object>();

		try {

			conn = GojiraDBPool.getInstance().getConnection();
			ps = conn.prepareStatement(nativeSQL);

			int count = 1;

			for (String value : valuesInOrder) {
				ps.setString(count, value);
				count++;
			}

			rs = ps.executeQuery();

			java.sql.ResultSetMetaData metaData = rs.getMetaData();
			int totalColumns = metaData.getColumnCount();

			while (rs.next()) {

				Object resultObj = null;
				try {
					resultObj = resultClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

				Class<?> a = resultObj.getClass();
				for (int i = 1; i <= totalColumns; i++) {
					try {
						Field field = a.getField(metaData.getColumnName(i));
						field.set(resultObj, rs.getString(metaData.getColumnName(i)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				resultList.add(resultObj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GojiraException(INFO_IDS.DYNAMIC_MSG, e.getMessage(), e.getErrorCode());
		} finally {
			GojiraDBPool.closeStatement(ps);
			GojiraDBPool.closeConnection(conn);
		}

		return resultList;
	}

	private String getErrorMessage(int errorCode) {
		switch (errorCode) {
		case 1062:
			return "";

		default:
			return "";
		}
	}

}
