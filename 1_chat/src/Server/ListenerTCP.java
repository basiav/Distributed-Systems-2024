package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ListenerTCP implements Runnable {
    Server server;
    int portNumber;
    int maxClients;

    public ListenerTCP(Server server, int portNumber, int maxClients) {
        this.server = server;
        this.portNumber = portNumber;
        this.maxClients = maxClients;
    }

    @Override
    public void run() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxClients);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client on the server");

                ClientHandler client = new ClientHandler(clientSocket, this.server);
                server.getClients().add(client);
                executor.execute(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
