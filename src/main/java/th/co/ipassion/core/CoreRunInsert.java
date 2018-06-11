package th.co.ipassion.core;

import java.sql.Statement;
import java.util.List;

import th.co.ipassion.connection.ConnectionDB;

public class CoreRunInsert  implements Runnable {
	
	String label;
	String table;
	String header;
	List<String> values;
	
	public CoreRunInsert(String label, String table, String header, List<String> values) {
		super();
		this.label = label;
		this.table = table;
		this.header = header;
		this.values = values;
	}
	
	@Override
	public void run() {
		System.out.println("Process Runnable start "+values.size()+" : "+label);
		String sql = "";
		Statement stmt = null;
		String valueSet = "";
		int i = 0;
		try {
			ConnectionDB db = new ConnectionDB();
//			db.setAutoCommit(false);
			stmt = db.con.createStatement();
			for(String value : values) {
				valueSet += (valueSet=="") ? value : "," + value ;
				if( ++i % 1000  == 0) {
					sql = "insert into "+table+" values "+valueSet+";";
					stmt.addBatch(sql);
					stmt.executeBatch();
//					db.con.commit();
					valueSet = "";
				}
			}
			if(valueSet!="") {
				sql = "insert into "+table+" values "+valueSet+";";
				stmt.addBatch(sql);
				stmt.executeBatch();
//				db.con.commit();
				valueSet = "";
			}
			System.out.println("Process Runnable end "+label);
			stmt.close();
			db.closeDB();
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		}
	}
	
}
