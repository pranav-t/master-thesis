package de.webdataplatform.graph;

public class Edge {


	
	private Node source;
	private Node destination;
	
//	private Operation operation;
	
	private int flow;

	
	public Edge(Node source, Node destination) {
		super();
		this.source = source;
		this.destination = destination;
//		this.operation = operation;
		
	}
	


	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}

//	public Operation getOperation() {
//		return operation;
//	}
//
//	public void setOperation(Operation operation) {
//		this.operation = operation;
//	}


	


//	public void computeOutputFlow(){
//				
//		if(operation == null){
//			setOutputFlow(inputFlow);
//			return;
//		}
//		setOutputFlow(operation.computeFlow(inputFlow));
//	}
//	
//
	@Override
	public String toString() {
		return source+"-->"+destination;
	}
//



	public int getFlow() {
		return flow;
	}



	public void setFlow(int flow) {
		this.flow = flow;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()){
				
			return false;
		}
		Edge other = (Edge) obj;
		if (destination == null) {
			if (other.destination != null){
				return false;
			}
		} else if (destination != other.destination){
					
			return false;
		}
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (source != other.source){
						
			return false;
		}
		return true;
	}



	
	
//	
//	
	



}
