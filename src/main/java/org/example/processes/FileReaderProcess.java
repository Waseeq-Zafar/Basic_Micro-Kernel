package org.example.processes;

import org.example.kernel.UserProcess;


import org.example.utils.FileHelper;

public class FileReaderProcess extends UserProcess {
    private final FileHelper fileHelper;
    private final UserProcess dependentProcess;
    private boolean hasStartedReading = false;

    public FileReaderProcess(int pid, int requiredMemory, int burstTime, String fileName, UserProcess dependentProcess) {
        super(pid, requiredMemory, burstTime);
        this.fileHelper = new FileHelper(fileName);
        this.dependentProcess = dependentProcess;
    }

    @Override
    public void executeStep() {
        // Ensure Process 2 (FileWriterProcess) is completed before reading
        if (!dependentProcess.isCompleted()) {
            System.out.println("⏳ Process " + getPid() + " is waiting for Process " + dependentProcess.getPid() + " to finish.");
            return;
        }

        // Start reading the file in steps
        if (!hasStartedReading) {
            System.out.println("📖 Process " + getPid() + " started reading file.");
            hasStartedReading = true;
        }

        // Read and print file content
        finalResult = fileHelper.readFile();
        markCompleted(); // Process finishes after reading
        System.out.println("✅ Process " + getPid() + " finished reading file.");
    }
}
