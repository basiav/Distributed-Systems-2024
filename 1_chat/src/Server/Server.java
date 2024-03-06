package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    static int portNumber = 12345;
    static int maxClients = 10;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    public void run() {
        // TCP
        ListenerTCP listenerTCP = new ListenerTCP(this, portNumber, maxClients);
        new Thread(listenerTCP).start();

        // UDP
        ListenerUDP listenerUDP = new ListenerUDP(this, portNumber);
        new Thread(listenerUDP).start();


    }

    public List<ClientHandler> getClients() {
        return clients;
    }
}