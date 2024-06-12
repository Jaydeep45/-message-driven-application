package org.motadata.message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents a thread-safe message queue for a message-driven application.
 * It provides methods for producers to add messages and consumers to retrieve messages.
 */
public class MessageQueue {

    // The internal queue implementation using a BlockingQueue for thread-safe operations.
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

    /**
     * Adds a message to the queue. This method might block if the queue is full.
     *
     * @param message the message to be added (must not be null)
     * @throws InterruptedException if interrupted while waiting to add the message
     */
    public void produce(Message message) throws InterruptedException {
        queue.put(message);
    }


    /**
     * Retrieves and removes a message from the queue. This method might block if the queue is empty.
     *
     * @return the retrieved message
     * @throws InterruptedException if interrupted while waiting to retrieve a message
     */
    public Message consume() throws InterruptedException {
        return queue.take();
    }

}
