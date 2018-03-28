package sample;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerConnection extends Thread {

    String computerName;
    String sharedFolder;
    boolean shouldRun = true;
    public String Server_ADDRESS = computerName;
    public static int SERVER_PORT = 8080;

    Socket socket = null;
    PrintWriter netOut = null;

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public ServerConnection(String computerName, String sharedFolder, Socket socket) {
        super();
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Please Enter Your Command (DIR | UPLOAD filename | DOWNLOAD filename): ");
            Scanner reader = new Scanner(System.in);
            String command = reader.next();
            //put cmds here
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

