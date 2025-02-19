package org.example.kernel;


import java.util.LinkedList;
import java.util.Queue;

public class CPUScheduler {

    private final MemoryManager memoryManager;
    private final int timeQuantum;
    private final Queue<UserProcess> userProcessQueue;
    private final Queue<UserProcess> completedUserProcesses; // Track completed processes

    public CPUScheduler(int timeQuantum, MemoryManager memoryManager) {
        this.timeQuantum = timeQuantum;
        this.memoryManager = memoryManager;
        this.userProcessQueue = new LinkedList<>();
        this.completedUserProcesses = new LinkedList<>();
    }

    public void addProcess(UserProcess userProcess) {
        userProcessQueue.add(userProcess);
    }

    public boolean isQueueEmpty() {
        return userProcessQueue.isEmpty();
    }

    public Queue<UserProcess> getCompletedProcesses() {
        return new LinkedList<>(completedUserProcesses); // Return a copy to prevent modification issues
    }

    public void execute() {
        Logger.info("🔄 Starting Round Robin Scheduling...");

        Queue<UserProcess> remainingUserProcesses = new LinkedList<>();

        while (!userProcessQueue.isEmpty()) {
            UserProcess currentUserProcess = userProcessQueue.poll();

            int executionTime = Math.min(timeQuantum, currentUserProcess.remainingTime);
            Logger.info("▶ Process " + currentUserProcess.pid + " executing for " + executionTime + "ms");

            // 🔴 CALL executeStep() in a loop for each time unit
            for (int i = 0; i < executionTime; i++) {
                currentUserProcess.executeStep();
            }

            if (currentUserProcess.remainingTime > 0) {
                remainingUserProcesses.add(currentUserProcess); // Re-add if not finished
            } else {
                Logger.info("✅ Process " + currentUserProcess.pid + " completed execution.");
                memoryManager.freeMemory(currentUserProcess);
                completedUserProcesses.add(currentUserProcess);
            }
        }
        userProcessQueue.addAll(remainingUserProcesses);
    }

}
