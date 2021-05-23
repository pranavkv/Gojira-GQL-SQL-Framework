
package gojira;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import com.gojira.gql.common.AppConfig;
import com.gojira.gql.db.GojiraDBPool;
import com.gojira.gql.exception.GojiraException;
import com.gojira.gql.transactions.GQuery;
import com.gojira.gql.transactions.LogicORExpression;

public class Test {

	public static void main(String[] args) throws GojiraException {

		try {
			AppConfig.loadFile("config.properties");
		} catch (ConfigurationException e) {
			System.out.println(e);
		}

		GojiraDBPool.initDataSource(AppConfig.getProperties());
//		Student std = new Student();
//		std.setAddress("44444");
//		std.setId("26");
//		std.setName("ss");
//
//		GQuery qry = new GQuery();
//		qry.add(std);

//		HibernateDBPool.initDataSource(AppConfig.getProperties());
//		Student std = new Student();
//		std.setAddress("44444");
//		std.setId("23");
//		std.setName("ss");
//
//		GQuery qry = new GQuery();
//		qry.delete(std);

		LogicORExpression orExpr = new LogicORExpression()
			    .equals("id", "21")
				.equals("id", "22");
		
		Student stud = (Student) new GQuery()
				.select("student")
				.where()
				.set(orExpr).fetch(Student.class);
		
		List<Object> list =  new GQuery()
				.fetchAll("SELECT * FROM STUDENT WHERE name = ? AND adress LIKE ?", new String [] {"nobody","something"}, Student.class);             
		
//		Student obj = (Student) GQ.select("student").where().equals("id", "21").fetch(Student.class);
		
//		Student obj = (Student) GQ.select("student").where().equals("id", "21").fetch(Student.class);
//		System.out.println(obj.getId());
	}
}
