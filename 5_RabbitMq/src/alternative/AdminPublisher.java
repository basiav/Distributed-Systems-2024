package alternative;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;


public class AdminPublisher {
    public static final String ADMIN_EXCHANGE_NAME = "ADMIN_EXCHANGE";

    public static void main(String[] args) throws IOException, TimeoutException {
        AdminPublisher adminPublisher = new AdminPublisher();
        adminPublisher.run();
    }

    private void run() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channelOut = connection.createChannel();

        // Set up admin exchange for sending
        channelOut.exchangeDeclare(ADMIN_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);


        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // read msg
            System.out.println("Enter message: ");
            String message = br.readLine();

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            // publish
            channelOut.basicPublish(ADMIN_EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message);
        }
    }

}
