package com.gojira.gql.transactions;

import java.util.ArrayList;

/**
* This class provides functions to perform all the SQL matching functions with the ease of classes 
* @author  Pranav k.v
* @version 1.0.0
* @since   2021-05-05 
*/

public class LogicANDExpression extends GqueryMapper {

	protected String gql = "(";

	public LogicANDExpression() {
		this.preparedValues = new ArrayList<String>();
	}

	protected ArrayList<String> preparedValues;

	private void frameGQL(String gql) {
		if (!this.gql.endsWith("("))
			this.gql = this.gql + " AND ";

		this.gql = this.gql + gql;
	}

	public LogicANDExpression equals(String field, String value) {
		frameGQL(field + " = " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicANDExpression notEquals(String field, String value) {
		frameGQL(field + " <> " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicANDExpression greater(String field, String value) {
		frameGQL(field + " > " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicANDExpression lesser(String field, String value) {
		frameGQL(field + " < " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicANDExpression greaterOrEqual(String field, String value) {
		frameGQL(field + " >= " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicANDExpression lesserOrEqual(String field, String value) {
		frameGQL(field + " <= " + "?");
		this.preparedValues.add(value);
		return this;
	}

}
