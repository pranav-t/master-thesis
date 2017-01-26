package de.webdataplatform.scripts;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.log.Log;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.ssh.LocalCommandService;
import de.webdataplatform.ssh.SSHConnection;
import de.webdataplatform.ssh.SSHService;

public class InstallNodes {

	
	private static Log log;
	
	
	public static void main(String[] args) {

		log= new Log("install-nodes.log");	
		
		SystemConfig.load(log);
		NetworkConfig.load(log);
		
		List<SSHConnection> nodes = new ArrayList<SSHConnection>();
//		nodes.add(new SSHConnection("jan-1", "172.24.36.217",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-client", "172.24.34.133",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-master", "172.24.34.108",22, "ubuntu", null));
//		
//		nodes.add(new SSHConnection("jan-zk-1", "172.24.37.121",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-zk-2", "172.24.37.122",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-zk-3", "172.24.37.123",22, "ubuntu", null));
		
		
		
////
////		
//		nodes.add(new SSHConnection("jan-rs-1", "172.24.34.104",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-2", "172.24.34.107",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-3", "172.24.34.105",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-4", "172.24.34.106",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-5", "172.24.35.207",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-6", "172.24.35.208",22, "ubuntu", null));	
//		nodes.add(new SSHConnection("jan-rs-7", "172.24.35.231",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-8", "172.24.35.232",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-9", "172.24.36.207",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-10", "172.24.36.208",22, "ubuntu", null));	
//		nodes.add(new SSHConnection("jan-rs-11", "172.24.36.209",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-12", "172.24.36.210",22, "ubuntu", null));	
//		nodes.add(new SSHConnection("jan-rs-13", "172.24.37.87",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-14", "172.24.37.88",22, "ubuntu", null));	
//		nodes.add(new SSHConnection("jan-rs-15", "172.24.37.89",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-16", "172.24.37.90",22, "ubuntu", null));	
//		nodes.add(new SSHConnection("jan-rs-17", "172.24.37.105",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-18", "172.24.37.106",22, "ubuntu", null));	
//		nodes.add(new SSHConnection("jan-rs-19", "172.24.37.107",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-rs-20", "172.24.37.108",22, "ubuntu", null));			
		
		
		
		
		
		
////		
		nodes.add(new SSHConnection("jan-ds-1", "172.24.35.55",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-2", "172.24.35.56",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-3", "172.24.35.57",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-4", "172.24.35.58",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-5", "172.24.35.59",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-6", "172.24.35.60",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-ds-7", "172.24.35.61",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-8", "172.24.35.62",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-ds-9", "172.24.36.211",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-10", "172.24.36.212",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-ds-11", "172.24.36.213",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-12", "172.24.36.214",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-ds-13", "172.24.37.96",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-14", "172.24.37.97",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-15", "172.24.37.98",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-16", "172.24.37.99",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-ds-17", "172.24.37.101",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-18", "172.24.37.102",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-ds-19", "172.24.37.103",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-ds-20", "172.24.37.104",22, "ubuntu", null));	
//	
//		
//		
//		
//		
//		
//////		
//////////		
		nodes.add(new SSHConnection("jan-vm-1", "172.24.34.41",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-2", "172.24.34.38",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-3", "172.24.34.39",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-4", "172.24.34.40",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm-5", "172.24.34.43",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-6", "172.24.34.44",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-7", "172.24.34.42",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-8", "172.24.34.45",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm-9", "172.24.34.46",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-10", "172.24.34.49",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-11", "172.24.34.47",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-12", "172.24.34.48",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-13", "172.24.34.51",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-14", "172.24.34.50",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-vm-15", "172.24.34.52",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-16", "172.24.34.60",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-17", "172.24.34.55",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-18", "172.24.34.53",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-19", "172.24.34.56",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-20", "172.24.34.54",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-21", "172.24.34.58",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-22", "172.24.34.57",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-23", "172.24.34.59",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-24", "172.24.34.61",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm-25", "172.24.34.62",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-26", "172.24.34.63",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-27", "172.24.34.65",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-28", "172.24.34.64",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm-29", "172.24.34.66",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-30", "172.24.34.67",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-31", "172.24.34.68",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-32", "172.24.34.70",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-vm-33", "172.24.34.69",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-34", "172.24.34.71",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-35", "172.24.34.72",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-36", "172.24.34.73",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-37", "172.24.34.74",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-38", "172.24.34.75",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-39", "172.24.34.76",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-40", "172.24.34.79",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm-41", "172.24.34.80",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-42", "172.24.34.77",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-43", "172.24.34.78",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-44", "172.24.34.81",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-45", "172.24.34.82",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-46", "172.24.34.84",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-47", "172.24.34.83",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-48", "172.24.34.85",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-49", "172.24.34.86",22, "ubuntu", null));
//		nodes.add(new SSHConnection("jan-vm-50", "172.24.34.87",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-51", "172.24.34.89",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-52", "172.24.34.90",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-53", "172.24.34.91",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm-54", "172.24.34.95",22, "ubuntu", null));

		nodes.add(new SSHConnection("jan-vm2-1", "172.24.36.59",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-2", "172.24.36.84",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-3", "172.24.36.102",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-4", "172.24.36.77",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm2-5", "172.24.36.60",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-6", "172.24.36.70",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-7", "172.24.36.61",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-8", "172.24.36.81",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm2-9", "172.24.36.104",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-10", "172.24.36.65",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-11", "172.24.36.69",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-12", "172.24.36.99",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-13", "172.24.36.62",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-14", "172.24.36.63",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-15", "172.24.36.78",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-16", "172.24.36.64",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-17", "172.24.36.96",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-18", "172.24.36.105",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-19", "172.24.36.66",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-20", "172.24.36.72",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-21", "172.24.36.76",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-22", "172.24.36.103",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-23", "172.24.36.86",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-24", "172.24.36.100",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm2-25", "172.24.36.82",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-26", "172.24.36.89",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-27", "172.24.36.101",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-28", "172.24.36.67",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm2-29", "172.24.36.91",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-30", "172.24.36.106",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-31", "172.24.36.68",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-32", "172.24.36.93",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-33", "172.24.36.79",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-34", "172.24.36.107",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-35", "172.24.36.75",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-36", "172.24.36.74",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-37", "172.24.36.97",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-38", "172.24.36.71",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-39", "172.24.36.83",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-40", "172.24.36.94",22, "ubuntu", null));	
		nodes.add(new SSHConnection("jan-vm2-41", "172.24.36.80",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-42", "172.24.36.98",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-43", "172.24.36.108",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-44", "172.24.36.73",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-45", "172.24.36.85",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-46", "172.24.36.88",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-47", "172.24.36.87",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-48", "172.24.36.92",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-49", "172.24.36.90",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm2-50", "172.24.36.95",22, "ubuntu", null));

		
		
		nodes.add(new SSHConnection("jan-vm3-1", "172.24.39.149",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-2", "172.24.39.128",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-3", "172.24.39.130",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-4", "172.24.39.129",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-5", "172.24.39.141",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-6", "172.24.39.160",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-7", "172.24.39.131",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-8", "172.24.39.140",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-9", "172.24.39.188",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-10", "172.24.39.136",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-11", "172.24.39.169",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-12", "172.24.39.193",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-13", "172.24.39.196",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-14", "172.24.39.183",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-15", "172.24.39.132",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-16", "172.24.39.139",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-17", "172.24.39.133",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-18", "172.24.39.144",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-19", "172.24.39.210",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-20", "172.24.39.212",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-21", "172.24.39.134",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-22", "172.24.39.148",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-23", "172.24.39.135",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-24", "172.24.39.216",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-25", "172.24.39.153",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-26", "172.24.39.145",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-27", "172.24.39.147",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-28", "172.24.39.154",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-29", "172.24.39.137",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-30", "172.24.39.155",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-31", "172.24.39.184",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-32", "172.24.39.138",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-33", "172.24.39.204",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-34", "172.24.39.217",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-35", "172.24.39.167",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-36", "172.24.39.142",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-37", "172.24.39.218",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-38", "172.24.39.178",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-39", "172.24.39.171",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-40", "172.24.39.192",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-41", "172.24.39.143",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-42", "172.24.39.205",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-43", "172.24.39.151",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-44", "172.24.39.185",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-45", "172.24.39.186",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-46", "172.24.39.211",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-47", "172.24.39.146",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-48", "172.24.39.199",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-49", "172.24.39.168",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-50", "172.24.39.177",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-51", "172.24.39.208",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-52", "172.24.39.150",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-53", "172.24.39.173",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-54", "172.24.39.187",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-55", "172.24.39.194",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-56", "172.24.39.174",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-57", "172.24.39.209",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-58", "172.24.39.206",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-59", "172.24.39.202",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-60", "172.24.39.158",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-61", "172.24.39.219",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-62", "172.24.39.156",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-63", "172.24.39.166",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-64", "172.24.39.221",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-65", "172.24.39.170",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-66", "172.24.39.161",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-67", "172.24.39.162",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-68", "172.24.39.191",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-69", "172.24.39.224",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-70", "172.24.39.159",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-71", "172.24.39.172",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-72", "172.24.39.203",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-73", "172.24.39.222",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-74", "172.24.39.181",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-75", "172.24.39.213",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-76", "172.24.39.152",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-77", "172.24.39.164",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-78", "172.24.39.157",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-79", "172.24.39.189",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-80", "172.24.39.182",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-81", "172.24.39.215",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-82", "172.24.39.179",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-83", "172.24.39.220",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-84", "172.24.39.180",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-85", "172.24.39.195",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-86", "172.24.39.165",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-87", "172.24.39.175",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-88", "172.24.39.227",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-89", "172.24.39.226",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-90", "172.24.39.163",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-91", "172.24.39.190",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-92", "172.24.39.176",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-93", "172.24.39.197",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-94", "172.24.39.200",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-95", "172.24.39.223",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-96", "172.24.39.201",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-97", "172.24.39.198",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-98", "172.24.39.207",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-99", "172.24.39.225",22, "ubuntu", null));
		nodes.add(new SSHConnection("jan-vm3-100", "172.24.39.214",22, "ubuntu", null));		
		
//		nodes.add(sshConnection);
		if(args.length== 0)log.info(InstallNodes.class, "Please supply arguments: install , update");
		
		
		boolean installVM=false, installHadoop=false, installHBase=false;
		
		
		for (String arg : args) {
			
			if(arg.equals("vms"))installVM=true;
			if(arg.equals("hadoop"))installHadoop=true;
			if(arg.equals("hbase"))installHBase=true;
			
		}
		
		
		if(args[0].equals("install"))installNodes(nodes, installVM, installHBase, installHadoop);
		if(args[0].equals("update"))updateNodes(nodes);
//		killHBase(nodes);
		

		SSHService.closeSessions();
		log.close();
		
		
		
		
	}


	

	
	public static void installNodes(List<SSHConnection> nodes, boolean installVM, boolean installHBase, boolean installHadoop) {
		
		
		List<String> startupCommand;
		
		SSHConnection firstNode = nodes.get(0);
		
		for (SSHConnection node : nodes) {
		
//			if(!node.equals(firstNode)){
				
				log.info(InstallNodes.class, "setting hostname:"+node);
				startupCommand = new ArrayList<String>();
//				startupCommand.add("sudo  sh -c \"echo '"+node.getAddress()+"   "+node.getHostname()+ "' >> /etc/hosts\"");
//				startupCommand.add("sudo  sh -c \"echo '"+firstNode.getAddress()+"   "+firstNode.getHostname()+ "' >> /etc/hosts\"");
				
				startupCommand.add("sudo  sh -c \"echo '172.24.34.133   jan-client' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.34.108   jan-master' >> /etc/hosts\"");

				startupCommand.add("sudo  sh -c \"echo '172.24.37.121   jan-zk-1' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.122   jan-zk-2' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.123   jan-zk-3' >> /etc/hosts\"");
				
				startupCommand.add("sudo  sh -c \"echo '172.24.34.104   jan-rs-1' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.34.107   jan-rs-2' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.34.105   jan-rs-3' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.34.106   jan-rs-4' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.207   jan-rs-5' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.208   jan-rs-6' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.231   jan-rs-7' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.232   jan-rs-8' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.36.207   jan-rs-9' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.36.208   jan-rs-10' >> /etc/hosts\"");	
				startupCommand.add("sudo  sh -c \"echo '172.24.36.209   jan-rs-11' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.36.210   jan-rs-12' >> /etc/hosts\"");	
				startupCommand.add("sudo  sh -c \"echo '172.24.37.87   jan-rs-13' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.88   jan-rs-14' >> /etc/hosts\"");	
				startupCommand.add("sudo  sh -c \"echo '172.24.37.89   jan-rs-15' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.90   jan-rs-16' >> /etc/hosts\"");	
				startupCommand.add("sudo  sh -c \"echo '172.24.37.105   jan-rs-17' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.106   jan-rs-18' >> /etc/hosts\"");	
				startupCommand.add("sudo  sh -c \"echo '172.24.37.107   jan-rs-19' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.108   jan-rs-20' >> /etc/hosts\"");
				
				startupCommand.add("sudo  sh -c \"echo '172.24.35.55   jan-ds-1' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.56   jan-ds-2' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.57   jan-ds-3' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.58   jan-ds-4' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.59   jan-ds-5' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.60   jan-ds-6' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.61   jan-ds-7' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.35.62   jan-ds-8' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.36.211   jan-ds-9' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.36.212   jan-ds-10' >> /etc/hosts\"");	
				startupCommand.add("sudo  sh -c \"echo '172.24.36.213   jan-ds-11' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.36.214   jan-ds-12' >> /etc/hosts\"");					
				startupCommand.add("sudo  sh -c \"echo '172.24.37.96   jan-ds-13' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.97   jan-ds-14' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.98   jan-ds-15' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.99   jan-ds-16' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.101   jan-ds-17' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.102   jan-ds-18' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.103   jan-ds-19' >> /etc/hosts\"");
				startupCommand.add("sudo  sh -c \"echo '172.24.37.104   jan-ds-20' >> /etc/hosts\"");				
				
				SSHService.sendCommand(log, node, startupCommand,false);
	
				
				log.info(InstallNodes.class, "setting environment variables:"+node);
				startupCommand = new ArrayList<String>();
				startupCommand.add("sudo  sh -c \"echo 'JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64/' >> /etc/environment\"");
				SSHService.sendCommand(log, node, startupCommand,false);
				
			
				log.info(InstallNodes.class, "updating system:"+node);
				startupCommand = new ArrayList<String>();
				startupCommand.add("yes | sudo  apt-get update");
				SSHService.sendCommand(log, node, startupCommand,false);
				
				log.info(InstallNodes.class, "install java:"+node);
				startupCommand = new ArrayList<String>();
				startupCommand.add("yes | sudo apt-get install default-jre");
				SSHService.sendCommand(log, node, startupCommand,false);
				
	//			startupCommand.add("sudo apt-get install openjdk-8-jdk");
				
				log.info(InstallNodes.class, "setting folder permission /usr/local");
				startupCommand = new ArrayList<String>();
				startupCommand.add("sudo chmod 777 /usr/local");
				SSHService.sendCommand(log, node, startupCommand,false);
				
				
				if(installHadoop){
					log.info(InstallNodes.class, "making hadoop folder");
					startupCommand = new ArrayList<String>();
					startupCommand.add("sudo mkdir -m 777 /usr/local/hadoop");
					SSHService.sendCommand(log, node, startupCommand,false);			
					
					log.info(InstallNodes.class, "transfering hadoop files");
					LocalCommandService.transferDirectory(log, node, "/usr/local/hadoop-1.2.1", "/usr/local/hadoop-1.2.1");
				}
				
				if(installHBase){
					log.info(InstallNodes.class, "transfering hbase files");
					LocalCommandService.transferDirectory(log, node, "/usr/local/hbase-0.98.6.1-hadoop1", "/usr/local/hbase-0.98.6.1-hadoop1");
				}
				
				if(installVM){
					log.info(InstallNodes.class, "transfering scalablevm files");
					LocalCommandService.transferDirectory(log, node, "/usr/local/scalablevm", "/usr/local/scalablevm");
				}
//			}
//			if(node.equals(firstNode)){
//				log.info(InstallNodes.class, "transfering ssh files");
//				LocalCommandService.transferFile(log, node, "/home/jan/.ssh/id_rsa", "/home/ubuntu/.ssh/");
//				LocalCommandService.transferFile(log, node, "/home/jan/.ssh/id_rsa.pub", "/home/ubuntu/.ssh/");
//			}
		}
		
		SSHService.closeSessions();
		
	}
	
	public static void updateNodes(List<SSHConnection> nodes) {
		
		List<String> startupCommand;

		for (SSHConnection node : nodes) {
			
		
			log.info(InstallNodes.class, "Deleting scalablevm folder");
			startupCommand = new ArrayList<String>();
			startupCommand.add("sudo rm -r /usr/local/scalablevm");
			SSHService.sendCommand(log, node, startupCommand, false);	
			
			log.info(InstallNodes.class, "transfering scalablevm files");
			LocalCommandService.transferDirectory(log, node, "/usr/local/scalablevm", "/usr/local/scalablevm");
		
			
			
		}
		
		SSHService.closeSessions();

	}
	
	
	

	
}
