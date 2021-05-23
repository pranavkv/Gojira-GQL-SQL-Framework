package com.gojira.gql.transactions;

import java.util.ArrayList;

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
