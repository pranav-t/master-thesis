package de.webdataplatform.system;

import de.webdataplatform.settings.TableDefinition;

public abstract class Table {


	protected String tableName;

	protected TableDefinition tableDefinition;
	
	
	



	public Table(String tableName, TableDefinition tableDefinition) {
		super();
		this.tableName = tableName;
		this.tableDefinition = tableDefinition;
	}



	public String getTableName() {
		return tableName;
	}



	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	

	
	public TableDefinition getTableDefinition() {
		return tableDefinition;
	}



	public void setTableDefinition(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}



	public abstract String toString();
	
	public abstract String toExtendedString();



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}


	

}
