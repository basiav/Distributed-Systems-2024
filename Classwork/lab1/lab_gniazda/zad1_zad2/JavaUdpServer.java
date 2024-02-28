package zad1_zad2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class JavaUdpServer {

    public static void main(String[] args)
    {
        final int PORT_NUMBER = 9008;

        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;

        try{
            socket = new DatagramSocket(PORT_NUMBER);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer = "Server: Pong Java Udp".getBytes();

            while(true) {
                /* Receive ping */
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                System.out.println("Server received msg: " + new String(receivePacket.getData()).trim());
                System.out.println("[received from]: " + receivePacket.getSocketAddress());

                /* Send pong */
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }
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
