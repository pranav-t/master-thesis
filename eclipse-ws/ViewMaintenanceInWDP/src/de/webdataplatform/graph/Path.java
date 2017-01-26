package de.webdataplatform.graph;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.view.operation.ViewOperation;

public class Path {


	
	
	
	private ViewOperation operation;
	
	private List<Node> sources= new ArrayList<Node>();
	
	private List<Node> destinations=new ArrayList<Node>();
	
	
	
	
	
	
	

	public Path(ViewOperation operation, List<Edge> ingoing, List<Edge> outgoing) {
		super();
		this.operation = operation;
		
		for (Edge edge : ingoing) {
			sources.add(edge.getSource());
		}
		
		for (Edge edge : outgoing) {
			destinations.add(edge.getDestination());
		}
	}







	public ViewOperation getOperation() {
		return operation;
	}

	public void setOperation(ViewOperation operation) {
		this.operation = operation;
	}

	public List<Node> getSources() {
		return sources;
	}

	public void setSources(List<Node> sources) {
		this.sources = sources;
	}

	public List<Node> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Node> destinations) {
		this.destinations = destinations;
	}

















	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destinations == null) ? 0 : destinations.hashCode());
		result = prime * result + ((sources == null) ? 0 : sources.hashCode());
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
		Path other = (Path) obj;
		if (destinations == null) {
			if (other.destinations != null)
				return false;
		} else if (!destinations.equals(other.destinations))
			return false;
		if (sources == null) {
			if (other.sources != null)
				return false;
		} else if (!sources.equals(other.sources))
			return false;
		return true;
	}







	@Override
	public String toString() {
		return "OperationEnvironment [operation=" + operation + ", sources="
				+ sources + ", destinations=" + destinations + "]";
	}









	
	
	

	
	
	


	
	

	
	
	

}
