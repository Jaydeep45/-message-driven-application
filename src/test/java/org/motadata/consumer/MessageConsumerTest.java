package org.motadata.consumer;

import org.motadata.consumer.MessageConsumer;

import org.junit.Test;
import org.motadata.message.Message;
import org.motadata.message.MessageQueue;

import static org.junit.Assert.*;

public class MessageConsumerTest {


    // Successfully consumes a message from the queue
    @Test
    public void test_successfully_consumes_message() throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue();
        MessageConsumer consumer = new MessageConsumer(messageQueue);
        Message message = new Message("Test Message");
        messageQueue.produce(message);
    
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    
        Thread.sleep(100); // Allow some time for the consumer to consume the message
    
        assertEquals(1, consumer.getSuccessCount());
        consumerThread.interrupt(); // Stop the consumer thread
    }



    // Handles null message queue in constructor
    @Test(expected = IllegalArgumentException.class)
    public void test_handles_null_message_queue() {
        new MessageConsumer(null);
    }

    // Handles InterruptedException during message consumption
    @Test
    public void test_handles_interrupted_exception() throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue();
        MessageConsumer consumer = new MessageConsumer(messageQueue);
    
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    
        consumerThread.interrupt(); // Interrupt the consumer thread
    
        Thread.sleep(100); // Allow some time for the interruption to be processed
    
        assertEquals(1, consumer.getFailureCount());
    }

    // Continues consuming messages after an InterruptedException
    @Test
    public void test_continues_after_interrupted_exception() throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue();
        MessageConsumer consumer = new MessageConsumer(messageQueue);
    
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    
        consumerThread.interrupt(); // Interrupt the consumer thread
    
        Thread.sleep(100); // Allow some time for the interruption to be processed
    
        assertEquals(1, consumer.getFailureCount());
    
        Message message = new Message("Test Message");
        messageQueue.produce(message);
    
        Thread.sleep(100); // Allow some time for the consumer to consume the message
    
        assertEquals(1, consumer.getSuccessCount());
        consumerThread.interrupt(); // Stop the consumer thread
    }

}