package de.webdataplatform.scripts;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.log.Log;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.EvaluationConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.settings.Task;
import de.webdataplatform.settings.TaskConfig;
import de.webdataplatform.settings.TaskFile;
import de.webdataplatform.settings.TaskFill;
import de.webdataplatform.ssh.LocalCommandService;

public class RunEvaluation {

	private static Log log;
	private static Log result_detail;
	private static Log result_summary;
	private static Log result_rs_detail;
	private static Log result_rs_summary;	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		log= new Log("run-evaluation.log");	
		result_detail= new Log("result-detailed.log");	
		result_summary= new Log("result-summary.log");	
		result_rs_detail= new Log("result-rs-detailed.log");	
		result_rs_summary= new Log("result-rs-summary.log");			
		SystemConfig.load(log);
		NetworkConfig.load(log);
		EvaluationConfig.load(log);
		TaskConfig.load(log);
		
		
		int x = 0;
//		for (String query : QueryConfig.QUERIES) {


			if(args.length == 2){
				
				Integer clients=Integer.parseInt(args[0]);
				Integer vms=Integer.parseInt(args[1]);
				
				initEvaluation();
				runEvaluation(1, 1, clients, vms);
			}
			if(args.length > 2){
	
				
				Integer clients=Integer.parseInt(args[0]);
				
				Integer start=Integer.parseInt(args[1]);
				Integer end=Integer.parseInt(args[2]);
				
				Integer stepWidth=1;
				
				if(args.length == 4)stepWidth=Integer.parseInt(args[3]);
				

				initEvaluation();
				
				int iterationNum = (end - start + stepWidth)/stepWidth;
				int iteration = 1;
				
				for (int i = start; i <= end; i+=stepWidth) {
	
					runEvaluation(iterationNum, iteration, clients, i);
					iteration++;
				}
			}
			

			
			cleanUpEvaluation(x);
			
			x++;
			
//		}
		
		
	}
	

	
	public static void initEvaluation(){
		
		
		for (String baseTable : EvaluationConfig.BASE_TABLES) {	
			
			log.info(RunEvaluation.class, "create base table "+baseTable);
			LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.client.BasetableClient create "+baseTable+" 24");
		}
		
		log.info(RunEvaluation.class, "starting master");
		LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.test.VMS startmaster ");
		
		try {
		log.info(RunEvaluation.class, "waiting for master to come up");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
	
			e.printStackTrace();
		}
	
		int x = 0;
		for (String query : EvaluationConfig.QUERIES) {	
			log.info(RunEvaluation.class, "loading query");
			LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.client.VMSClient add q"+x+" "+query);
			x++;
		
		}
		
//		fillBaseTables(numOfClients);

		
	}
	
	
	
	
	
	public static Integer computeStart(int tableCount, int iteration, int iterationNum){
		
		
		return (tableCount/iterationNum)*iteration-(tableCount/iterationNum);
		
		
		
	}
	
	public static Integer computeEnd(int tableCount, int iteration, int iterationNum){
		
		return (tableCount/iterationNum)*iteration;
		
	}
	
	public static Integer getOrdersCount(){
		return 1500000;
	}
	
	
	public static void fillBaseTables(final int iterationNum, final int iteration, final int clientNum){
		
		log.info(RunEvaluation.class, "filling table");
		
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < clientNum; i++) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			
		    final int x = i;
		    
			Thread thread = new Thread(){
				
				public void run(){
					for (String taskString : EvaluationConfig.TASKS) {	
						
						Task task = TaskConfig.getTask(taskString);
						log.info("Executing task:"+task);
						
						
						if(task == null)
							try {
								throw new Exception("Task :"+taskString+" not found");
							} catch (Exception e) {
								log.error(this.getClass(), e);
							}
						
						if(task instanceof TaskFile){
							int count = ((TaskFile)task).getCount();
							
							final int start = computeStart(count, iteration, iterationNum)+computeStart(count/iterationNum, x+1, clientNum);
							final int end = computeStart(count, iteration, iterationNum)+computeEnd(count/iterationNum, x+1, clientNum);	

							log.info("Executing: "+"java -cp vmsystem.jar de.webdataplatform.client.BasetableClient execute "+task.getName()+" start="+start+" end="+end);
							LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.client.BasetableClient execute "+task.getName()+" start="+start+" end="+end);
							
						}else{
							
							log.info("Executing: "+"java -cp vmsystem.jar de.webdataplatform.client.BasetableClient execute "+task.getName());
							LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.client.BasetableClient execute "+task.getName());
						}
						
					}	
					
				}
			};
			thread.start();
			threads.add(thread);
		}
		
		while(true){
			boolean finished=true;
			
			log.info(RunEvaluation.class, "check if finished");
			for (Thread thread : threads) {
				if(thread.isAlive()){
					finished=false;
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(finished)break;
			
		}
		


		
		log.info(RunEvaluation.class, "finished table filling");
	}
	
	
	public static void runEvaluation(int iterationNum, int iteration, int numOfClients, int numOfVMs) {


		
		log.info(RunEvaluation.class, "number of clients:"+numOfClients);
		log.info(RunEvaluation.class, "number of VMs:"+numOfVMs);
		
		
		log.info(RunEvaluation.class, "reloading queries");
		int x = 0;
		for (String query : EvaluationConfig.QUERIES) {	
			log.info(RunEvaluation.class, "loading query");
			LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.client.VMSClient reload q"+x+" "+query);
			x++;
		
		}

		fillBaseTables(iterationNum, iteration, numOfClients);
		

		log.info(RunEvaluation.class, "starting vms");
		LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.test.VMS start "+numOfVMs);
		
		try {
			log.info(RunEvaluation.class, "waiting for vms to finish");
			Thread.sleep(300000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		
		log.info(RunEvaluation.class, "stopping vms");
		LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.test.VMS stop "+numOfVMs);

		try {
			log.info(RunEvaluation.class, "waiting for vms to finish");
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		
		
		result_detail.info(RunEvaluation.class, "reading vms-client");
		LocalCommandService.runCommand(result_detail, "java -cp vmsystem.jar de.webdataplatform.client.VMSClient throughput detailed");
		result_detail.info(RunEvaluation.class, "--------------------------------------------");
		
		result_summary.info(RunEvaluation.class, "reading vms-client");
		LocalCommandService.runCommand(result_summary, "java -cp vmsystem.jar de.webdataplatform.client.VMSClient throughput summary");
		result_summary.info(RunEvaluation.class, "--------------------------------------------");

		result_rs_detail.info(RunEvaluation.class, "reading vms-client");
		LocalCommandService.runCommand(result_rs_detail, "java -cp vmsystem.jar de.webdataplatform.client.VMSClient throughput rs detailed");
		result_rs_detail.info(RunEvaluation.class, "--------------------------------------------");
		
		result_rs_summary.info(RunEvaluation.class, "reading vms-client");
		LocalCommandService.runCommand(result_rs_summary, "java -cp vmsystem.jar de.webdataplatform.client.VMSClient throughput rs summary");
		result_rs_summary.info(RunEvaluation.class, "--------------------------------------------");

		

		
	}
	
	public static void cleanUpEvaluation(int x){
		
		log.info(RunEvaluation.class, "copy log");
		LocalCommandService.runCommand(log, "cp /usr/local/scalablevm/logs/run-evaluation.log /usr/local/results/run-eval"+x+".log");
		
		log.info(RunEvaluation.class, "copy results");
		LocalCommandService.runCommand(log, "cp /usr/local/scalablevm/logs/result-detailed.log /usr/local/results/result-detail"+x+".log");
		LocalCommandService.runCommand(log, "cp /usr/local/scalablevm/logs/result-summary.log /usr/local/results/result-summary"+x+".log");
		LocalCommandService.runCommand(log, "cp /usr/local/scalablevm/logs/result-rs-detailed.log /usr/local/results/result-rs-detail"+x+".log");
		LocalCommandService.runCommand(log, "cp /usr/local/scalablevm/logs/result-rs-summary.log /usr/local/results/result-rs-summary"+x+".log");
		
		log.info(RunEvaluation.class, "stopping master");
		LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.test.VMS stopmaster");
		
			
		log.info(RunEvaluation.class, "loading query");
		LocalCommandService.runCommand(log, "java -cp vmsystem.jar de.webdataplatform.scripts.ManageHBase resethbase");
		
		log.info(RunEvaluation.class, "cleaning logs");
		LocalCommandService.runCommand(log, "rm /usr/local/scalablevm/logs/*");		


		
		
	}
	
	

}
