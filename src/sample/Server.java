package sample;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.stage.DirectoryChooser;
        import java.io.File;
        import java.net.ServerSocket;
        import java.util.ArrayList;
        import java.util.List;

public class Server {

    public int socketNumber;
    private String computerName;
    private String sharedFolder;
    private String serverSharedFolder;

    ServerSocket ss;
    boolean shouldRun = true;

    public Server() {
        setComputerName("127.0.0.1");
        setSharedFolder(".");
        setSocketNumber(8080);
    }

    public Server(String computerName, String sharedFolder, String serverSharedFolder, int socketNumber) {
        this.computerName = computerName;
        this.sharedFolder = sharedFolder;
        this.serverSharedFolder = serverSharedFolder;
        this.socketNumber = socketNumber;
    }

    public void Connect() {
        try {
            ss = new ServerSocket(socketNumber);
            while (shouldRun) {
                System.out.println("\nLooking for client");
                ServerConnection sc = new ServerConnection(computerName, sharedFolder, serverSharedFolder, ss.accept());
                System.out.println("Connection Established");
                sc.run();
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
        String serverSharedFolder;
        String sep = System.getProperty("file.separator");

        // get computer name and shared folder path as arguments
        computerName = args[0];
        sharedFolder = args[1];

        // create a server folder if it doesn't already exist
        serverSharedFolder = sharedFolder + sep + "ServerSharedFolder";

        File directory = new File(serverSharedFolder);
        if (! directory.exists()){
            directory.mkdir();
        }

        Server server = new Server(computerName, sharedFolder, serverSharedFolder, 8080);
        server.Connect();


    }

}
