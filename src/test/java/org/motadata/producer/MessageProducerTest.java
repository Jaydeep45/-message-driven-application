package org.motadata.producer;

import org.motadata.message.Message;
import org.motadata.message.MessageQueue;
import org.motadata.producer.MessageProducer;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

public class MessageProducerTest {


    // Produces the correct number of messages
    @Test
    public void test_produces_correct_number_of_messages() throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue();
        int numberOfMessages = 5;
        MessageProducer producer = new MessageProducer(messageQueue, numberOfMessages);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        producerThread.join();
        for (int i = 0; i < numberOfMessages; i++) {
            assertNotNull(messageQueue.consume());
        }
    }

    // Messages are produced in the correct order
    @Test
    public void test_messages_produced_in_correct_order() throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue();
        int numberOfMessages = 5;
        MessageProducer producer = new MessageProducer(messageQueue, numberOfMessages);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        producerThread.join();
        for (int i = 0; i < numberOfMessages; i++) {
            assertEquals("Message " + i, messageQueue.consume().getMessage());
        }
    }

    // Handles null message queue in constructor
    @Test(expected = IllegalArgumentException.class)
    public void test_handles_null_message_queue_in_constructor() {
        new MessageProducer(null, 5);
    }

    // Handles null message queue in constructor
    @Test(expected = IllegalArgumentException.class)
    public void test_handles_number_of_message_queue_in_constructor() {
        new MessageProducer(new MessageQueue(), 0);
    }
}