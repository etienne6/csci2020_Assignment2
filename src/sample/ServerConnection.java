//Thread Class
package sample;

import javafx.stage.DirectoryChooser;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection extends Thread {

    String computerName;
    String sharedFolder;
    String serverSharedFolder;
    boolean shouldRun = true;
    public String Server_ADDRESS = computerName;
    public static int SERVER_PORT = 8080;

    Socket socket = null;
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    //Thread constructor, we pass in a Socket because in the run function the socket is called upon to be closed.
    public ServerConnection(String computerName, String sharedFolder, String serverSharedFolder, Socket socket) {
        super();
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        this.serverSharedFolder = serverSharedFolder;
        this.socket = socket;
    }

    //Function called upon to execute thread
    public void run() {
        String command;
        String sep = System.getProperty("file.separator");

        try {
            System.out.println("Please Enter Your Command (DIR | UPLOAD filename | DOWNLOAD filename): ");
            Scanner reader = new Scanner(System.in);
            command = in.readLine();
            if (command.startsWith("DIR")) {
                // display list of files in server directory
                System.out.println("Files in the Server Shared Folder: ");
                listOfFiles(serverSharedFolder);
            } else if (command.startsWith("UPLOAD")) {
                // remove the command so we can find the filename
                String filename = command.replace("UPLOAD ",sep);
                // add the folder path to filename
                String fullFilename = sharedFolder + filename;
                String fullOutFileName = serverSharedFolder + filename;
                // copy file from local to server
                upload(fullFilename, fullOutFileName);
            } else if (command.startsWith("DOWNLOAD")) {
                // remove the command so we can find the filename
                String filename = command.replace("DOWNLOAD ", sep);
                String fullFilename = sharedFolder + filename;
                String fullOutFileName = serverSharedFolder + filename;
                // copy file from server to local
                download(fullFilename, fullOutFileName);
            } else {
                System.out.println("Please try again and enter a valid command.");
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // upload function to copy files from local to server if UPLOAD command chosen
    public void upload(String inputFile, String outputFile) {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(inputFile));
            BufferedWriter outFile = new BufferedWriter(new FileWriter(outputFile));
            String line = inFile.readLine();
            while (line != null) {
                System.out.println(line);
                outFile.write(line);
                outFile.newLine();
                line = inFile.readLine();
                if (line!=null) {
                    outFile.flush();
                }
            }
            outFile.close();
            inFile.close();
        } catch (IOException e) {
            System.err.println("IOException while connecting");
        }
    }

    // download function to copy files from server folder to local if DOWNLOAD command chosen
    public void download(String inputFile, String outputFile) {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(outputFile));
            BufferedWriter outFile = new BufferedWriter(new FileWriter(inputFile));
            String line = inFile.readLine();
            while (line != null) {
                outFile.write(line);
                outFile.newLine();
                line = inFile.readLine();
                if (line != null) {
                    outFile.flush();
                }
            }
            outFile.close();
            inFile.close();
        } catch (IOException e) {
            System.err.println("IOException while connecting");
        }
    }

    // display list of files if DIR command chosen
    public void listOfFiles(String serverSharedFolder) {
        DirectoryChooser chooser = new DirectoryChooser();
        File path = new File(serverSharedFolder);
        chooser.setInitialDirectory(path);

        // create array of files from path and add to array list
        File[] filesInDirectory = path.listFiles();
        for (int i = 0; i < filesInDirectory.length; i++){
            String filename = filesInDirectory[i].getName();
            filename =  filename.replace(serverSharedFolder, "");
            System.out.println(filename);
        }
    }
}
