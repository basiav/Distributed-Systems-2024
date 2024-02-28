package zad1_zad2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JavaUdpClient {

    public static void main(String[] args) throws Exception
    {
        final int PORT_NUMBER = 9008;

        System.out.println("JAVA UDP CLIENT");
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            /* Send ping */
            byte[] sendBuffer = "Client: Ping Java Udp".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, PORT_NUMBER);
            socket.send(sendPacket);

            System.out.println("Sent to Server: " + new String(sendBuffer, StandardCharsets.UTF_8));
            System.out.println("[sent to]: " + sendPacket.getSocketAddress());

            /* Receive pong */
            byte[] receiveBuffer = new byte[1024];
            Arrays.fill(receiveBuffer, (byte) 0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            System.out.println("Client received msg: " + new String(receivePacket.getData()).trim());
            System.out.println("[received from]: " + receivePacket.getSocketAddress());

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
