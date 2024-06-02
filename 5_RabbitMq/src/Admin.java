package src;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Admin {
    public static final String ADMIN_EXCHANGE_NAME = "ADMIN_EXCHANGE";
    public static final String ADMIN_QUEUE_IN = "ADMIN_queue_in";

    // Two channels because of possible simultaneous channel usage
    // we don't want to consume and publish at the same time
    private final Channel channelIn;
    private final Channel channelOut;
    private final Consumer logConsumer;

    public Admin() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        channelIn = connection.createChannel();
        channelOut = connection.createChannel();

        // Set up admin exchange for sending
        channelOut.exchangeDeclare(ADMIN_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // Set up admin
        channelIn.queueDeclare(ADMIN_QUEUE_IN, false, false, false, null);

        logConsumer = new DefaultConsumer(channelIn) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                String sender = message.split("\\.")[0];
                System.out.printf("Log received from [%s] full message: %s %n", sender, message);
//                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Admin admin = new Admin();
        admin.run();
    }

    private void run() throws IOException, TimeoutException {
        System.out.println("src.Admin running");

        new Thread(listenOnQueue()).start();

        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter action: SEND/LOG");
            System.out.flush();
            String action = br.readLine();
            System.out.println("Action: " + action);

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

    private Runnable listenOnQueue() {
        return () -> {
            // consume
            try {
                channelIn.basicConsume(ADMIN_QUEUE_IN, true, logConsumer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
