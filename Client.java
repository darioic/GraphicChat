import java.lang.Thread;

public class Client {
	public static void main(String[] args) {
		MySocket sock = new MySocket("localhost", 65432);
		Frontend fron = new Frontend("Client", sock);
		fron.crear_frontend("Client");
		new Thread() {
			public void run() {
				String line;
				while ((line = sock.readLine()) != null) {
					fron.send(line);
				}
			}
		}.start();
	}
}