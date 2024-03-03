package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    static int portNumber = 12345;
    static int maxClients = 10;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }


    public void run() {

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