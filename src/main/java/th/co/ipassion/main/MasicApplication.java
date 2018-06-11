package th.co.ipassion.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import th.co.ipassion.core.CoreApplication;
import th.co.ipassion.core.CoreRunInsert;
import th.co.ipassion.dto.ColumnDTO;
import th.co.ipassion.dto.TableDTO;
import th.co.ipassion.prop.PropCon;

public class MasicApplication {

	public static void main(String[] args) {
//		init
		String server = "TLServer9";
		List<File> files;
		
//		test
		MasicApplication m = new MasicApplication();
		
//		run
		CoreApplication c = new CoreApplication();
		File schemas = c.getFileSchema(server);
		HashMap<String, TableDTO> bases = c.getTableDetail(schemas);
		for (Map.Entry<String, TableDTO> entry : bases.entrySet()) {
			files = m.getFileInServer(server, entry.getValue());
			for (File file : files) {
				if (file.isFile()) m.insertDataBase(server, entry.getValue(), file);
			} 
		}
	}
	public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
	private void insertDataBase(String server,TableDTO table,File filedata) {
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(filedata);
			br = new BufferedReader(fr);
			int i = 0;
			String head = "";
			String value = "";
			String _line = "";
			List<String> values  = new ArrayList<>();
			List<Integer> lengths = new ArrayList<>();
			System.out.println("===============");
			System.out.println(table.getTable());
			System.out.println("===============");
//			==== set head and length ==== 
			for (ColumnDTO h : table.getColumns()) {
				head += (head=="") ? h.getColumn() : ","+h.getColumn();
				lengths.add(Integer.parseInt(h.getLength()));
			}
			while((_line = br.readLine()) != null) {
				value = "(";
				int _index = 0;
				for (int l : lengths) {
					String cell = _line.substring(_index, _index+l);
					value += (value == "(") ? "'"+cell.trim()+"'" : ",'"+cell.trim()+"'";
					_index += l;
				}value += ")";
				values.add(value);
				if ( (++i % 10000) == 0) {
					String label = "["+filedata.getName()+"] : "+i ;
					new Thread(new CoreRunInsert(label, table.getSchema()+"."+filedata.getName().split("\\.")[0],head,values)).start();
					Thread.sleep(500);
					values = new ArrayList<String>();
				}
			}
			new Thread(new CoreRunInsert(""+i , table.getSchema()+"."+filedata.getName().split("\\.")[0],head,values)).start();
		} catch (FileNotFoundException e) {
			System.out.println("file not found ... !!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("file not readLine ... !!");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException not success ... !!");
			e.printStackTrace();
		}
		
	}
	
	private List<File> getFileInServer(String server, TableDTO table) {
		String homeDir = PropCon.get("dir.home")+PropCon.get("dir.data").replace("@_SERVER", server);
		List<File> files = new ArrayList<>();
		if(table.getTable().split("\\.")[1].contains("#") || table.getTable().split("\\.")[1].contains("?")) {
			for(File file : new File(homeDir).listFiles()) {
				String _table = table.getTable().split("\\.")[1];
				String _file = file.getName().split("\\.")[0];
				if(_file.startsWith(_table.replace("#", "").replace("?", "")) && _file.length() == _table.length()) {
					files.add(file);
				}
			}	
		}else
			files.add(new File(homeDir+"/"+table.getTable().split("\\.")[1]+".dat"));
		return files;
	}
	
}
