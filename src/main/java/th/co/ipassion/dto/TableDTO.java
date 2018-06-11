package th.co.ipassion.dto;

import java.util.List;

public class TableDTO {
	String schema;
	String table;
	List<ColumnDTO> columns;
	
	public TableDTO() {
		super();
	}
	
	
	
	public TableDTO(String schema, String table) {
		super();
		this.schema = schema;
		this.table = table;
	}



	public TableDTO(String schema, String table, List<ColumnDTO> columns) {
		super();
		this.schema = schema;
		this.table = table;
		this.columns = columns;
	}

	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public List<ColumnDTO> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnDTO> columns) {
		this.columns = columns;
	}
	
	
}
