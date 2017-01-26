package de.webdataplatform.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.query.Cluster;

public class GraphUtil {

	/**
	 * Merges a number of graphs together. Just copies nodes and edges in one big graph
	 * No merging included.
	 * @param graphs
	 * @return
	 */
	public static Graph mergeGraphs(List<Graph> graphs){
		
		Graph result = new Graph();
		
		for (Graph graph : graphs) {
			
			result.getNodes().addAll(graph.getNodes());
			result.getEdges().addAll(graph.getEdges());
		}

		
		return result;
	}
	
	

	
	/**
	 * Merges a number of nodes. Uses the first node of 
	 * the list as orientation node. Removes all other
	 * nodes. 
	 * 
	 * @param graph
	 * @param nodes
	 */
	public static void mergeNodes(Graph graph, List<Node> nodes){
		
		int j = 0;
		Node orientationNode=null;
		
		if(nodes.size() < 2)return;
		
		for (Node node : nodes) {
			

			
			//Pick first node as orientation node
			if(j == 0){
				orientationNode = node;

			}else{
				
				graph.redirectIngoingEdges(node, orientationNode);
				graph.redirectOutgoingEdges(node, orientationNode);
				
				graph.removeNode(node);

			}
			
			j++;
		}
		GraphUtil.eliminateDuplicateEdges(graph, orientationNode);
		
		
	}
	

	
	

	
	
	
	
	
	/**
	 * Eliminates all duplicate edges adjacent to a given node 
	 * @param node
	 */
	public static void eliminateDuplicateEdges(Graph graph, Node node){
		
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(graph.ingoingEdges(node));
//		edges.addAll(graph.outgoingEdges(node));
		
		Map<Edge, List<Edge>> edgeCount=new HashMap<Edge, List<Edge>>();
		
		for (Edge edge : edges) {
			
			if(edgeCount.get(edge)==null){
				List<Edge> edgesTemp = new ArrayList<Edge>();
				edgesTemp.add(edge);
				edgeCount.put(edge, edgesTemp);
			}
			else edgeCount.get(edge).add(edge);
			
			
		}
		
//		System.out.println("edgeCount: "+edgeCount);
		
		for (Edge edge : edgeCount.keySet()) {
			
//			System.out.println("edge: "+edge);	
//			System.out.println("edge-hash: "+edge.hashCode());	
//			System.out.println("edge-source: "+edge.getSource());	
//			System.out.println("edge-dest: "+edge.getDestination());
//			if(edge.getSource()!=null)System.out.println("edge-source-hash: "+edge.getSource().hashCode());
//			System.out.println("edge-dest-hash: "+edge.getDestination().hashCode());
			
			
			List<Edge> edgesTemp = edgeCount.get(edge);
			
			if(edgesTemp.size() > 1){
				
				for (int i = 0; i < edgesTemp.size(); i++) {
					
					if(i != 0)graph.getEdges().remove(edgesTemp.get(i));
					
				}
				
			}
		}
		
		
	}
	
	

	   /**
	    * Computes a topology of the graph		
	    * @param graph
	    * @return
	    */
		public static List<Node> kahnsAlgorithm(Graph graph){
			
			
			List<Node> L = new ArrayList<Node>();
			Set<Node> S = graph.getStartingNodes();
			List<Edge> edges = graph.copyEdges();

			
			while(!S.isEmpty()){
				
				Iterator<Node> it = S.iterator();
				Node n = it.next();
				S.remove(n);
				L.add(n);
				for (Edge e : graph.outgoingEdges(n, edges)) {
					Node m = e.getDestination();
					edges.remove(e);
					if(graph.ingoingEdges(m, edges).size() == 0)
					S.add(m);
				}
				
			}
			return L;

			
			
		}
	

	


	

}
