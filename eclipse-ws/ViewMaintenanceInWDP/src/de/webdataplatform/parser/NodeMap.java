package de.webdataplatform.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.graph.Node;

public class NodeMap {

	private Map<Set<String>, Node> map=new HashMap<Set<String>, Node>();
	
	public void putNode(String tableName, Node node){
		Set<String> tableNames = new HashSet<String>();
		tableNames.add(tableName);
		putNode(tableNames, node);
	}
	
	public void putNode(Set<String> tableNames, Node node){
		
		map.put(tableNames, node);
		
	}
	
	public void putAll(NodeMap nodeMap){
		
		this.map.putAll(nodeMap.getMap());
	}
	
	public Node getNode(String tableName){
		
		Set<String> tableNames = new HashSet<String>();
		tableNames.add(tableName);
		return getNode(tableNames);
	}
	
	public Node getNode(Set<String> tableNames){
		
		for (Set<String> temp : map.keySet()) {
			
			if(temp.containsAll(tableNames))return map.get(temp);
			
		}
		return null;
		
	}

	public Set<String> getTables(String tableName){
		
		Set<String> tableNames = new HashSet<String>();
		tableNames.add(tableName);
		return getTables(tableNames);
	}
	public Set<String> getTables(Set<String> tableNames){
		
		for (Set<String> temp : map.keySet()) {
			
			if(temp.containsAll(tableNames))return temp;
			
		}
		return null;
	}
	
	public void removeNode(Set<String> tableNames){
		
		map.remove(tableNames);
		
	}
	
	

	public Map<Set<String>, Node> getMap() {
		return map;
	}

	public void setMap(Map<Set<String>, Node> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "NodeMap [nodeMap=" + map + "]";
	}
	
	
	

}
