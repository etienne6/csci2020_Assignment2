package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread {

    Socket socket;
    boolean shouldRun = true;

    public ServerConnection(Socket socket){
        super();
        this.socket = socket;
    }

    public void run(){
        //under the run file is where we will put the commmand stuff!!
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
