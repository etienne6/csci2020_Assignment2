package sample;

        import javafx.application.Application;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.stage.DirectoryChooser;

        import java.io.File;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.ArrayList;
        import java.util.List;

public class Server {

    public int socketNumber;
    private String computerName;
    private String sharedFolder;

    ServerSocket ss;
    ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
    boolean shouldRun = true;

    public Server() {
        setComputerName("127.0.0.1");
        setSharedFolder(".");
        setSocketNumber(8080);
    }

    public Server(String computerName, String sharedFolder, int socketNumber) {
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        this.socketNumber = socketNumber;
    }

    public void Connect() {
        try {
            ss = new ServerSocket(socketNumber);
            while (shouldRun) {
                System.out.println("Looking for client");
                ServerConnection sc = new ServerConnection(computerName, sharedFolder, ss.accept());
                sc.start();
                System.out.println("Connection Established");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // get files from specified folder and add to ListView
    public static ObservableList<String> getServerFiles(String pathOfFiles) {
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

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public void setSharedFolder(String sharedFolder) {
        this.sharedFolder = sharedFolder;
    }

    public void setSocketNumber(int socketNumber) {
        this.socketNumber = socketNumber;
    }

    public static void main(String args[]){
        String computerName;
        String sharedFolder;

        computerName = args[0];
        sharedFolder = args[1];

        System.out.println(computerName);

        System.out.println(sharedFolder);

        Server server = new Server(computerName, sharedFolder, 8080);
        server.Connect();
    }

}
