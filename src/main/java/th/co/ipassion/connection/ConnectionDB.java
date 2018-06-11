package th.co.ipassion.connection;

import java.sql.*;

import th.co.ipassion.prop.PropCon;

public class ConnectionDB {
	  // JDBC driver name and database URL
	   String JDBC_DRIVER = PropCon.get("db.driver");  
	   String DB_URL = PropCon.get("db.url")+":"+PropCon.get("db.port")+"/"+PropCon.get("db.base");
	   String USER = PropCon.get("db.user");
	   String PASS = PropCon.get("db.password");
	   public Connection con = null;
	   
	public ConnectionDB () {
		 try{
		      Class.forName(JDBC_DRIVER);
		      con = DriverManager.getConnection(DB_URL,USER,PASS);
		 }catch (Exception e) {
			System.out.println("erorr connection");
			e.printStackTrace();
		}
	}
	
	public void setAutoCommit(Boolean b) {
		try {
			con.setAutoCommit(b);
		} catch (SQLException e) {
			System.out.println("error auto commit");
			e.printStackTrace();
		}
	}
	public void closeDB() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Error close connection");
			e.printStackTrace();
		}
	}
		
}
