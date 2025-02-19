package org.example.kernel;

public abstract class UserProcess {
    protected int pid;
    protected int remainingTime;
    public int requiredMemory;
    protected String finalResult = "N/A";
    protected MemoryManager.MemoryBlock allocatedMemory;
    private boolean completed = false;  // ✅ Track process completion

    public UserProcess(int pid, int requiredMemory, int remainingTime) {
        this.pid = pid;
        this.requiredMemory = requiredMemory;
        this.remainingTime = remainingTime;
        this.allocatedMemory = null;
    }

    public int getPid() {
        return pid;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public boolean isCompleted() {  // ✅ Check if process is done
        return completed;
    }

    protected void markCompleted() {  // ✅ Mark process as done
        this.completed = true;
        this.remainingTime = 0; // Ensure remaining time is 0
        System.out.println("✅ Process " + pid + " marked as completed.");
    }

    public abstract void executeStep();  // Execution logic to be implemented by subclasses

    public void executeStepDefault() {  // ✅ Default execution method (optional)
        if (completed) {  // ✅ Prevent execution if already completed
            System.out.println("🚨 Process " + pid + " already completed. Skipping...");
            return;
        }

        if (remainingTime > 0) {
            System.out.println("🔄 Executing process " + pid + ", Remaining Time: " + remainingTime);
            remainingTime--;

            if (remainingTime == 0) {
                markCompleted();
            }
        }
    }

    @Override
    public String toString() { // ✅ Helpful for debugging/logging
        return "[Process " + pid + "] Memory: " + requiredMemory + "MB, Remaining Time: " + remainingTime +
                ", Status: " + (completed ? "✅ Completed" : "⏳ Running");
    }
}
