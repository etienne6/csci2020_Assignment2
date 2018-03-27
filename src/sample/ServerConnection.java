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
        String command = null;

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Please Enter Your Command (DIR | UPLOAD filename | DOWNLOAD filename): ");
            command = in.readLine();
            //socket.close();
        } catch (IOException e) {
            System.err.print(("Error reading message from reader"));
        }

        try {
            socket = new Socket(Server_ADDRESS, SERVER_PORT);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + Server_ADDRESS);
        } catch (IOException e) {
            System.err.println("IOException while connecting");
        }

        // respond to appropriate command
        if (command.startsWith("DIR")) {
            // print statement just for debugging
            System.out.println("You entered the DIR command");

        } else if (command.startsWith("UPLOAD")) {
            // print statement just for debugging
            System.out.println("You entered the UPLOAD command");

            // remove the command so we can find the filename
            String filename = command.replace("UPLOAD ", "\\");
            // add the folder path to filename
            filename = sharedFolder + filename;
            System.out.println("Filename: " + filename);
            System.out.println("The contents of your file: ");

            // print contents of entered text file
            try {
                BufferedReader inFile = new BufferedReader(new FileReader(filename));
                String line = inFile.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = inFile.readLine();
                }
                inFile.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (command.startsWith("DOWNLOAD")) {
            // print statement just for debugging
            System.out.println("You entered the DOWNLOAD command");

            // remove the command so we can find the filename
            String filename = command.replace("DOWNLOAD ", "");
            System.out.println("Filename: " + filename);
        } else {
            System.out.println("Please try again and enter a valid command.");
        }

        if (socket != null) {
            try {
                netOut = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                System.err.println("IOException while opening output stream");
            }
        }

    }
}

