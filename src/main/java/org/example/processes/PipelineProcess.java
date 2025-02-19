package org.example.processes;

import org.example.kernel.UserProcess;
import org.example.utils.MessageQueue;

public class PipelineProcess extends UserProcess {
    public PipelineProcess(int pid, int requiredMemory, int burstTime) {
        super(pid, requiredMemory, burstTime);
    }

    @Override
    public void executeStep() {
        System.out.println("🚀 Process " + getPid() + " (Sender) is running.");

        // ✅ Send a message
        String message = "Hello from Process " + getPid();
        MessageQueue.sendMessage(message);
        System.out.println("📨 Process " + getPid() + " sent message: " + message);
        finalResult = "Message Send";

        markCompleted();
    }
}