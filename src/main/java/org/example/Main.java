package org.example;

import org.example.kernel.CPUScheduler;
import org.example.kernel.MemoryManager;
import org.example.kernel.UserProcess;
import org.example.processes.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

        List<UserProcess> processes = new ArrayList<>();

        // Create memory manager with 100 units of memory
        MemoryManager memoryManager = new MemoryManager(100); // Example total memory
        CPUScheduler cpuScheduler = new CPUScheduler(3, memoryManager); // Time quantum = 3ms

        // Create processes
        UserProcess p1 = new SumCalculator(1, 10, 7);
        UserProcess p2 = new FileWriterProcess(2, 40, 5, "output.txt");
        UserProcess p3 = new FileReaderProcess(3, 20, 5, "output.txt", p2); // Dependent on p2 (FileWriterProcess)
        UserProcess p4 = new PipelineProcess(4, 10, 2);  // Sender
        UserProcess p5 = new MessageListenerProcess(5, 10, 2); // Listener


        processes.add(p1);
        processes.add(p2);
        processes.add(p3);
        processes.add(p4);
        processes.add(p5);

        // Queue of pending processes
        Queue<UserProcess> pendingUserProcesses = new LinkedList<>(processes);

        // Start processing
        while (!pendingUserProcesses.isEmpty() || !cpuScheduler.isQueueEmpty()) {
            Queue<UserProcess> remainingUserProcesses = new LinkedList<>();

            // Process each user process
            while (!pendingUserProcesses.isEmpty()) {
                UserProcess userProcess = pendingUserProcesses.poll();

                // Check if memory can be allocated
                if (memoryManager.allocateMemory(userProcess, userProcess.requiredMemory)) {
                    cpuScheduler.addProcess(userProcess);
                } else {
                    remainingUserProcesses.add(userProcess);
                }
            }

            // Update pending processes for next iteration
            pendingUserProcesses = remainingUserProcesses;

            // Run Round Robin once
            cpuScheduler.execute();

            // Free memory for completed processes
            Queue<UserProcess> completedUserProcesses = cpuScheduler.getCompletedProcesses();
            for (UserProcess userProcess : completedUserProcesses) {
                memoryManager.freeMemory(userProcess);
            }
        }

        // Show results after completion of all processes
        System.out.println("🎯 All processes completed.");
        memoryManager.printMemoryState(); // Show final memory state

        System.out.println("\n=========== FINAL RESULTS ===========");
        for (UserProcess process : processes) {
            System.out.println("[FINAL] 🏁 Process " + process.getPid() + " result: " + process.getFinalResult());
        }
        System.out.println("=====================================");




    }
}