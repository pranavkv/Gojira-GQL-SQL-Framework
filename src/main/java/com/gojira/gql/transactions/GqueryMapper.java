package com.gojira.gql.transactions;

import java.util.ArrayList;

import com.gojira.gql.exception.GojiraException;

/**
* This class provides functions to perform all the SQL matching functions with the ease of classes 
* @author  Pranav k.v
* @version 1.0.0
* @since   2021-05-05 
*/

public class GqueryMapper extends GQuery {

	private String gql = "";

	protected ArrayList<String> preparedValues;

	public GqueryMapper() {
		preparedValues = new ArrayList<String>();
	}

	protected String getGQL() {
		return this.gql;
	}

	protected void setGQL(String initGql) {
		this.gql = initGql;
	}

	public GqueryMapper limit(int count) {
		this.gql = this.gql + " LIMIT " + count;
		return this;
	}

	public GqueryMapper and() {
		this.gql = this.gql + " AND ";
		return this;
	}

	public GqueryMapper where() {
		this.gql = this.gql + " WHERE ";
		return this;
	}

	public Object fetch(Class<?> resultClass) throws GojiraException {
		return super.fetch(this, resultClass);
	}
	
	public Object fetchAll(Class<?> resultListClass) throws GojiraException {
		return super.fetchAll(this, resultListClass);
	}

	public GqueryMapper or() {
		this.gql = this.gql + " OR ";
		return this;
	}

	public GqueryMapper equals(String field, String value) {
		this.gql = this.gql + " " + field + " = " + "?";
		this.preparedValues.add(value);
		return this;
	}

	public GqueryMapper notEquals(String field, String value) {
		this.gql = this.gql + " " + field + " <> " + "?";
		this.preparedValues.add(value);
		return this;
	}

	public GqueryMapper greater(String field, String value) {
		this.gql = this.gql + " " + field + " > " + "?";
		this.preparedValues.add(value);
		return this;
	}

	public GqueryMapper lesser(String field, String value) {
		this.gql = this.gql + " " + field + " < " + "?";
		this.preparedValues.add(value);
		return this;
	}

	public GqueryMapper greaterOrEqual(String field, String value) {
		this.gql = this.gql + " " + field + " >= " + "?";
		this.preparedValues.add(value);
		return this;
	}

	public GqueryMapper lesserOrEqual(String field, String value) {
		this.gql = this.gql + " " + field + " <= " + "?";
		this.preparedValues.add(value);
		return this;
	}

	public GqueryMapper set(LogicANDExpression LAE) {
		this.gql = this.gql + LAE.gql + ") ";
		this.preparedValues.addAll(LAE.preparedValues);
		return this;
	}

	public GqueryMapper set(LogicORExpression LOE) {
		this.gql = this.gql + LOE.gql + ") ";
		this.preparedValues.addAll(LOE.preparedValues);
		return this;
	}
}
