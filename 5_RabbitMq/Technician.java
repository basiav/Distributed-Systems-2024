import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Technician {
    final String EXCHANGE_NAME = "MED_EXCHANGE";
    private static int __id = 1;
    private int _id;
    private final Random random = new Random();

    private final String _logName;
    private Connection connection;
    private final Channel channel;

    public Technician(ExaminationType t1, ExaminationType t2) throws IOException, TimeoutException {
        _id = __id++;
//        _logName = "[T%d %c%c]".formatted(_id, t1.toString().charAt(0), t2.toString().charAt(0));
        _logName = "[T%d %s %s]".formatted(_id, t1.toString(), t2.toString());
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        String QUEUE_NAME_1 = "queue_" + t1.toString().toLowerCase();
        String QUEUE_NAME_2 = "queue_" + t2.toString().toLowerCase();
        channel.queueDeclare(QUEUE_NAME_1, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME_2, false, false, false, null);

//        channel.basicQos(1);

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        System.out.printf("Created Technician subscribed to queues %s %s%n", QUEUE_NAME_1, QUEUE_NAME_2);
        System.out.println();

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(_logName + " Received: " + message);

                try {
                    Thread.sleep(random.nextInt(2000) + 800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

//                channel.basicAck(envelope.getDeliveryTag(), false);

                String[] attrs = message.split("\\.");
                String returnMsg = attrs[1] + " " + attrs[2] + " done";
                String returnKey = "doctor." + attrs[0];
                channel.basicPublish(EXCHANGE_NAME, returnKey,
                        null, returnMsg.getBytes(StandardCharsets.UTF_8));
                System.out.printf(_logName + " Sent: %s %s%n", returnKey, returnMsg);
            }
        };

        channel.basicConsume(QUEUE_NAME_1, true, consumer);
        channel.basicConsume(QUEUE_NAME_2, true, consumer);
//        channel.basicConsume(QUEUE_NAME_1, false, consumer);
//        channel.basicConsume(QUEUE_NAME_2, false, consumer);
    }
}
