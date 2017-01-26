package de.webdataplatform.basetable;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.message.SystemID;
import de.webdataplatform.system.DeltaRecord;
import de.webdataplatform.system.TableRecord;

public class BaseTableUpdate {


	
	
//	private BaseTableOperation baseOperation;
	
	
	private String operationType;
	
	private TableRecord newBaseRecord;
	
	private TableRecord oldBaseRecord;
	
	private SystemID sender;
	
	
	
	public BaseTableUpdate() {
		super();

	}


	public BaseTableUpdate(BaseTableOperation bto) {
		super();
		
		TableRecord tableRecord = new TableRecord(bto.getBaseTable(), bto.getKey(), bto.getColumns(), bto.getColFamilies());
		String type = bto.getType();
		
		
		if(DeltaRecord.isDeltaRecord(tableRecord)){
			
			DeltaRecord deltaRecord = new DeltaRecord(tableRecord);
			newBaseRecord = deltaRecord.getNewRecord();
			oldBaseRecord = deltaRecord.getOldRecord();
			
		}else{
			if(type.equals("Put")){
				newBaseRecord = tableRecord;
			}
			if(type.equals("Delete") || type.equals("DeleteColumn") || type.equals("DeleteFamily")){
				oldBaseRecord=tableRecord;
			}
		}
		operationType = type;
		this.sender = bto.getSender();
	}

	
	public BaseTableUpdate copy(){
		
		BaseTableUpdate btu = new BaseTableUpdate();
		
		btu.operationType= new String(this.operationType);
		
		if(this.newBaseRecord!= null)
		btu.newBaseRecord=this.newBaseRecord.copy();
		else btu.newBaseRecord=null;
		
		if(this.oldBaseRecord!= null)
		btu.oldBaseRecord=this.oldBaseRecord.copy();
		else btu.oldBaseRecord=null;
		
		btu.setSender(sender);
		
//		btu.baseOperation = this.baseOperation.copy();
//		btu.viewRecord = this.viewRecord.copy();

		
		return btu;
		
	}


	public TableRecord getRecord(){
		
		
		if(operationType.equals("Put")){
			return newBaseRecord;
		}
		
		if(operationType.equals("Delete") || operationType.equals("DeleteColumn") || operationType.equals("DeleteFamily")){
			
			if(oldBaseRecord != null)return oldBaseRecord;
			else return newBaseRecord;
		}
			
		return null;
	}

	public void setRecord(String type, TableRecord tableRecord){
		
		if(DeltaRecord.isDeltaRecord(tableRecord)){
			
			DeltaRecord deltaRecord = new DeltaRecord(tableRecord);
			newBaseRecord = deltaRecord.getNewRecord();
			oldBaseRecord = deltaRecord.getOldRecord();
			
		}else{
			if(type.equals("Put")){
				newBaseRecord = tableRecord;
				oldBaseRecord = null;
			}
			if(type.equals("Delete") || type.equals("DeleteColumn") || type.equals("DeleteFamily")){
				oldBaseRecord=tableRecord;
				newBaseRecord=null;
			}
		}	
	
		operationType = type;
	}
	
	
	
	public boolean isUpdate(){
		
		return (operationType.equals("Put")&& oldBaseRecord != null);
	}
	
	public TableRecord getDeltaRecord(){
		
		DeltaRecord deltaRecord = new DeltaRecord(newBaseRecord, oldBaseRecord);
		
		return deltaRecord.transform();
		
	}

	
	
	public List<BaseTableUpdate> splitUpdate(){
		
		List<BaseTableUpdate> result = new ArrayList<BaseTableUpdate>();
		
		if(this.isUpdate()){
			BaseTableUpdate btu1 = this.copy();
			btu1.setOperationType("Delete");
			btu1.setBaseRecord(null);
			
			BaseTableUpdate btu2 = this.copy();
			btu2.setOperationType("Put");
			btu2.setOldBaseRecord(null);
	
			result.add(btu1);
			result.add(btu2);

		return result;
		}else return null;
	}

	public void setBaseRecord(TableRecord tableRecord){
		
		newBaseRecord = tableRecord;
		

	}
	

	public TableRecord getBaseRecord(){
		
		
		return newBaseRecord;
		
	}


	public String getOperationType() {
		return operationType;
	}


	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}


	public TableRecord getOldBaseRecord() {
		return oldBaseRecord;
	}


	public void setOldBaseRecord(TableRecord oldBaseRecord) {
		this.oldBaseRecord = oldBaseRecord;
	}


	public SystemID getSender() {
		return sender;
	}


	public void setSender(SystemID sender) {
		this.sender = sender;
	}


	@Override
	public String toString() {
		return "BaseTableUpdate [operationType=" + operationType
				+ ", newBaseRecord=" + newBaseRecord + ", oldBaseRecord="
				+ oldBaseRecord + ", sender=" + sender + "]";
	}



	
	
	
	


	






	
	



	
	
	
	


	
	

}
