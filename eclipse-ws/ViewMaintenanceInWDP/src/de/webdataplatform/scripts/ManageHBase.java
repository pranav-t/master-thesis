package de.webdataplatform.scripts;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.log.Log;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.ssh.LocalCommandService;
import de.webdataplatform.ssh.SSHConnection;
import de.webdataplatform.ssh.SSHService;

public class ManageHBase {

	
	private static Log log;
	
	
	public static void main(String[] args) {

		log= new Log("manage-hbase.log");	
		
		SystemConfig.load(log);
		NetworkConfig.load(log);
		
		List<SSHConnection> nodes = new ArrayList<SSHConnection>();
		
//		nodes.add(new SSHConnection("jan-1", "172.24.36.217",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-client", "172.24.34.133",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-master", "172.24.34.108",22, "ubuntu", null));
//		
		nodes.add(new SSHConnection("jan-zk-1", "172.24.37.121",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-zk-2", "172.24.37.122",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-zk-3", "172.24.37.123",22, "ubuntu", null));
//		
//		
//		
		nodes.add(new SSHConnection("jan-rs-1", "172.24.34.104",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-2", "172.24.34.107",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-3", "172.24.34.105",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-4", "172.24.34.106",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-5", "172.24.35.207",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-6", "172.24.35.208",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-rs-7", "172.24.35.231",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-8", "172.24.35.232",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-9", "172.24.36.207",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-10", "172.24.36.208",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-rs-11", "172.24.36.209",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-12", "172.24.36.210",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-rs-13", "172.24.37.87",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-14", "172.24.37.88",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-rs-15", "172.24.37.89",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-16", "172.24.37.90",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-rs-17", "172.24.37.105",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-18", "172.24.37.106",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-rs-19", "172.24.37.107",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-rs-20", "172.24.37.108",22, "ubuntu", null));			
		

		
		
//		nodes.add(sshConnection);
		if(args.length== 0)log.info(ManageHBase.class, "Please supply arguments: config, kill , cleanlogs, cleanhadoop");

		if(args[0].equals("config")){
//			writeHadoopConfig(nodes, "/usr/local/hadoop-1.2.1/conf/");
			writeHBaseConfig(nodes, "/usr/local/hbase-0.98.6.1-hadoop1/conf/");
		}
		if(args[0].equals("copyconfig")){
			
			copyConfig(nodes);
		}
		
		if(args[0].equals("kill")){
			
			killHBase(nodes);
		}
		
		if(args[0].equals("cleanlogs")){
			
			cleanLogs(nodes);
		}		
		
		if(args[0].equals("cleanhadoop")){
			
			cleanHadoop(nodes);
		}
		if(args[0].equals("resethbase")){
			
			resetHBase(nodes);
		}
		SSHService.closeSessions();		
		System.exit(0);
		
	}
	
	public static void writeHadoopConfig(List<SSHConnection> nodes, String hadoopConfigPath) {
		
		List<String> startupCommand;
		
		SSHConnection firstNode = nodes.get(0);
		for (SSHConnection node1 : nodes) {
			
			startupCommand = new ArrayList<String>();
			startupCommand.add("sed -i ':a;N;$!ba;s@<name>fs.default.name</name>\\s*<value>[A-Za-z0-9.,-:/]*</value>@<name>fs.default.name</name>\\n\\t\\t<value>hdfs://"+firstNode.getAddress()+"</value>@' "+hadoopConfigPath+"core-site.xml");
			SSHService.sendCommand(log, node1, startupCommand, false);
			


			
			boolean firstWrite=true;
			for (SSHConnection node2 : nodes) {
					if(!node2.equals(firstNode)){
						if(firstWrite){
							firstWrite=false;
							startupCommand = new ArrayList<String>();
							startupCommand.add("sudo  sh -c \"echo '"+node2.getAddress()+ "' > "+hadoopConfigPath+"slaves\"");
							SSHService.sendCommand(log, node1, startupCommand,false);
						}else{
							startupCommand = new ArrayList<String>();
							startupCommand.add("sudo  sh -c \"echo '"+node2.getAddress()+ "' >> "+hadoopConfigPath+"slaves\"");
							SSHService.sendCommand(log, node1, startupCommand,false);
							
						}
					}	
				
			}
			
			

		}
		
		
		
		
	}
	
	public static void copyConfig(List<SSHConnection> nodes){
			
			List<String> startupCommand;

			for (SSHConnection node : nodes) {
				
			
				log.info(InstallNodes.class, "Deleting config folder");
				startupCommand = new ArrayList<String>();
				startupCommand.add("sudo rm -r /usr/local/hbase-0.98.6.1-hadoop1/conf");
				SSHService.sendCommand(log, node, startupCommand, false);	
				
				log.info(InstallNodes.class, "transfering config files");
				LocalCommandService.transferDirectory(log, node, "/usr/local/hbase-0.98.6.1-hadoop1/conf", "/usr/local/hbase-0.98.6.1-hadoop1/conf");
			
				
				
			}
			
			SSHService.closeSessions();

	}
	

	public static void writeHBaseConfig(List<SSHConnection> nodes, String hbaseConfigPath) {
		
		List<String> startupCommand;
		
		SSHConnection firstNode = nodes.get(0);
		
		for (SSHConnection node1 : nodes) {
			
//			startupCommand = new ArrayList<String>();
//			startupCommand.add("sed -i ':a;N;$!ba;s@<name>hbase.rootdir</name>\\s*<value>[A-Za-z0-9.,-:/]*</value>@<name>hbase.rootdir</name>\\n\\t\\t<value>hdfs://"+firstNode.getAddress()+":8020/hbase</value>@' "+hbaseConfigPath+"hbase-site.xml");
//			SSHService.sendCommand(log, node1, startupCommand, false);
	
			startupCommand = new ArrayList<String>();
			startupCommand.add("sed -i ':a;N;$!ba;s@<name>hbase.zookeeper.quorum</name>\\s*<value>[A-Za-z0-9.,-:/]*</value>@<name>hbase.zookeeper.quorum</name>\\n\\t\\t<value>172.24.37.121, 172.24.37.122, 172.24.37.123</value>@' "+hbaseConfigPath+"hbase-site.xml");
			SSHService.sendCommand(log, node1, startupCommand, false);			

//			boolean firstWrite=true;
//			for (SSHConnection node2 : nodes) {
//					if(!node2.equals(firstNode)){
//					
//						if(firstWrite){
//							firstWrite=false;
//							startupCommand = new ArrayList<String>();
//							startupCommand.add("sudo  sh -c \"echo '"+node2.getAddress()+ "' > "+hbaseConfigPath+"regionservers\"");
//							SSHService.sendCommand(log, node1, startupCommand,false);
//						}else{
//							startupCommand = new ArrayList<String>();
//							startupCommand.add("sudo  sh -c \"echo '"+node2.getAddress()+ "' >> "+hbaseConfigPath+"regionservers\"");
//							SSHService.sendCommand(log, node1, startupCommand,false);
//						}
//						
//					}
//				
//			}
			
			

		}
		
		
		
	}
	
	public static void killHBase(List<SSHConnection> nodes) {
		
		
		List<String> startupCommand;
		
		
		for (SSHConnection node : nodes) {
			
			
			log.info(ManageHBase.class, "killing process:"+node);
			startupCommand = new ArrayList<String>();
			startupCommand.add("sudo pkill -9 -f hbase");
			SSHService.sendCommand(log, node, startupCommand, false);
			
			
		}
	}
	public static void killHadoop(List<SSHConnection> nodes) {
		
		
		List<String> startupCommand;
		
		
		for (SSHConnection node : nodes) {
			
			
			log.info(ManageHBase.class, "killing process:"+node);
			startupCommand = new ArrayList<String>();
			startupCommand.add("sudo pkill -9 -f hadoop");
			SSHService.sendCommand(log, node, startupCommand, false);
			
			
		}
	}

	
	
	
	
	public static void cleanHadoop(List<SSHConnection> nodes) {
		
		
		List<String> startupCommand;
		
		SSHConnection firstNode = nodes.get(0);
		
		for (SSHConnection node : nodes) {
			
			if(!node.equals(firstNode)){
				log.info(ManageHBase.class, "cleaning data node:"+node);
				startupCommand = new ArrayList<String>();
				startupCommand.add("sudo rm -r /usr/local/hadoop/*");
				SSHService.sendCommand(log, node, startupCommand, false);
			}
			
		}
		

		
	}	
	
	
	public static void cleanLogs(List<SSHConnection> nodes) {
		
		
		List<String> startupCommand;
		
		
		for (SSHConnection node : nodes) {
			
			
			log.info(ManageHBase.class, "deleting hbase log:"+node);
			startupCommand = new ArrayList<String>();
			startupCommand.add("sudo rm -r /usr/local/hbase-0.98.6.1-hadoop1/logs/*");
			SSHService.sendCommand(log, node, startupCommand, false);
			
			log.info(ManageHBase.class, "deleting hadoop log:"+node);
			startupCommand = new ArrayList<String>();
			startupCommand.add("sudo rm -r /usr/local/hadoop-1.2.1/logs/*");
			SSHService.sendCommand(log, node, startupCommand, false);
			
		}
	}
	
	public static void resetHBase(List<SSHConnection> nodes) {
		
		List<String> startupCommand;
		
		SSHConnection firstNode = nodes.get(0);
		
		log.info(ManageHBase.class, "stopping hbase:"+firstNode);
		startupCommand = new ArrayList<String>();
		startupCommand.add("/usr/local/hbase-0.98.6.1-hadoop1/bin/stop-hbase.sh");
		SSHService.sendCommand(log, firstNode, startupCommand, false);
		
		for (SSHConnection node : nodes) {
			
			if(node.getHostname().contains("zk")){
				
				log.info(ManageHBase.class, "deleting zookeeper directory:"+node);
				startupCommand = new ArrayList<String>();
				startupCommand.add("rm -r /usr/local/zookeeper/*");
				SSHService.sendCommand(log, node, startupCommand, false);	
			}
		}
		
		
		log.info(ManageHBase.class, "deleting hadoop directory"+firstNode);
		startupCommand = new ArrayList<String>();
		startupCommand.add("/usr/local/hadoop-1.2.1/bin/hadoop fs -rmr -r -f /hbase");
		SSHService.sendCommand(log, firstNode, startupCommand, false);			
		
		log.info(ManageHBase.class, "wait until zookeeper nodes are destroyed");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info(ManageHBase.class, "starting hbase:"+firstNode);
		startupCommand = new ArrayList<String>();
		startupCommand.add("/usr/local/hbase-0.98.6.1-hadoop1/bin/start-hbase.sh");
		SSHService.sendCommand(log, firstNode, startupCommand, false);
		
		log.info(ManageHBase.class, "waiting for HBase to come up");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
