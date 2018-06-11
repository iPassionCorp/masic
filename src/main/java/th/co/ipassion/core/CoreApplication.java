package th.co.ipassion.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import th.co.ipassion.dto.ColumnDTO;
import th.co.ipassion.dto.TableDTO;
import th.co.ipassion.prop.PropCon;

public class CoreApplication {
	
	public File getFileSchema(String server) {
		String dirHome = PropCon.get("dir.home");
		String dirFile = dirHome + PropCon.get("dir.sch").replace("@_SERVER", server);
		return new File(dirFile);
	}
	
	public HashMap<String, TableDTO> getTableDetail(File files) {
		String key = "";
		String schema = "";
		String column = "";
		String length = "";
		List<ColumnDTO> columns = null;
		HashMap<String, TableDTO> bases = new LinkedHashMap<String, TableDTO>();
		FileReader fr = null;
		BufferedReader bf = null;
		for (File file : files.listFiles()) {
			schema = file.getName().split("\\.")[0];
			try {
				fr = new FileReader(file);
				bf = new BufferedReader(fr);
				String line = null;
				while ((line = bf.readLine()) != null) {
					if (line.contains("@") && !line.matches("(.*)(.@)(.*)")) {
						if (!bases.isEmpty())
							bases.get(key).setColumns(columns);
						key = schema+"."+line.split("\\s+")[0].replace("@", "");
						columns = new ArrayList<>();
						bases.put(key, new TableDTO(schema, key.replace("@", "")));
					} else if (line.contains("text") || line.matches("(.*)(number)(.\\d+|\\D+)(.*)")) {
						String lineFix = line.trim().replaceAll("\\s+", "|");
						column = lineFix.split("\\|")[0];
						length = (lineFix.split("\\|")[2].contains(",")) ? 
								lineFix.split("\\|")[2].split(",")[0] : lineFix.split("\\|")[2];
						columns.add(new ColumnDTO(column, length));
					}
				}
				bases.get(key).setColumns(columns);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bases;
	}
}
