package gojira;

import com.gojira.gql.annotations.DbAnnotations.Column;
import com.gojira.gql.annotations.DbAnnotations.Default;
import com.gojira.gql.annotations.DbAnnotations.PrimaryKey;
import com.gojira.gql.annotations.DbAnnotations.Table;

@Table("student")
public class Student {

	@PrimaryKey
	@Column("id")
	public String id;

	@Column
	public String name;

	@Column
	@Default("sss")
	public String address;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return this.address;
	}

	public void setAddress(String addr) {
		this.address = addr;
	}

}
