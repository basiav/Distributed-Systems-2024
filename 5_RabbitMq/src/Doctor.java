package src;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;


public class Doctor extends Thread {
    private final String docId;

    private final Channel channel;

    // Work queues - sending tasks to technicians
    final String QUEUE_NAME_HIP = "queue_hip";
    final String QUEUE_NAME_KNEE = "queue_knee";
    final String QUEUE_NAME_ELBOW = "queue_elbow";

    // Exchange and private queue for results - obtaining results from technicians
    final String EXCHANGE_NAME = "MED_RESULTS_EXCHANGE";
    private final String resultsQueueName;
    private final Consumer resultsConsumer;

    // ADMIN mode functionalities
    // private queue for info from admin
    private final String adminListenerQueueName;
    private final Consumer adminConsumer;

    public Doctor(String doctorId) throws IOException, TimeoutException {
        docId = doctorId;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        // SEND - to examination work queues
        String queue_hip_name, queue_knee_name, queue_elbow_name;
        queue_hip_name = channel.queueDeclare(QUEUE_NAME_HIP, false, false, false, null).getQueue();
        queue_knee_name = channel.queueDeclare(QUEUE_NAME_KNEE, false, false, false, null).getQueue();
        queue_elbow_name = channel.queueDeclare(QUEUE_NAME_ELBOW, false, false, false, null).getQueue();

        System.out.println(this.docId + " work queue: " + queue_hip_name);
        System.out.println(this.docId + " work queue: " + queue_knee_name);
        System.out.println(this.docId + " work queue: " + queue_elbow_name);

        // RECEIVE - from the subscribe/publish results exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        resultsQueueName = doctorId + "_results_queue";
        String bindingKey = "doctor." + docId;
        String qn = channel.queueDeclare(resultsQueueName, false, false, false, null).getQueue();
        channel.queueBind(qn, EXCHANGE_NAME, bindingKey);
        System.out.println(docId + " created publish/subscribe queue: " + qn + " with binding key " + bindingKey);

        resultsConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.printf("[%s] Results received: %s %n", docId, message);

                // Send the same message to ADMIN
                String forwardResultsMsg = docId + "." + message;
                channel.basicPublish("", AdminLogger.ADMIN_QUEUE_IN, null, forwardResultsMsg.getBytes("UTF-8"));
            }
        };

        // ADMIN
        channel.exchangeDeclare(AdminPublisher.ADMIN_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        adminListenerQueueName = docId + "_admin_listener_queue";
        qn = channel.queueDeclare(adminListenerQueueName, false, false, false, null).getQueue();
        System.out.println(docId + " created admin queue: " + qn);
        channel.queueBind(qn, AdminPublisher.ADMIN_EXCHANGE_NAME, "");

        adminConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.printf("[%s] Info from ADMIN received: %s %n", docId, message);
            }
        };
    }

    @Override
    public void run() {
        try {
            // Listen on the results queue with results from technicians
            channel.basicConsume(resultsQueueName, true, resultsConsumer);
            // Listen on the admin's info queue
            channel.basicConsume(adminListenerQueueName, true, adminConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] sampleExaminations = ExamplesProvider.sampleExaminations.get(docId);
        String[] patientNames = ExamplesProvider.samplePatients.get(docId);

        // Request examinations
        Random random = new Random();
        for (int i = 0; i < sampleExaminations.length; i++) {
            try {
                Thread.sleep(random.nextInt(2000) + 5000);

                String patientName = patientNames[i];
                String examinationType = sampleExaminations[i];

                String message = docId + "." + patientName + "." + examinationType;

                String queue_name = "queue_" + examinationType.toLowerCase();
                channel.basicPublish("", queue_name, null, message.getBytes());
                System.out.printf("[%s] Sent: %s %n", docId, message);

                // HANDLING ADMIN
                // Same message to admin - copy to admin
                channel.basicPublish("", AdminLogger.ADMIN_QUEUE_IN, null, message.getBytes("UTF-8"));
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
