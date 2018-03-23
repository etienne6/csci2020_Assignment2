package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String computerName;
        String sharedFolder;

        // take in computer name and shared folder as command line arguments
        Parameters params = getParameters();
        List<String> parameters = params.getRaw();

        computerName = parameters.get(0);
        sharedFolder = parameters.get(1);

        System.out.println(computerName);
        System.out.println(sharedFolder);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = (Controller)loader.getController();
        controller.setSharedFolder(sharedFolder);

        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 550, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
