import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("BusyWait")
public class Doctor extends Thread {
    private static final String[] patientNames = { "Alice", "Bob", "Charlie", "David", "Emma", "Felix" };
    private final Random random = new Random();
    private final String _name;

    private final Consumer _consumer;
    private final Channel channel;

    final String EXCHANGE_NAME = "MED_EXCHANGE";

    // Sending tasks to technicians
    final String QUEUE_NAME_HIP = "queue_hip";
    final String QUEUE_NAME_KNEE = "queue_knee";
    final String QUEUE_NAME_ELBOW = "queue_elbow";

    // Obtaining results from technicians
    private final String queueName;

    public Doctor(String name) throws IOException, TimeoutException {
        _name = name;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        // SEND
        channel = connection.createChannel();

        String queue_hip_name, queue_knee_name, queue_elbow_name;
        queue_hip_name = channel.queueDeclare(QUEUE_NAME_HIP, false, false, false, null).getQueue();
        queue_knee_name = channel.queueDeclare(QUEUE_NAME_KNEE, false, false, false, null).getQueue();
        queue_elbow_name = channel.queueDeclare(QUEUE_NAME_ELBOW, false, false, false, null).getQueue();

        System.out.println("------------------");
        System.out.println("created work queue: " + queue_hip_name);
        System.out.println("created work queue: " + queue_knee_name);
        System.out.println("created work queue: " + queue_elbow_name);
        System.out.println("------------------");

        // RECEIVE
        String bindingKey = "doctor." + _name;
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        System.out.println("created publish/subscribe queue: " + queueName);

        _consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(message);
            }
        };

    }

    public void requestRandomExamination() throws IOException {
        String patientName = patientNames[random.nextInt(patientNames.length)];
        String examinationType = ExaminationType.values()[random.nextInt(ExaminationType.values().length)].toString();

        String key = "technician." + examinationType;
        String message = _name + "." + patientName + "." + examinationType;

        String queue_name = "queue_" + examinationType.toLowerCase();
        channel.basicPublish("", queue_name, null, message.getBytes());
        System.out.printf("[%s] Sent: %s %s%n", _name, key, message);
    }

    @Override
    public void run() {
        while (true)
//        for (String patient : patientNames)
        try {
//            Thread.sleep(random.nextInt(2000) + 900);
            Thread.sleep(random.nextInt(2000) + 5000);
            channel.basicConsume(queueName, true, _consumer);
            requestRandomExamination();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
