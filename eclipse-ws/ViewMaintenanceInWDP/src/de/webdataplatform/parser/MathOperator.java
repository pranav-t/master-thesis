package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.webdataplatform.system.TableRecord;

public class MathOperator implements ITerm{

	
	protected ITerm leftTerm;
	
	protected ITerm rightTerm;

	public MathOperator(ITerm leftTerm) {
		super();
		this.leftTerm = leftTerm;

	}
	
	public MathOperator(ITerm leftTerm, ITerm rightTerm) {
		super();
		this.leftTerm = leftTerm;
		this.rightTerm = rightTerm;
	}


	
	

	public ITerm getLeftTerm() {
		return leftTerm;
	}

	public void setLeftTerm(ITerm leftTerm) {
		this.leftTerm = leftTerm;
	}

	public ITerm getRightTerm() {
		return rightTerm;
	}

	public void setRightTerm(ITerm rightTerm) {
		this.rightTerm = rightTerm;
	}


	@Override
	public List<ColumnName> getBaseColumns() {
		List<ColumnName> result = new ArrayList<ColumnName>();
		result.addAll(leftTerm.getBaseColumns());
		result.addAll(rightTerm.getBaseColumns());
		return result;
	}

	@Override
	public Object eval(TableRecord tableRecord) throws Exception {

		return 0;
	}

	@Override
	public Object evalGrouping(List<TableRecord> tableRecord) throws Exception {
	
		return 0;
	}




	

}
