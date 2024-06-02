package alternative;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class AdminLogger {
    public static final String ADMIN_QUEUE_IN = "ADMIN_queue_in";

    public static void main(String[] args) throws IOException, TimeoutException {
        AdminLogger adminLogger = new AdminLogger();
        adminLogger.run();
    }

    private void run() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channelIn = connection.createChannel();

        // Set up admin queue
        channelIn.queueDeclare(ADMIN_QUEUE_IN, false, false, false, null);

        Consumer logConsumer = new DefaultConsumer(channelIn) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                String sender = message.split("\\.")[0];
                System.out.printf("Log received from [%s] full message: %s %n", sender, message);
//                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // consume
        channelIn.basicConsume(ADMIN_QUEUE_IN, true, logConsumer);
    }
}
