package sample;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerConnection extends Thread {

    String computerName;
    String sharedFolder;
    String serverSharedFolder;
    boolean shouldRun = true;
    public String Server_ADDRESS = computerName;
    public static int SERVER_PORT = 8080;

    Socket socket = null;
    PrintWriter netOut = null;

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public ServerConnection(String computerName, String sharedFolder, String serverSharedFolder, Socket socket) {
        super();
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        this.serverSharedFolder = serverSharedFolder;
        this.socket = socket;
    }

    public void run() {
        String command;
        String sep = System.getProperty("file.separator");

        try {
            System.out.println("Please Enter Your Command (DIR | UPLOAD filename | DOWNLOAD filename): ");
            Scanner reader = new Scanner(System.in);
            command = in.readLine();
            if (command.startsWith("DIR")) {
                // print statement just for debugging
                System.out.println("You entered the DIR command");

            } else if (command.startsWith("UPLOAD")) {
                // print statement just for debugging
                System.out.println("You entered the UPLOAD command");
                System.out.println(command);

                // remove the command so we can find the filename
                String filename = command.replace("UPLOAD ",sep);
                System.out.println(filename);
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

                String newFileName = serverSharedFolder + sep + filename;

            } else if (command.startsWith("DOWNLOAD")) {
                // print statement just for debugging
                System.out.println("You entered the DOWNLOAD command");

                // remove the command so we can find the filename
                String filename = command.replace("DOWNLOAD ", sep);
                filename = serverSharedFolder + filename;
                System.out.println("Filename: " + filename);
                System.out.println("The contents of your file: ");
                // print contents of entered text file
                try {
                    BufferedReader downloadFile = new BufferedReader(new FileReader(filename));
                    String line = downloadFile.readLine();
                    while (line != null) {
                        System.out.println(line);
                        line = downloadFile.readLine();
                    }
                    downloadFile.close();
                } catch (IOException e) {
                    System.err.println("Error, could not find file to download.");
                }
            } else {
                System.out.println("Please try again and enter a valid command.");
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

