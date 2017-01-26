package de.tum.in.vmseval.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VMSClient {
    
    public static String addQuery(String query) {
        
        String acknowledgement = "*";
        
        String clientName="@";
        String clientIp="@";
        String clientMessageServerPort = "5555";        
        try {
            clientName = InetAddress.getLocalHost().getHostName();
            clientIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(VMSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        String ip = "node1";
        String ip = "sue-master-1";
        int port = 4004;
        
        boolean requestStatus = false;
        int x = 0;
        while(!requestStatus && x < 10){
            try { 
                Socket socket = new Socket(ip,port);
                //socket.setSoTimeout(10000);

                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = 
                "â†’" +
                    "client" + "#" + clientName + "#" + clientIp + "#" + clientMessageServerPort +
                "Å§" +
                    "addQuery" +
                "Å§" +
                    query +
                "â†";
                
                printWriter.println(message);
                printWriter.flush();

                acknowledgement = reader.readLine();
                //log.message(MessageClient.class, "receiving handshake from "+ip+":"+port+", "+response);

                printWriter.close();
                reader.close();
                socket.close();

                if(acknowledgement.equals("ok")) {
                    requestStatus = true;
                } else {
                    throw new Exception("message could not be sent: "+message+" answer was:"+acknowledgement);
                }
            } catch (Exception e) {
                e.printStackTrace();
                x++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) { e1.printStackTrace(); }
            }
        }
        
        String response = "requestFailure";
        if(requestStatus == true) {
            response = waitForAddBaseTableResponse();
        }
        return response;
    }
 
    public static String getThroughputSummary() {
        
        String acknowledgement = "*";
        
        String clientName="@";
        String clientIp="@";
        String clientMessageServerPort = "5555";        
        try {
            clientName = InetAddress.getLocalHost().getHostName();
            clientIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(VMSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        String ip = "node1";
        String ip = "sue-master-1";
        int port = 4004;
        
        boolean requestStatus = false;
        int x = 0;
        while(!requestStatus && x < 10){
            try { 
                Socket socket = new Socket(ip,port);
                //socket.setSoTimeout(10000);

                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = 
                "â†’" +
                    "client" + "#" + clientName + "#" + clientIp + "#" + clientMessageServerPort +
                "Å§" +
                    "throughputSummaryVMS" +
                "Å§" +
                    "summary" +
                "â†";
                
                printWriter.println(message);
                printWriter.flush();

                acknowledgement = reader.readLine();
                //log.message(MessageClient.class, "receiving handshake from "+ip+":"+port+", "+response);

                printWriter.close();
                reader.close();
                socket.close();

                if(acknowledgement.equals("ok")) {
                    requestStatus = true;
                } else {
                    throw new Exception("message could not be sent: "+message+" answer was:"+acknowledgement);
                }
            } catch (Exception e) {
                e.printStackTrace();
                x++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) { e1.printStackTrace(); }
            }
        }
        
        String response = "requestFailure";
        if(requestStatus == true) {
            response = waitForStatus();
        }
        return response;
    }    
    
    private static String waitForAddBaseTableResponse() {
        String responseString = "responseFailure";
        try {
            ServerSocket serverSocket = new ServerSocket(5555);
            Socket cs = serverSocket.accept();
            //cs.setSoTimeout(10000);
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            PrintWriter acknowledgementWriter = new PrintWriter(cs.getOutputStream());
            responseString = responseReader.readLine();
            
            if(responseString != null) {
                String[] msgParts = responseString.split("Å§");
                String component = msgParts[0];
                responseString = msgParts[1];
                String content = msgParts[2];
                acknowledgementWriter.println("ok");
            }
            
            acknowledgementWriter.flush();
            acknowledgementWriter.close();
            serverSocket.close();
            cs.close();
        } catch (IOException ex) {
            Logger.getLogger(VMSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return responseString;
    }
    
    private static String waitForStatus() {
        String responseString = "responseFailure";
        try {
            ServerSocket serverSocket = new ServerSocket(5555);
            Socket cs = serverSocket.accept();
            //cs.setSoTimeout(10000);
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            PrintWriter acknowledgementWriter = new PrintWriter(cs.getOutputStream());
            responseString = responseReader.readLine();
            
            if(responseString != null) {
                String[] msgParts = responseString.split("Å§");
                String component = msgParts[0];
                //responseString = msgParts[1];
                responseString = msgParts[2];
                acknowledgementWriter.println("ok");
            }
            
            acknowledgementWriter.flush();
            acknowledgementWriter.close();
            serverSocket.close();
            cs.close();
        } catch (IOException ex) {
            Logger.getLogger(VMSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return responseString;
    }
    
}
