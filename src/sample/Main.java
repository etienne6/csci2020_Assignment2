package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main extends Application {

    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        String computerName;
        String sharedFolder;

        // take in computer name and shared folder as command line arguments
        Parameters params = getParameters();
        List<String> parameters = params.getRaw();

        computerName = parameters.get(0);
        sharedFolder = parameters.get(1);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        // Create a controller instance
        Controller controller = new Controller(computerName,sharedFolder);

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
        new Client();
        Application.launch(args);
    }
    public static class Client{
        public Client(){
            try {
                s = new Socket("localhost", 3333);
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                listenforInput();
            } catch(UnknownHostException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void listenforInput(){
        Scanner console = new Scanner(System.in);
        while(true){
            while(!console.hasNext()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String input = console.nextLine();

                try {
                    dout.writeUTF(input);

                    while(din.available() == 0){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    String reply = din.readUTF();
                    System.out.println(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
