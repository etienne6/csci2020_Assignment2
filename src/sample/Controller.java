package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    private ListView<String> localFiles = new ListView<>();
    private ListView<String> serverFiles = new ListView<>();
    private Button downloadButton;
    private Button uploadButton;
    private String sharedFolder;
    List<String> fileList = new ArrayList<>();

    public void setSharedFolder (String sharedFolder){ this.sharedFolder = sharedFolder; }

    // tried to get the listview working, can't get it to display
    public ListView<String> getFiles() {
        File path = new File(sharedFolder);

        File[] dirContents = path.listFiles();
        List<String> fileList = new ArrayList<>();
        for (int i = 0; i <= dirContents.length; i++) {
            if (dirContents[i].isFile()) {
                fileList.add(dirContents[i].getName());
            }
        }
        ObservableList<String> files = FXCollections.observableArrayList(fileList);
        ListView<String> localFiles = new ListView<String>(files);
        localFiles.getItems().addAll(files);
        return localFiles;
    }

    public void OnDownload(ActionEvent actionEvent) {
    }

    public void OnUpload(ActionEvent actionEvent) {
    }


}

