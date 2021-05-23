package com.gojira.gql.transactions;

import java.util.ArrayList;

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
