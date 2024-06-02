package ManualDoctorPiloting;

import com.rabbitmq.client.*;
import src.AdminLogger;
import src.AdminPublisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


public class ManualDoctor {
    private final String docId;

    // Work queues - sending tasks to technicians
    final String QUEUE_NAME_HIP = "queue_hip";
    final String QUEUE_NAME_KNEE = "queue_knee";
    final String QUEUE_NAME_ELBOW = "queue_elbow";

    // Exchange and private queue for results - obtaining results from technicians
    final String EXCHANGE_NAME = "MED_RESULTS_EXCHANGE";

    public ManualDoctor(String doctorId) throws IOException, TimeoutException {
        this.docId = doctorId;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input doctorId:");
        String doctorId = br.readLine();

        ManualDoctor doctor = new ManualDoctor(doctorId);
        doctor.run();

        br.close();
    }

    private void run() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // SEND - to examination work queues
        String queue_hip_name, queue_knee_name, queue_elbow_name;
        queue_hip_name = channel.queueDeclare(QUEUE_NAME_HIP, false, false, false, null).getQueue();
        queue_knee_name = channel.queueDeclare(QUEUE_NAME_KNEE, false, false, false, null).getQueue();
        queue_elbow_name = channel.queueDeclare(QUEUE_NAME_ELBOW, false, false, false, null).getQueue();

        System.out.println("created work queue: " + queue_hip_name);
        System.out.println("created work queue: " + queue_knee_name);
        System.out.println("created work queue: " + queue_elbow_name);

        // RECEIVE - from the subscribe/publish results exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();
        String bindingKey = "doctor." + docId;
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        System.out.println(docId + " created publish/subscribe queue: " + queueName);

        Consumer examinationResultsConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.printf("[%s] Results received: %s %n", docId, message);

                // ADMIN functionalities
                // Forward results to ADMIN
                String forwardResultsMsg = docId + "." + message;
                channel.basicPublish("", AdminLogger.ADMIN_QUEUE_IN, null, forwardResultsMsg.getBytes("UTF-8"));
            }
        };

        // Listen on examination results queue
        channel.basicConsume(queueName, true, examinationResultsConsumer);

        // ADMIN functionalities
        channel.exchangeDeclare(AdminPublisher.ADMIN_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String adminListenerQueueName = docId + "_admin_listener_queue";
        String qn = channel.queueDeclare(adminListenerQueueName, false, false, false, null).getQueue();
        System.out.println(docId + "qn: " + qn);
        channel.queueBind(qn, AdminPublisher.ADMIN_EXCHANGE_NAME, "");

        Consumer adminMessagesConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.printf("[%s] Info from ADMIN received: %s %n", docId, message);
            }
        };

        // Listen on admin's info queue
        channel.basicConsume(adminListenerQueueName, true, adminMessagesConsumer);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Input patient name: ");
            String patientName = br.readLine();

            System.out.println("Request examination - hip, knee or elbow: ");
            String examinationType = br.readLine();

            if ("exit".equals(patientName) || "exit".equals(examinationType)) {
                break;
            }

            String message = docId + "." + patientName + "." + examinationType;

            String queue_name = "queue_" + examinationType.toLowerCase();
            channel.basicPublish("", queue_name, null, message.getBytes());
            System.out.printf("[%s] Sent: %s %n", docId, message);

            // ADMIN functionalities
            // Same message to admin - copy to admin
            channel.basicPublish("", AdminLogger.ADMIN_QUEUE_IN, null, message.getBytes("UTF-8"));
        }
    }
}
