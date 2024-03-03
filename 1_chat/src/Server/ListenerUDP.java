package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ListenerUDP implements Runnable {
    Server server;
    int portNumber;

    public ListenerUDP(Server server, int portNumber) {
        this.server = server;
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        try (DatagramSocket datagramSocket = new DatagramSocket(portNumber)) {
            while(!datagramSocket.isClosed()) {
                byte[] bytes_buff = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes_buff, bytes_buff.length);
                datagramSocket.receive(packet);
                String msg = new String(bytes_buff);
                sendAll(datagramSocket, packet.getPort(), msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendAll(DatagramSocket datagramSocket, int sourcePort, String msg) {
        // TODO
        System.out.println("SendAll UDP " + msg);
        for (ClientHandler clientHandler : server.getClients()) {
            if (clientHandler.getSocket().getPort() != sourcePort) {
                try {
                    int port = clientHandler.getSocket().getPort();
                    InetAddress address = InetAddress.getByName("localhost");

                    byte[] msgBytes = msg.getBytes();
                    datagramSocket.send(new DatagramPacket(msgBytes, msgBytes.length, address, port));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
