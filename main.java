import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class main extends Application{
  public static void main(String[] args){ launch(args); }

  public void start(Stage primaryStage){
    primaryStage.setTitle("File Sharer v1.0");
    StackPane root = new StackPane();
    primaryStage.setScene(new Scene(root, 300,250));
    primaryStage.show();
  }
}
