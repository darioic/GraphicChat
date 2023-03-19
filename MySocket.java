import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket {

    private Socket sock;

    public MySocket(String host, int port) {
        try {
            sock = new Socket(host, port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public MySocket(Socket s) {
        sock = s;
    }

    public String readLine() {
        try {
            InputStream input = sock.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            line = reader.readLine();
            return line;
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return null;
    }

    public void print(String line) throws IOException {
        try {
            OutputStream output = sock.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(line);
        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }

    public void close() {
        try {
            sock.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
