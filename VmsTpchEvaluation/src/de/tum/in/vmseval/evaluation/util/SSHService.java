package de.tum.in.vmseval.evaluation.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SSHService {

    private static final Map<String, Session> SESSIONS = new HashMap<>();
    
    private static final boolean SSH_PASSWORDLESS_LOGIN = true;
//    private static final String SSH_PRIVATE_KEY_FILE = "/home/pranav/.ssh/id_rsa";
    private static final String SSH_PRIVATE_KEY_FILE = "/home/pranav/.ssh/pranav-vmsevaluation.pem";
    private static final String SSH_KNOWN_HOSTS_FILE = "/home/pranav/.ssh/known_hosts";
			
    
    public static void copyFile(SSHConnection sshConnection, String sourceDirectory, String targetDirectory, String fileName) {
        String SFTPHOST = sshConnection.getAddress();
        int SFTPPORT = sshConnection.getPort();
        String SFTPUSER = sshConnection.getUser();
        String SFTPPASS = sshConnection.getPassword();

        Session session;
        Channel channel;
        ChannelSftp channelSftp;
        try {
            JSch jsch = new JSch();

            if(SSH_PASSWORDLESS_LOGIN){
                jsch.setKnownHosts(SSH_KNOWN_HOSTS_FILE);
                jsch.addIdentity(SSH_PRIVATE_KEY_FILE);    
            }	
            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            if(!SSH_PASSWORDLESS_LOGIN) {
                session.setPassword(SFTPPASS);
            }
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(targetDirectory);

            File f = new File(sourceDirectory+"/" + fileName);
            channelSftp.put(new FileInputStream(f), f.getName(), ChannelSftp.OVERWRITE);

            channelSftp.exit();

            session.disconnect();
        } catch (JSchException | SftpException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void sendCommand(SSHConnection sshConnection, String command) {
        List<String> commands = new ArrayList<>();
        commands.add(command);
        sendCommand(sshConnection, commands, false);
    }
    
    public static void sendCommand(SSHConnection sshConnection, List<String> commands) {
        sendCommand(sshConnection, commands, false);
    }
	
    public static void sendCommand(SSHConnection sshConnection, List<String> commands, boolean executeInBackground) {

        String SFTPHOST = sshConnection.getAddress();
        int SFTPPORT = sshConnection.getPort();
        String SFTPUSER = sshConnection.getUser();
        String SFTPPASS = sshConnection.getPassword();
        Session session;
        try {

            if(SESSIONS.get(SFTPHOST)==null || !SESSIONS.get(SFTPHOST).isConnected()) {
                JSch jsch = new JSch();
                if(SSH_PASSWORDLESS_LOGIN) {
                    jsch.setKnownHosts(SSH_KNOWN_HOSTS_FILE);
                    jsch.addIdentity(SSH_PRIVATE_KEY_FILE);    
                }	
                session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
                if(!SSH_PASSWORDLESS_LOGIN) {
                    session.setPassword(SFTPPASS);
                }

                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                SESSIONS.put(SFTPHOST, session);
            } else {
                session = SESSIONS.get(SFTPHOST);
            }

            long time = new Date().getTime();
            String command ="";
            if(executeInBackground) {
                command ="nohup sh -c \"";
            }
            
            int x = 1;
            for (String commandx : commands) {
                command += commandx;
                if(x++ != commands.size()) {
                    command += " && ";
                }
            }
            System.out.print("\tremote command: " + command);
            if(executeInBackground) {
                command += "\" > /dev/null 2>&1 &";
            }
            
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            BufferedReader in = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
            channelExec.setCommand(command);
            channelExec.connect();

            if(!executeInBackground){	
                String msg;
                while((msg=in.readLine())!=null){
                    System.out.println(msg);
                }
            }
            channelExec.disconnect();
            System.out.println(" --> time: "+(new Date().getTime() - time+" ms"));
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
    }
        
    public static void closeSessions(){
        for (String host : SESSIONS.keySet()) {
            SESSIONS.get(host).disconnect();
        }
    }

}
