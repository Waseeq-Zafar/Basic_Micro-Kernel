package org.example.processes;

import org.example.kernel.UserProcess;
import org.example.utils.MessageQueue;

public class MessageListenerProcess extends UserProcess {
    public MessageListenerProcess(int pid, int requiredMemory, int burstTime) {
        super(pid, requiredMemory, burstTime);
    }

    @Override
    public void executeStep() {
        System.out.println("👂 Process " + getPid() + " (Listener) is waiting for a message...");

        // ✅ Receive message and print
        finalResult = MessageQueue.receiveMessage();
        System.out.println("📥 Process " + getPid() + " received message: " + finalResult);

        markCompleted();
    }
}