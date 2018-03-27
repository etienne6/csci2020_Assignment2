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

    public int socketNumber = 8080;
    private String computerName;
    private String sharedFolder;

    ServerSocket ss;
    ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
    boolean shouldRun = true;

    public Server() {
        setComputerName("127.0.0.1");
        setComputerName(".");
    }

    public Server(String computerName, String sharedFolder) {
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
    }

    public void Connect() {
        try {
            ss = new ServerSocket(socketNumber);
            while (shouldRun) {
                System.out.println("Looking for client");

                Socket s = ss.accept();
                System.out.println("Connection Established");
                ServerConnection sc = new ServerConnection(s, this);
                sc.start();
                connections.add(sc);
                ss.close();
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

    public String getComputerName() {
        return this.computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getSharedFolder() {
        return this.sharedFolder;
    }

    public void setSharedFolder(String sharedFolder) {
        this.sharedFolder = sharedFolder;
    }

}
