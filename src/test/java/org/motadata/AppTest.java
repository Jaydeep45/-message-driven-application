package org.motadata;


import org.junit.Test;
import org.motadata.consumer.MessageConsumer;
import org.motadata.message.MessageQueue;
import org.motadata.producer.MessageProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class AppTest {




    // Initializes MessageQueue correctly
    @Test
    public void test_message_queue_initialization() {
        MessageQueue messageQueue = new MessageQueue();
        assertNotNull(messageQueue);
    }

    // Creates MessageProducer with correct parameters
    @Test
    public void test_message_producer_initialization() {
        MessageQueue messageQueue = new MessageQueue();
        MessageProducer producer = new MessageProducer(messageQueue, 10);
        assertNotNull(producer);
    }

    // Creates MessageConsumer with correct parameters
    @Test
    public void test_message_consumer_initialization() {
        MessageQueue messageQueue = new MessageQueue();
        MessageConsumer consumer = new MessageConsumer(messageQueue);
        assertNotNull(consumer);
    }

    // Submits producer and consumer tasks to ExecutorService
    @Test
    public void test_executor_service_submission() {
        MessageQueue messageQueue = new MessageQueue();
        MessageProducer producer = new MessageProducer(messageQueue, 10);
        MessageConsumer consumer = new MessageConsumer(messageQueue);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(producer);
        executor.submit(consumer);
        assertFalse(executor.isShutdown());
        executor.shutdown();
    }



    // MessageQueue initialization failure
    @Test(expected = IllegalArgumentException.class)
    public void test_message_queue_initialization_failure() {
        new MessageProducer(null, 10);
    }

    // MessageProducer initialization with invalid number of messages
    @Test(expected = IllegalArgumentException.class)
    public void test_message_producer_invalid_number_of_messages() {
        new MessageProducer(new MessageQueue(), -1);
    }

    // MessageConsumer initialization with null MessageQueue
    @Test(expected = IllegalArgumentException.class)
    public void test_message_consumer_null_message_queue() {
        new MessageConsumer(null);
    }
}