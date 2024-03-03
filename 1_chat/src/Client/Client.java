package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static int portNumber = 12345;
    static String hostName = "localhost";

    public static void main(String[] args) {
        System.out.println("---CLIENT----");

        try (Socket socket = new Socket(hostName, portNumber)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner stdin = new Scanner(System.in);
            System.out.print("Enter your nickname: ");
            String id = stdin.nextLine();
            out.println(id);

            new Thread(listenTCP(in)).start();

            DatagramSocket datagramSocket = new DatagramSocket(socket.getLocalPort());
            new Thread(listenUDP(datagramSocket)).start();

            // Type message
            while (true) {
                String message = stdin.nextLine();

                if (message.startsWith("U")) {
                    System.out.println("UDPP");
                    byte[] msgBytes = message.getBytes();
                    int port = socket.getPort();
                    InetAddress address = InetAddress.getByName("localhost");
                    datagramSocket.send(new DatagramPacket(msgBytes, msgBytes.length, address, port));
                }
                else {
                    out.println(message);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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
            try
            {
                while (!datagramSocket.isClosed())
                {
                    byte[] buff = new byte[1024];
                    DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);
                    datagramSocket.receive(datagramPacket);
                    String msg = new String(buff);
                    System.out.printf("Received message: \"%s\"%n", msg);
                }
            }
            catch (IOException e)
            { e.printStackTrace(); }
            finally
            {
                if (datagramSocket != null)
                    datagramSocket.close();
            }
        };
    }
}
