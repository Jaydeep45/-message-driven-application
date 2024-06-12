package org.motadata.producer;

import org.motadata.message.Message;
import org.motadata.message.MessageQueue;


/**
 * This class represents a message producer in a message-driven application.
 * It is responsible for generating and adding messages to a message queue.
 */
public final class MessageProducer implements Runnable{

    // The message queue where the producer adds the generated messages.
    private final MessageQueue messageQueue;
    // The total number of messages this producer will generate.
    private final int numberOfMessage;

    /**
     * Constructs a new MessageProducer instance with the provided message queue and number of messages to generate.
     *
     * @param messageQueue the message queue to which messages will be added (must not be null)
     * @param numberOfMessage the total number of messages to generate (must be greater than or equal to 1)
     * @throws IllegalArgumentException if the provided messageQueue is null or the numberOfMessage is less than 1
     */
    public MessageProducer(MessageQueue messageQueue, int numberOfMessage) {
        if(messageQueue == null)
            throw new IllegalArgumentException("Message Queue cannot be null");
        if(numberOfMessage < 1)
            throw new IllegalArgumentException("Number of Messages cannot be less than 1");
        this.messageQueue = messageQueue;
        this.numberOfMessage = numberOfMessage;
    }

    /**
     * This method implements the logic for the producer thread. It iterates for the specified number of messages,
     * generates a new message object for each iteration, and adds it to the message queue using the `produce` method.
     * Any `InterruptedException` encountered during the `produce` call is caught and logged as an error.
     */
    @Override
    public void run() {
        for (int i = 0; i < this.numberOfMessage; i++) {
            try {
                Message message = new Message("Message " + i);
                messageQueue.produce(message);
            } catch (InterruptedException e) {
                System.err.println("Failed to produce message. Reason: " + e.getMessage());
            }
        }
    }
}
