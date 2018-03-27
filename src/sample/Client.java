package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    public int socketNumber = 8080;
    private String computerName;
    private String sharedFolder;

    public Client() {
        setComputerName("127.0.0.1");
        setComputerName(".");
    }

    public Client(String computerName, String sharedFolder) {
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
    }

    public void Connect() {
        try {
            System.out.println("Client: Client Started");
            Socket s = new Socket(computerName, socketNumber);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            listenforInput();
        } catch(UnknownHostException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    // get files from specified folder and add to ListView
    public static ObservableList<String> getFiles(String pathOfFiles) {
        // use directory chooser to specify path
        DirectoryChooser chooser = new DirectoryChooser();
        File path = new File(pathOfFiles);
        chooser.setInitialDirectory(path);

        // create array of files from path and add to array list
        File[] filesInDirectory = path.listFiles();
        List<String> fileList = new ArrayList<>();
        for (int i = 0; i < filesInDirectory.length; i++) {
            // check if content in path is a file, if it is, add to array list
            if (filesInDirectory[i].isFile()) {
                fileList.add(filesInDirectory[i].getName());
            }
        }
        // create observable list from array list
        ObservableList<String> files = FXCollections.observableArrayList(fileList);
        return files;
    }

    public static void listenforInput() {
        Scanner console = new Scanner(System.in);
        while (true) {
            while (!console.hasNext()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String input = console.nextLine();

                try {
                    dout.writeUTF(input);

                    while (din.available() == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    String reply = din.readUTF();
                    System.out.println(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public String getComputerName() {
        return this.computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getSharedFolder() {
        return this.sharedFolder;
    }

    public void setSharedFolder(String sharedFolder) {
        this.sharedFolder = sharedFolder;
    }
}
