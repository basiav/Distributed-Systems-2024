import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        channel = connection.createChannel();

        // SEND
        String queue_hip_name, queue_knee_name, queue_elbow_name;
        queue_hip_name = channel.queueDeclare(QUEUE_NAME_HIP, false, false, false, null).getQueue();
        queue_knee_name = channel.queueDeclare(QUEUE_NAME_KNEE, false, false, false, null).getQueue();
        queue_elbow_name = channel.queueDeclare(QUEUE_NAME_ELBOW, false, false, false, null).getQueue();

        System.out.println("created work queue: " + queue_hip_name);
        System.out.println("created work queue: " + queue_knee_name);
        System.out.println("created work queue: " + queue_elbow_name);

        // RECEIVE
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        queueName = channel.queueDeclare().getQueue();
        String bindingKey = "doctor." + _name;
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        System.out.println("created publish/subscribe queue: " + queueName);

        _consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.printf("[%s] Results received: %s %n", _name, message);
            }
        };

    }

    public void requestRandomExamination() throws IOException {
        String patientName = patientNames[random.nextInt(patientNames.length)];
        String examinationType = ExaminationType.values()[random.nextInt(ExaminationType.values().length)].toString();

        String message = _name + "." + patientName + "." + examinationType;

        String queue_name = "queue_" + examinationType.toLowerCase();
        channel.basicPublish("", queue_name, null, message.getBytes());
        System.out.printf("[%s] Sent: %s %n", _name, message);
    }

    @Override
    public void run() {
//        while (true)
////        for (String patient : patientNames)
//        try {
////            Thread.sleep(random.nextInt(2000) + 900);
//            Thread.sleep(random.nextInt(2000) + 5000);
//            channel.basicConsume(queueName, true, _consumer);
//            requestRandomExamination();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        String[] sampleExaminations = ExamplesProvider.sampleExaminations.get(_name);

        for (int i = 0; i < sampleExaminations.length; i++) {
            try {
                Thread.sleep(random.nextInt(2000) + 5000);
                channel.basicConsume(queueName, true, _consumer);

                String patientName = patientNames[i];
                String examinationType = sampleExaminations[i];

                String message = _name + "." + patientName + "." + examinationType;

                String queue_name = "queue_" + examinationType.toLowerCase();
                channel.basicPublish("", queue_name, null, message.getBytes());
                System.out.printf("[%s] Sent: %s %n", _name, message);

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
