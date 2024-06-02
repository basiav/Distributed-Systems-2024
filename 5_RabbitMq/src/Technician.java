package src;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Technician {
    // Exchange for results - to send to doctors
    final String EXCHANGE_NAME = "MED_RESULTS_EXCHANGE";
    private final Random random = new Random();
    private final String techId;

    private final Channel channel;

    // ADMIN mode functionalities
    // to listen for admin's info and forward action results to admin
    private final Consumer adminConsumer;
    private String adminListenerQueueName;


    public Technician(String technicianId, ExaminationType t1, ExaminationType t2) throws IOException, TimeoutException {
        techId = "[%s %s %s]".formatted(technicianId, t1.toString(), t2.toString());

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        channel = connection.createChannel();

        // 2 work queues for 2 examination types a technician can handle
        String QUEUE_NAME_1 = "queue_" + t1.toString().toLowerCase();
        String QUEUE_NAME_2 = "queue_" + t2.toString().toLowerCase();

        channel.queueDeclare(QUEUE_NAME_1, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME_2, false, false, false, null);

//        channel.basicQos(1);

        // Exchange to send results back to the doctor
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        System.out.printf("Created src.Technician %s subscribed to queues %s %s%n", this.techId, QUEUE_NAME_1, QUEUE_NAME_2);

        Consumer workQueueConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(techId + " received: " + message);

                // HANDLING ADMIN
                // Send to admin info, that we have accepted the examination request
                String forwardExaminationAcceptance = techId + "." + message;
                channel.basicPublish("", AdminLogger.ADMIN_QUEUE_IN, null, forwardExaminationAcceptance.getBytes("UTF-8"));

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
                System.out.printf("%s sent: %s %s%n", techId, returnKey, returnMsg);

                // HANDLING ADMIN
                // Forward examination results to admin
                String forwardResultsMsg = techId + "." + returnMsg;
                channel.basicPublish("", AdminLogger.ADMIN_QUEUE_IN, null, forwardResultsMsg.getBytes("UTF-8"));
            }
        };

        channel.basicConsume(QUEUE_NAME_1, true, workQueueConsumer);
        channel.basicConsume(QUEUE_NAME_2, true, workQueueConsumer);

//        channel.basicConsume(QUEUE_NAME_1, false, consumer);
//        channel.basicConsume(QUEUE_NAME_2, false, consumer);

        // ADMIN
        channel.exchangeDeclare(AdminPublisher.ADMIN_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        adminListenerQueueName = techId + "_admin_listener_queue";
        String qn = channel.queueDeclare(adminListenerQueueName, false, false, false, null).getQueue();
        channel.queueBind(qn, AdminPublisher.ADMIN_EXCHANGE_NAME, "");

        adminConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.printf("%s Info from ADMIN received: %s %n", techId, message);
            }
        };

        // Listen to admin's info
        channel.basicConsume(adminListenerQueueName, true, adminConsumer);
    }
}
