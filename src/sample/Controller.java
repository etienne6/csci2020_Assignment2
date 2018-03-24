package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public String sharedFolder;

    public Controller (String sharedFolder){
        this.sharedFolder = sharedFolder;
        System.out.println("The Shared Folder is: " + sharedFolder);
    }

    @FXML
    public void initialize(){
        // set sharedFolderPath as the one from command line
        String sharedFolderPath = sharedFolder;
        // create Observable List of files from specified folder
        ObservableList<String> localFileList = getFiles(sharedFolderPath);
        // create Observable List of files from specified folder
        ObservableList<String> serverFilesList = getServerFiles(sharedFolderPath);
        // add observable list of files to ListView
        localFiles.setItems(localFileList);
        // add observable list of files to ListView
        serverFiles.setItems(localFileList);
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


}

