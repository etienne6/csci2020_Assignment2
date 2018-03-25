package sample;

import com.sun.deploy.util.StringUtils;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// keep this: fx:controller="sample.Controller"

public class Controller {
    private ListView<String> list = new ListView<>();
    @FXML
    private ListView<String> localFiles;
    @FXML
    private ListView<String> serverFiles;
    @FXML
    private Button downloadButton;
    @FXML
    private Button uploadButton;
    @FXML
    private Button commandButton;
    public String sharedFolder;
    public String computerName;
    // bring shared folder path from Main file
    public Controller (String computerName, String sharedFolder){
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        System.out.println("The Computer Name is: " + computerName);
        System.out.println("The Shared Folder is: " + sharedFolder);
    }

    @FXML
    public void initialize(){
        // set sharedFolderPath as the one from command line
        String sharedFolderPath = sharedFolder;
       // connect to the server (still working on function)
       // connectToServer();
        // create Observable List of files from specified folder
        ObservableList<String> localFileList = getFiles(sharedFolderPath);
        // create Observable List of files from specified folder
        ObservableList<String> serverFilesList = getServerFiles(sharedFolderPath);
        // add observable list of files to ListView
        localFiles.setItems(localFileList);
        // add observable list of files to ListView
        serverFiles.setItems(localFileList);
        //
        localFiles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    String item = localFiles.getSelectionModel().getSelectedItem();
                    String FileName = item;
                    System.out.println(item);
                    File file = new File(FileName);
                    String localPath = file.getAbsolutePath();
                }
            }
        });
        serverFiles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    String item = serverFiles.getSelectionModel().getSelectedItem();
                    String FileName = item;
                    System.out.println(item);
                    System.out.println(item);
                    File file = new File(FileName);
                    String serverPath = file.getAbsolutePath();
                }
            }
        });
    }

    // get files from specified folder and add to ListView
    public ObservableList<String> getFiles(String pathOfFiles) {
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

    // get files from specified folder and add to ListView
    public ObservableList<String> getServerFiles(String pathOfFiles) {
        // use directory chooser to specify path
        DirectoryChooser chooser = new DirectoryChooser();
        File path = new File(pathOfFiles);
        chooser.setInitialDirectory(path);
        System.out.println(path);

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

    public void OnDownload(ActionEvent actionEvent) {

    }

    public void OnUpload(ActionEvent actionEvent) {

    }

    public void OnCommand(ActionEvent actionEvent) {
        System.out.println("Please Enter Your Command (DIR | UPLOAD filename | DOWNLOAD filename): ");
        Scanner input = new Scanner(System.in);
        String command = input.nextLine();

        // respond to appropriate command
        if(command.startsWith("DIR")){
            // print statement just for debugging
            System.out.println("You entered the DIR command");

        } else if (command.startsWith("UPLOAD")) {
            // print statement just for debugging
            System.out.println("You entered the UPLOAD command");

            // remove the command so we can find the filename
            String filename  = command.replace("UPLOAD ", "\\");
            // add the folder path to filename
            filename = sharedFolder + filename;
            System.out.println("Filename: " + filename);

            // print contents of entered text file
            try {
                BufferedReader in = new BufferedReader(new FileReader(filename));
                String line = in.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = in.readLine();
                }
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (command.startsWith("DOWNLOAD")) {
            // print statement just for debugging
            System.out.println("You entered the DOWNLOAD command");

            // remove the command so we can find the filename
            String filename  = command.replace("DOWNLOAD ", "");
            System.out.println("Filename: " + filename);
        } else {
            System.out.println("Please try again and enter a valid command.");
        }

    }

    public void ViewFiles(){
        // create Observable List of files from specified folder
        // ObservableList<String> serverFilesList = getServerFiles(sharedFolderPath);
        // add observable list of files to ListView
        // serverFiles.setItems(serverFilesList);
    }

    public void connectServer(){
        try {
            // implement how to connect to server
            System.out.println("Server: Waiting for client....");
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server: Connection Established");
                connectClient();
                //ViewFiles();
               // clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void connectClient(){
        try {
            // implement how to connect to client
            System.out.println("Client: Client Started");
            Socket clientSocket = new Socket("127.0.0.1", 8080);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void refresh(){
        // implement to refresh file lists once we upload/download
    }
}

