package org.motadata.consumer;

import org.motadata.message.Message;
import org.motadata.message.MessageQueue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents a consumer in a message-driven application.
 * It is responsible for receiving messages from a message queue and processing them.
 */
public class MessageConsumer implements Runnable {
    // The message queue from which the consumer receives messages.
    private final MessageQueue messageQueue;

    // An AtomicInteger to track the number of successfully processed messages.
    private final AtomicInteger successCount;

    // An AtomicInteger to track the number of encountered failures during processing.
    private final AtomicInteger failureCount;


    /**
     * Constructs a new MessageConsumer instance with the provided message queue.
     *
     * @param messageQueue the message queue from which to consume messages (must not be null)
     * @throws IllegalArgumentException if the provided messageQueue is null
     */
    public MessageConsumer(MessageQueue messageQueue) {
        if(messageQueue == null)
            throw new IllegalArgumentException("Message Queue cannot be null");
        this.messageQueue = messageQueue;
        this.successCount = new AtomicInteger();
        this.failureCount = new AtomicInteger();
    }

    /**
     * The main execution loop of the consumer thread. It continuously attempts to consume messages
     * from the queue, process them (logging success), and keeps track of successes and failures.
     * This method exits upon interruption (e.g., when the executor service shuts down).
     */
    @Override
    public void run() {
        while (true) {
            try {
                Message message = messageQueue.consume();
                System.out.println(message.getMessage() + " Consumed successfully!");
                successCount.incrementAndGet();
            } catch (InterruptedException e) {
                failureCount.incrementAndGet();
                System.err.println("Failed to consume message. Reason: "+  e.getMessage());
            }
        }
    }

    /**
     * Retrieves the current count of successfully processed messages.
     *
     * @return the number of successfully consumed messages
     */
    public int getSuccessCount() {
        return this.successCount.get();
    }

    /**
     * Retrieves the current count of encountered failures during processing.
     *
     * @return the number of failures encountered while consuming messages
     */
    public int getFailureCount() {
        return this.failureCount.get();
    }
}
