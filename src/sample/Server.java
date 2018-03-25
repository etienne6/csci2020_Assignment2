package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ServerSocket ss;
    ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
    boolean shouldRun = true;


    public static void main(String[] args){
        System.out.println("Waiting on connection ...");
        new Server();
        System.out.println("Established connection");
    }
    public Server(){
        try{
            ss = new ServerSocket(3333);
            while(shouldRun){
                Socket s = ss.accept();
                ServerConnection sc = new ServerConnection(s, this);
                sc.start();
                connections.add(sc);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}