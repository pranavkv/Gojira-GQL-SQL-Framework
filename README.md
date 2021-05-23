# Gojira-GQL-SQL--Framework

#How to initialiaze

Load the configuration by passing the properties file like below

    try {
          AppConfig.loadFile("config.properties");
        } catch (ConfigurationException e) {
          System.out.println(e);
        }
    GojiraDBPool.initDataSource(AppConfig.getProperties());    
    
#1 - INSERT SNIPPET
   
   	Student std = new Student();
	  std.setAddress("something");
  	std.setId("26");
    std.setName("Nobody");

		GQuery qry = new GQuery();
		qry.add(std);


#2 - DELETE

    Student std = new Student();
	  std.setAddress("something");
  	std.setId("26");
    std.setName("Nobody");

		GQuery qry = new GQuery();
		qry.delete(std);
    
#3 - WHERE QUERY with Logical Expression

    LogicORExpression orExpr = new LogicORExpression()
		    .equals("id", "21")
		    .equals("id", "22");
		
		Student stud = (Student) new GQuery()
				.select("student")
				.where()
				.set(orExpr).fetch(Student.class);
	
 #4 - WHERE QUERY WITH LIST OF ROWS
 
     List<Object> list = new GQuery().select("student")
        .where()
        .equals("id", "21")
        .fetch(Student.class);
    
 #5 - NATIVE SELECT QUERY
    
     List<Object> list = new GQuery()
        .fetch("SELECT * FROM STUDENT WHERE name = ? AND adress LIKE ?", new String[] {"nobody","something"}, Student.class);
        
        
