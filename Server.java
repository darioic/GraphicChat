import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	private static final ConcurrentHashMap<String, MySocket> serv = new ConcurrentHashMap<>();

	public static void main(String[] args) {

		MyServerSocket servsock = new MyServerSocket(65432);

		while (true) {
			MySocket sock = servsock.accept();

			new Thread() {
				public void run() {
					try {
						sock.print("[SERVIDOR]> Porfavor introduzca su nombre de usuario: ");
					} catch (IOException e) {
						e.printStackTrace();
					}
					String name = sock.readLine();
					serv.put(name, sock);
					String sms;
					while ((sms = sock.readLine()) != null) {
						snd_msg(sms, name);
						System.out.println(name + " --> " + sms);
						if (sms.equals("SALIR")) {
							break;
						}
					}
					serv.remove(name);
					sock.close();
				}
			}.start();
		}
	}

	public static void snd_msg(String sms, String name) {
		MySocket sock;
		for (Map.Entry<String, MySocket> user : serv.entrySet()) {
			sock = user.getValue();
			if (!name.equals(user.getKey())) {
				try {
					sock.print(name + "> " + sms);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}
}