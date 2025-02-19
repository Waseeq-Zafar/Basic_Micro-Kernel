package org.example.processes;

import org.example.kernel.UserProcess;


public class SumCalculator extends UserProcess {
    private int sum = 0;
    private int currentNumber = 1;
    private final int targetNumber;

    public SumCalculator(int pid, int requiredMemory, int N) {
        super(pid, requiredMemory, N);
        this.targetNumber = N;
    }

    @Override
    public void executeStep() {
        if (remainingTime > 0 && currentNumber <= targetNumber) {
            sum += currentNumber;
            System.out.println("[SUM] 🧮 Process " + pid + " summing: " + sum);
            currentNumber++;
            remainingTime--;
        }

        if (remainingTime <= 0) {
            finalResult = "Final sum: " + sum; // Store the final sum
            System.out.println("[SUM] ✅ Process " + pid + " completed sum: " + sum);
        }
    }
}
