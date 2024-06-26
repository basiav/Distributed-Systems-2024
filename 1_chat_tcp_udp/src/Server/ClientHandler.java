package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler implements Runnable{
    private final Server server;
    private final Socket socket;

    PrintWriter out;
    BufferedReader in;

    String id;

    public ClientHandler(Socket clientSocket, Server server) throws IOException {
        this.socket = clientSocket;
        this.server = server;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            this.id = in.readLine().trim();
            System.out.println("Client connected, id: " + id);

            while(true) {
                String msg = in.readLine().trim();
                System.out.printf("Sending new message from [%s] to other clients...%n", id);
                sendToAll(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendToAll(String msg) {
        for (ClientHandler client : server.getClients()) {
            if (!client.getId().equals(id))  {
                System.out.printf("...sending to [%s]%n", client.getId());
                client.out.println(String.format("%s: %s", id, msg));
            }
        }
    }

    public String getId() {
        return id;
    }
    public Socket getSocket() { return socket; }
}
