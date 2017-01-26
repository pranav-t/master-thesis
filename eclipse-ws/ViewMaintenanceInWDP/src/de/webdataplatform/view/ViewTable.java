package de.webdataplatform.view;

import java.util.List;

import de.webdataplatform.settings.TableDefinition;
import de.webdataplatform.system.Table;

public class ViewTable extends Table{


	private int numOfRecords;
	
	private float sizeOfRecord;
	
	private List<String> colFams;
	
	
	
//	private boolean rowKeyChanged;
	
//	private List<ViewOperation> viewExpression = new ArrayList<ViewOperation>();
	

	public ViewTable(String tableName, List<String> colFams, TableDefinition tableDefinition) {
		super(tableName, tableDefinition);
//		this.viewExpression = viewExpression;
		this.colFams = colFams;
//		this.rowKeyChanged = rowKeyChanged;
		
	}






//
//	public List<ViewOperation> getViewExpression() {
//		return viewExpression;
//	}
//
//
//
//	public void setViewExpression(List<ViewOperation> viewExpression) {
//		this.viewExpression = viewExpression;
//	}

	
	
	
//
//	public boolean isRowKeyChanged() {
//		return rowKeyChanged;
//	}
//
//
//
//	public void setRowKeyChanged(boolean rowKeyChanged) {
//		this.rowKeyChanged = rowKeyChanged;
//	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((colFams == null) ? 0 : colFams.hashCode());
		result = prime * result + numOfRecords;
		result = prime * result + Float.floatToIntBits(sizeOfRecord);
		return result;
	}






	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewTable other = (ViewTable) obj;
		if (colFams == null) {
			if (other.colFams != null)
				return false;
		} else if (!colFams.equals(other.colFams))
			return false;
		if (numOfRecords != other.numOfRecords)
			return false;
		if (Float.floatToIntBits(sizeOfRecord) != Float
				.floatToIntBits(other.sizeOfRecord))
			return false;
		return true;
	}






	public int getNumOfRecords() {
		return numOfRecords;
	}



	public void setNumOfRecords(int numOfRecords) {
		this.numOfRecords = numOfRecords;
	}



	public float getSizeOfRecord() {
		return sizeOfRecord;
	}



	public void setSizeOfRecord(float sizeOfRecord) {
		this.sizeOfRecord = sizeOfRecord;
	}



	public List<String> getColFams() {
		return colFams;
	}



	public void setColFams(List<String> colFams) {
		this.colFams = colFams;
	}



	public int computeOutputFlow(int inputFlow) {
		// TODO Auto-generated method stub
		return 0;
	}
	





	public String toString() {

		return tableName+", "+tableDefinition;
	}
	
	


	@Override
	public String toExtendedString() {

		return tableName;
	}	
	



}
