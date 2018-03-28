package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    //enter your own filepath
    public String sharedFolder;
    public String computerName = "127.0.0.1";
    // bring shared folder path from Main file
    public Controller (String computerName, String sharedFolder){
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        System.out.println("The Computer Name is: " + computerName);
        System.out.println("The Shared Folder is: " + sharedFolder);
    }

    @FXML
    public void initialize(){
        // connect to the server (still working on function)
        // connectToServer();
        // create Observable List of files from specified folder

        ObservableList<String> localFileList = Client.getFiles(sharedFolder);
        // create Observable List of files from specified folder
        ObservableList<String> serverFilesList = Server.getServerFiles(sharedFolder);
        // add observable list of files to ListView
        localFiles.setItems(localFileList);
        // add observable list of files to ListView
        serverFiles.setItems(serverFilesList);
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
                    /*try {
                        BufferedReader in = new BufferedReader(new FileReader(file));
                        String line = in.readLine();
                        while (line != null) {
                            System.out.println(line);
                            line = in.readLine();
                        }
                        in.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    */
                    String FileName2  = FileName.replace(".", "(copy).");
                    // file copying system
                    try {
                        OutputStream os = new FileOutputStream(FileName2);
                        Files.copy(Paths.get(FileName), os);
                        os.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

    public void OnDownload(ActionEvent actionEvent) {
        System.out.println("You have decided to Download");

    }

    public void OnUpload(ActionEvent actionEvent) {
        System.out.println("You have decided to Upload");

    }
    //connects to the server and allows user to upload/download to server
    public void OnCommand(ActionEvent actionEvent){

        Client client = new Client(computerName, sharedFolder);
        client.Connect();
    }

    public void ViewFiles(){
        // create Observable List of files from specified folder
        // ObservableList<String> serverFilesList = getServerFiles(sharedFolderPath);
        // add observable list of files to ListView
        // serverFiles.setItems(serverFilesList);
    }

    public void refresh(){
        // implement to refresh file lists once we upload/download
    }
}

