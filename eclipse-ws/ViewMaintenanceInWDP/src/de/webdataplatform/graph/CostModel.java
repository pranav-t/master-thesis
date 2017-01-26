package de.webdataplatform.graph;

import java.util.List;
import java.util.Map;

import de.webdataplatform.view.ViewTable;

public class CostModel {


	
	public static float PUT_WEIGHT = 1f;
	public static float GET_WEIGHT = 1f;
	public static float DELETE_WEIGHT = 1f;
	
	public static float OPERATION_WEIGHT = 0.5f;
	public static float STORAGE_WEIGHT = 0.5f;
	
	
	/**
	 * Computes the update flow in the graph, given all input rates of base tables
	 * @param graph
	 * @param inputRates
	 */
	public static void computeFlow(Graph graph, Map<String, Integer> inputRates){
		

		for (Node node : GraphUtil.kahnsAlgorithm(graph)) {
			

			int inputFlow = 0;
			
			if(graph.ingoingEdges(node).size() == 0){
				TableNode tableNode = (TableNode)node;
				inputFlow = inputRates.get(tableNode.getTable().getTableName());
				for (Edge edge : graph.outgoingEdges(node)) {
					edge.setFlow(inputFlow);
				}
			}
			
			if(node instanceof OperationNode){
				
				for (Edge edge : graph.ingoingEdges(node)) {
					
					inputFlow += edge.getFlow();

				}
				
				
				OperationNode operationNode = (OperationNode)node;

				
				// compute output flow of operation
				int outputFlow = operationNode.getOperation().computeFlow(inputFlow);
				
				for (Edge edge : graph.outgoingEdges(node)) {
					edge.setFlow(outputFlow);
				}

				
			}
			
			if(node instanceof TableNode && ((TableNode) node).getTable() instanceof ViewTable){
				

				TableNode tableNode =(TableNode)node;
				
				// Compute flow
				Edge ingoingEdge = graph.ingoingEdges(tableNode).get(0);
				
				for (Edge edge : graph.outgoingEdges(node)) {
					edge.setFlow(ingoingEdge.getFlow());
				}

				
				// Compute cost
				Node operationNode = graph.ingoingOperationNodes(tableNode).get(0);
				
				inputFlow=0;
				for (Edge edge : graph.ingoingEdges(operationNode)) {
					
					inputFlow += edge.getFlow();

				}
				
				int cost = ((OperationNode)operationNode).getOperation().computeCost(inputFlow);
				node.setCost(cost);
					
			}
			

			
		}
		
		
	}
	
	
	
	
	
//	/**
//	 * Computes the cost of the graph, given the flow has been computed before.
//	 * @param graph
//	 */
//	public static void computeCost(Graph graph){
//		
//
//		for (Node node : GraphUtil.kahnsAlgorithm(graph)) {
//			
//			List<Edge> ingoingEdges = graph.ingoingEdges(node);
//			int nodeFlow = 0;
//			if(graph.ingoingEdges(node).size() == 0){
//				nodeFlow = 100;
//			}
//			for (Edge edge : ingoingEdges) {
//
//				nodeFlow += edge.getFlow();
//			}
//			
//			if(node instanceof OperationNode){
//				
//				OperationNode operationNode = (OperationNode)node;
//				nodeFlow = operationNode.getOperation().computeFlow(nodeFlow);
//			}
//			if(node instanceof TableNode && ((TableNode) node).getTable() instanceof ViewTable){
//				
////				int minCost = Integer.MAX_VALUE;
//				Edge minEdge = null;
//				for (Edge edge : ingoingEdges) {
//					if(minEdge == null || minEdge.getFlow()>edge.getFlow()){
//						minEdge = edge;
//					}
//				}
//				int cost = ((OperationNode)minEdge.getSource()).getOperation().computeCost(minEdge.getFlow());
//				((TableNode) node).setCost(cost);
//				
//			}
//			
//			List<Edge> outgoingEdges = graph.outgoingEdges(node);
//			for (Edge edge : outgoingEdges) {
//				edge.setFlow(nodeFlow);
//			}
//			
//		}
//		
//		
//	}
	
	
	
	
}
