package org.motadata;


import org.motadata.consumer.MessageConsumer;
import org.motadata.message.MessageQueue;
import org.motadata.producer.MessageProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class represents the main application entry point for a message-driven application.
 * It creates a message queue, producer, and consumer threads, and manages their execution.
 */
public class App {

    // A logger instance for logging application-level messages.

    public static void main( String[] args ) {
        // Create a message queue for communication between producer and consumer
        MessageQueue messageQueue = new MessageQueue();

        // Create a message producer that will generate and add 10 messages to the queue
        MessageProducer producer = new MessageProducer(messageQueue, 10);

        // Create a message consumer that will consume messages from the queue
        MessageConsumer consumer = new MessageConsumer(messageQueue);

        // Create a fixed thread pool with 2 threads for producer and consumer
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit the producer and consumer tasks to the executor
        executor.submit(producer);
        executor.submit(consumer);

        // Wait for the executor to terminate (up to 10 seconds)
        try {
             executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        // Shutdown the executor (ensures no new tasks are submitted)
        executor.shutdown();

        // Log summary information about consumed messages
        System.out.println("Successfully Consumed " +  consumer.getSuccessCount() + " Messages");
        System.out.println("Failed to consume " + consumer.getFailureCount() + " Messages");
    }
}
