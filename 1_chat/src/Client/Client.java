package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client implements Runnable {
    static int serverPort = 12345;
    static String hostName = "localhost";
    static int multicastPort = 12346;
    static String multicastAddress = "230.1.1.1";
    static String ASCII_ART_PATH = "ascii_art.txt";

    private DatagramSocket datagramSocket;
    private MulticastSocket multicastSocket;

    public static void main(String[] args) {
        System.out.println("---CLIENT----");
        Client client = new Client();
        client.run();
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(hostName, serverPort)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner stdin = new Scanner(System.in);
            System.out.print("Enter your nickname: ");
            String id = stdin.nextLine();
            out.println(id);

            // TCP
            new Thread(listenTCP(in)).start();

            // UDP
            this.datagramSocket = new DatagramSocket(socket.getLocalPort());
            new Thread(listenUDP(datagramSocket)).start();

            // Multicast
            this.multicastSocket = new MulticastSocket(Client.multicastPort);
            InetAddress multicastGroupAddress = InetAddress.getByName(Client.multicastAddress);
            multicastSocket.joinGroup(multicastGroupAddress);
            new Thread(listenUDP(multicastSocket)).start();

            // Type message
            while (true) {
                String message = stdin.nextLine();

                // UDP
                if (message.startsWith("U") || message.startsWith("M")) {
                    char type = message.charAt(0);

                    message = id + ": " + message + "\n";
                    byte[] msgBytes = message.getBytes();

                    if (type == 'U')
                        datagramSocket.send(new DatagramPacket(
                                msgBytes, msgBytes.length, InetAddress.getByName(Client.hostName), socket.getPort())
                        );
                    else
                        datagramSocket.send(new DatagramPacket(
                                msgBytes, msgBytes.length, multicastGroupAddress, Client.multicastPort)
                        );
                }
                // ASCII ART
                else if (message.startsWith("ASCII_ART")) {
                    try {
                        message = new String(Files.readAllBytes(Paths.get(ASCII_ART_PATH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    message = id + ":\n" + message + "\n";
                    byte[] msgBytes = message.getBytes();
                    datagramSocket.send(new DatagramPacket(
                            msgBytes, msgBytes.length, InetAddress.getByName(Client.hostName), socket.getPort())
                    );
                }
                // TCP
                else {
                    out.println(message);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (datagramSocket != null)
                datagramSocket.close();

            if (multicastSocket != null)
            {
                try
                { multicastSocket.leaveGroup(InetAddress.getByName(Client.multicastAddress)); }
                catch (IOException e)
                { e.printStackTrace(); }
                multicastSocket.close();
            }
        }
    }

    private static Runnable listenTCP(BufferedReader in) {
        return () -> {
            try {
                while (true) {
                    String message = in.readLine().trim();
                    System.out.printf("Received message: \"%s\"%n", message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private static Runnable listenUDP(DatagramSocket datagramSocket) {
        return () -> {
            try {
                while (!datagramSocket.isClosed()) {
                    byte[] buff = new byte[1024];
                    DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);
                    datagramSocket.receive(datagramPacket);
                    String msg = new String(buff).trim();
                    System.out.printf("Received message: \"%s\"%n", msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (datagramSocket != null)
                    datagramSocket.close();
            }
        };
    }
}
