package th.co.ipassion.dto;

public class ColumnDTO {
	private String column;
	private String length;
	
	public ColumnDTO(String column, String length) {
		super();
		this.column = column;
		this.length = length;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	
}
