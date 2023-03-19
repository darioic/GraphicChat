import java.io.IOException;
import java.net.ServerSocket;

public class MyServerSocket {

    private MySocket sock;
    private ServerSocket servsock;

    public MyServerSocket(int port) {
        try {
            servsock = new ServerSocket(port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public MySocket accept() {
        try {
            sock = new MySocket(servsock.accept());
            return sock;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
