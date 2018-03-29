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
    private String serverSharedFolder;
    private String sep = System.getProperty("file.separator");


    public Client() {
        setComputerName("127.0.0.1");
        setSharedFolder(".");
        setServerSharedFolder("."+sep+"serverSharedFolder");
    }

    public Client(String computerName, String sharedFolder, String serverSharedFolder) {
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        this.serverSharedFolder = serverSharedFolder;
    }

    public void Connect() {
        try {
            System.out.println("Client: Client Started");
            Socket s = new Socket(computerName, socketNumber);
            // Input and Output stream for file and data transfer
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
        } catch(UnknownHostException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    // get files from specified folder and add to ListView
    // pathOfFiles is local folder directory
    public static ObservableList<String> getFiles(String pathOfFiles) {
        // use directory chooser to specify path of local files
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

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public void setSharedFolder(String sharedFolder) {
        this.sharedFolder = sharedFolder;
    }

    public void setServerSharedFolder(String serverSharedFolder) { this.serverSharedFolder = serverSharedFolder; }
}
