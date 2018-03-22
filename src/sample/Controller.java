package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class Controller {

    private ListView<sample.ProjectFile> localFiles = new ListView<>();
    private ListView<ProjectFile> serverFiles = new ListView<>();
    private Button downloadButton;
    private Button uploadButton;

    public void OnDownload(ActionEvent actionEvent) {
    }

    public void OnUpload(ActionEvent actionEvent) {
    }
}

