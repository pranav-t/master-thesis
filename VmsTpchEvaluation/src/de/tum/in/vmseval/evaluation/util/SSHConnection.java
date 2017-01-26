package de.tum.in.vmseval.evaluation.util;

public class SSHConnection {

    private String hostname;
    private String address;
    private int port;
    private String user;
    private String password;

    public SSHConnection(String hostname, String address, int port, String user, String password) {
        this.hostname = hostname;
        this.address = address;
        this.port = port;
        this.user = user;
        this.password = password;
    }
    
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SSHConnection{" + "hostname=" + hostname + ", address=" + address + ", port=" + port + ", user=" + user + ", password=" + password + '}';
    }
    
}
