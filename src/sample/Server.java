package sample;

import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    public static void main(String[] args) {
        try{
            System.out.println("Looking for client");
            ServerSocket ss = new ServerSocket(8080);
            Socket soc = ss.accept();
            System.out.println("Connection Established");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
