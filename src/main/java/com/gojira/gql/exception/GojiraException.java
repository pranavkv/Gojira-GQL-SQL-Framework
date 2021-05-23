package com.gojira.gql.exception;

public class GojiraException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String infoId;

	private int sqlCode;

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public GojiraException(String infoID, String message, Throwable cause) {
		super(message, cause);
		this.setInfoId(infoID);
	}

	public GojiraException(String infoID, String message) {
		super(message);
		this.setInfoId(infoID);
	}

	public GojiraException(String infoID, String message, int sqlCode) {
		super(message);
		this.setInfoId(infoID);
		this.sqlCode = sqlCode;
	}

	public GojiraException(String infoID) {
		this.setInfoId(infoID);
	}

	public int getSqlCode() {
		return sqlCode;
	}

	public void setSqlCode(int sqlCode) {
		this.sqlCode = sqlCode;
	}
}
