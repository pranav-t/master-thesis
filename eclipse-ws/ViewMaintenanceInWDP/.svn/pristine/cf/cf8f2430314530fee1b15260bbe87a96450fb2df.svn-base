package de.webdataplatform.query;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.graph.Node;
import de.webdataplatform.view.operation.ViewOperation;

public class Cluster {


	
	private List<ViewExpression> viewExpressions=new ArrayList<ViewExpression>();

	
	
	public Cluster(List<ViewExpression> viewMappings) {
		super();
		this.viewExpressions = viewMappings;
	}
	

	public Cluster(ViewExpression viewMapping) {
		super();
		this.viewExpressions.add(viewMapping);
	}
	
	
	
	public List<ViewExpression> getViewExpressions() {
		return viewExpressions;
	}

	public void setViewMappings(List<ViewExpression> viewMappings) {
		this.viewExpressions = viewMappings;
	}

	public boolean compareAndAddOnEqual(List<ViewOperation> viewExpression, Node node){
	
		for (ViewExpression viewMapping : viewExpressions) {
			
			
			if(viewMapping.compare(viewExpression) == ViewExpression.EQUAL){
				viewExpressions.add(new ViewExpression(viewExpression,  node));
				return true;
			}
		}
		return false;
	
	}
	
	public boolean compareAndAddOnSimilar(List<ViewOperation> viewExpression, Node node){
		
		
		for (ViewExpression viewMapping : viewExpressions) {
			
			
			if(viewMapping.compare(viewExpression) == ViewExpression.SIMILAR){
				viewExpressions.add(new ViewExpression(viewExpression,  node));
				return true;
			}
		}
		return false;
	
	}
	
	
	

	public List<Node> getNodes(){
		
		List<Node> result = new ArrayList<Node>();
		
		
		for (ViewExpression viewMapping : viewExpressions) {
			result.add(viewMapping.getNode());
		}
		
		
		return result;
	}
	

	@Override
	public String toString() {
		
		String result="";
		for (ViewExpression viewMapping : viewExpressions) {
			
			result += viewMapping+"\n";
			
		}
		return result;
	}
	
	
	

}
