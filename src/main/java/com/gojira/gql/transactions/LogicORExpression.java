package com.gojira.gql.transactions;

import java.util.ArrayList;

/**
* This class provides functions to perform all the SQL matching functions with the ease of classes 
* @author  Pranav k.v
* @version 1.0.0
* @since   2021-05-05 
*/

public class LogicORExpression extends GqueryMapper {

	protected String gql = "(";

	public LogicORExpression() {
		this.preparedValues = new ArrayList<String>();
	}

	protected ArrayList<String> preparedValues;

	private void frameGQL(String gql) {
		if (!this.gql.endsWith("("))
			this.gql = this.gql + " OR ";

		this.gql = this.gql + gql;
	}

	public LogicORExpression equals(String field, String value) {
		frameGQL(field + " = " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicORExpression notEquals(String field, String value) {
		frameGQL(field + " <> " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicORExpression greater(String field, String value) {
		frameGQL(field + " > " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicORExpression lesser(String field, String value) {
		frameGQL(field + " < " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicORExpression greaterOrEqual(String field, String value) {
		frameGQL(field + " >= " + "?");
		this.preparedValues.add(value);
		return this;
	}

	public LogicORExpression lesserOrEqual(String field, String value) {
		frameGQL(field + " <= " + "?");
		this.preparedValues.add(value);
		return this;
	}

}
