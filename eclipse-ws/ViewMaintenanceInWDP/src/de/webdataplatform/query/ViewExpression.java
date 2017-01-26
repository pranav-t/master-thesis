package de.webdataplatform.query;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.graph.Node;
import de.webdataplatform.view.operation.ViewOperation;

public class ViewExpression {



	private List<ViewOperation> viewOperations=new ArrayList<ViewOperation>();
	
	private Node node;
	
	
	
	public ViewExpression(List<ViewOperation> viewExpression, Node tableNode) {
		
		super();
		this.viewOperations = viewExpression;
		this.node= tableNode;
	}



	
	public static int EQUAL=2;
	public static int SIMILAR=1;
	public static int NO_MATCH=0;
	
	
	public int compare(List<ViewOperation> viewExpression){


		if(this.viewOperations.size() != viewExpression.size()){
			return NO_MATCH; 
		}
		
		boolean isEqual=true;
		boolean isSimilar=true;
		
		int i = 0;
		for (ViewOperation viewOperation : this.viewOperations) {
			
			if(i != viewExpression.size()-1){
				if(!viewOperation.equals(viewExpression.get(i))){
					isEqual=false;
					isSimilar=false;
				}
			}else{
				if(!viewOperation.equals(viewExpression.get(i))){
					isEqual=false;
				}
				if(!viewOperation.similar(viewExpression.get(i))){
					isSimilar=false;
				}
			}
			i++;
			
		}
		if(isEqual)return EQUAL;
		if(isSimilar)return SIMILAR;
		return NO_MATCH; 
	}
	
	

	
	
	
	

	public Node getNode() {
		return node;
	}








	public void setNode(Node tableNode) {
		this.node = tableNode;
	}








	@Override
	public String toString() {
		return "ViewMapping [viewExpression=" + viewOperations + "-> tableNode="+ node + "]";
	}
	
	

	
	
	
	

}
