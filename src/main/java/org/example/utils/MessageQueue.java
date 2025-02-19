package org.example.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static void sendMessage(String message) {
        try {
            queue.put(message); // Thread-safe blocking queue
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String receiveMessage() {
        try {
            return queue.take(); // Blocks until message is available
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error receiving message";
        }
    }
}