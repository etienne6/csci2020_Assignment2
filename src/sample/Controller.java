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
    public String serverSharedFolder;
    public String FileName;
    String sep = System.getProperty("file.separator");

    // bring shared folder path from Main file
    public Controller (String computerName, String sharedFolder, String serverSharedFolder){
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        this.serverSharedFolder = serverSharedFolder;
    }

    @FXML
    public void initialize(){
        // create Observable List of files from specified folder
        ObservableList<String> localFileList = Client.getFiles(sharedFolder);
        // create Observable List of files from specified folder
        ObservableList<String> serverFilesList = Server.getServerFiles(serverSharedFolder);
        // add observable list of files to ListView
        localFiles.setItems(localFileList);
        // add observable list of files to ListView
        serverFiles.setItems(serverFilesList);

        // get local filename when it is clicked in list view
        localFiles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 1) {
                    String item = localFiles.getSelectionModel().getSelectedItem();
                    FileName = item;
                    setFileName(FileName);
                }
            }
        });
        // get server filename when it is clicked in list view
        serverFiles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 1) {
                    String item = serverFiles.getSelectionModel().getSelectedItem();
                    FileName = item;
                    setFileName(FileName);
                }
            }
        });
    }

    // when download button is clicked (after file selection through clicking), it will send file over to local folder
    public void OnDownload(ActionEvent actionEvent) {
       try {
        String inputFile = serverSharedFolder + sep + FileName;
        String outputFile = sharedFolder + sep + FileName;

        BufferedReader inFile = new BufferedReader(new FileReader(inputFile));
        BufferedWriter outFile = new BufferedWriter(new FileWriter(outputFile));
        String line = inFile.readLine();
        while (line != null) {
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
    // refresh after downloading
    initialize();
    }

    // when upload button is clicked (after file selection through clicking), it will send file over to server folder
    public void OnUpload(ActionEvent actionEvent) {
        try {
            String inputFile = sharedFolder + sep + FileName;
            String outputFile = serverSharedFolder + sep + FileName;

            BufferedReader inFile = new BufferedReader(new FileReader(inputFile));
            BufferedWriter outFile = new BufferedWriter(new FileWriter(outputFile));
            String line = inFile.readLine();
            while (line != null) {
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
        // refresh after uploading
        initialize();
    }

    // connects to the server and allows user to upload/download to server
    public void OnCommand(ActionEvent actionEvent){
        Client client = new Client(computerName, sharedFolder, serverSharedFolder);
        client.Connect();
    }
    public void setFileName(String Filename) { this.FileName = FileName; }
}

