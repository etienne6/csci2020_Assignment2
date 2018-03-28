package sample;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

        import java.net.Socket;
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


        serverSharedFolder = sharedFolder + sep + "ServerSharedFolder";
        System.out.println(serverSharedFolder);

        if (computerName == null || sharedFolder == null){
            new Client();
            //new Server();
        } else {
              //Server server = new Server(computerName, sharedFolder);
              //server.Connect();
              Client client = new Client(computerName, sharedFolder);
              client.Connect();

        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        // Create a controller instance
        Controller controller = new Controller(computerName,sharedFolder,serverSharedFolder);

        // Set it in the FXMLLoader
        loader.setController(controller);


        //Controller controller = (Controller)loader.getController();
        //controller.setSharedFolder(sharedFolder);
        Parent root = (Parent)loader.load();
        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 550, 500));
        primaryStage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

