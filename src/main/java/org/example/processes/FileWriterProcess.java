package org.example.processes;

import org.example.kernel.DiskScheduler;
import org.example.kernel.UserProcess;
import org.example.utils.FileHelper;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;



public class FileWriterProcess extends UserProcess {
    private final FileHelper fileHelper;
    private final String fileName;
    private final AtomicBoolean isWaitingForInput = new AtomicBoolean(true); // Waiting flag

    private final DiskScheduler diskScheduler;


    public FileWriterProcess(int pid, int requiredMemory, int executionTime, String fileName, DiskScheduler diskScheduler) {
        super(pid, requiredMemory, executionTime);
        this.fileHelper = new FileHelper(fileName);
        this.fileName = fileName;
        this.diskScheduler = diskScheduler;
        startUserInputHandler();
    }

    private void startUserInputHandler() {
        try {
            // Path where IntelliJ compiles classes
            String classpath = " target\\classes (absolute path)";

            // Open CMD for input
            String command;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                command = "cmd /c start /WAIT java -cp \"" + classpath + "\" org.example.utils.UserInputHandler " + fileName;
            } else {
                command = "x-terminal-emulator -e java -cp \"" + classpath + "\" org.example.utils.UserInputHandler " + fileName;
            }

            // Start the input process in a new terminal (blocks execution)
            Process inputProcess = Runtime.getRuntime().exec(command);

            // **Run in a separate thread to avoid blocking**
            new Thread(() -> {
                try {
                    inputProcess.waitFor(); // Wait for user to enter input
                    isWaitingForInput.set(false); // Allow process 2 to continue
                    System.out.println("[FILE] ✅ User input received. Process " + pid + " will continue.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            System.err.println("[ERROR] ❌ Could not open new terminal for input.");
            e.printStackTrace();
        }
    }

    @Override
    public void executeStep() {
        if (remainingTime > 0) {
            if (!isWaitingForInput.get()) {
                remainingTime = 0; // Mark process as done after input

                // Simulate multiple track writes (e.g., header + data)
                int track1 = new java.util.Random().nextInt(100); // Track 0–99
                int track2 = new java.util.Random().nextInt(100); // Another write

                System.out.println("[DISK] 📝 PID " + pid + " writing to Track " + track1);
                diskScheduler.addRequest(track1, pid);

                System.out.println("[DISK] 📝 PID " + pid + " writing to Track " + track2);
                diskScheduler.addRequest(track2, pid);

                // Process requests together (so movement is shown for both)
                diskScheduler.processRequests();

                finalResult = "done";
                System.out.println("[PROCESS] ✅ Process " + pid + " completed after writing user input.");
                markCompleted();
            } else {
                System.out.println("[WAIT] ⏳ Process " + pid + " is waiting for user input...");
            }
        }
    }
}