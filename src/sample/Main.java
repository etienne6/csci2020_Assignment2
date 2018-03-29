/*
Mir Afgan Talpur
Etienne Caronan
 */

package sample;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import java.io.File;
        import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String computerName;
        String sharedFolder;
        String serverSharedFolder;
        String sep = System.getProperty("file.separator");

        // take in computer name and shared folder as command line arguments
        Parameters params = getParameters();
        List<String> parameters = params.getRaw();

        computerName = parameters.get(0);
        sharedFolder = parameters.get(1);

        // define shared server folder
        serverSharedFolder = sharedFolder + sep + "ServerSharedFolder";

        // if it doesn't exist, create it
        File directory = new File(serverSharedFolder);
        if (! directory.exists()){
            directory.mkdir();
        }

        // create instance of Client class
        if (computerName == null || sharedFolder == null){
            new Client();
        } else {
              Client client = new Client(computerName, sharedFolder, serverSharedFolder);
              client.Connect();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        // Create a controller instance, pass command line arguments as parameters
        Controller controller = new Controller(computerName,sharedFolder,serverSharedFolder);

        // Set it in the FXMLLoader
        loader.setController(controller);

        // basic UI for stage
        Parent root = (Parent)loader.load();
        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 550, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

