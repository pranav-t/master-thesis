package de.webdataplatform.view.operation;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.Node;
import de.webdataplatform.system.TableRecord;




public abstract class ViewOperation {

	protected String symbol;

	private boolean rowKeyChanged;

	public ViewOperation(String symbol, boolean rowKeyChanged) {
		super();

		this.rowKeyChanged = rowKeyChanged;
		this.symbol = symbol;
	}
	


	public abstract String toString();
	
	public abstract int computeFlow(int inputFlow);

	public abstract int computeCost(int inputFlow);

	public abstract int hashCode();

	public abstract boolean equals(Object obj);
	
	public abstract boolean similar(Object obj);

	public abstract String getViewRecordKey(BaseTableUpdate btu);
	
	public abstract String getViewRecordKey(TableRecord baseRecord);
	
	public abstract Node getSplitOperation();


	public String getSymbol() {
		return symbol;
	}



	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}



	public boolean isRowKeyChanged() {
		return rowKeyChanged;
	}



	public void setRowKeyChanged(boolean rowKeyChanged) {
		this.rowKeyChanged = rowKeyChanged;
	}





}
