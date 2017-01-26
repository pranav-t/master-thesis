package de.webdataplatform.query;


import de.webdataplatform.graph.Graph;

public class Query {


	
	
	private Graph queryDAG= new Graph();
	
	private String queryName;
	
	private String queryString;
	
	
	
	public Query(String queryName, String queryString) {
		super();
		this.queryName = queryName;
		this.queryString = queryString;
	}

	
	
	
	
	
	
	
	
	
	public Query(String queryName, Graph queryDAG) {
		super();
		this.queryDAG = queryDAG;
		this.queryName = queryName;
	}










	public Graph getQueryDAG() {
		return queryDAG;
	}

	public void setQueryDAG(Graph queryDAG) {
		this.queryDAG = queryDAG;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
















	@Override
	public String toString() {
		return "Query [queryName=" + queryName + ", queryString=" + queryString
				+ "]";
	}










	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((queryName == null) ? 0 : queryName.hashCode());
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
		Query other = (Query) obj;
		if (queryName == null) {
			if (other.queryName != null)
				return false;
		} else if (!queryName.equals(other.queryName))
			return false;
		return true;
	}
	
	
	
	

	


	
	
	
	

	
	
}
