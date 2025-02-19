package org.example.processes;

import org.example.kernel.UserProcess;
import org.example.utils.FileHelper;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;



public class FileWriterProcess extends UserProcess {
    private final FileHelper fileHelper;
    private final String fileName;
    private final AtomicBoolean isWaitingForInput = new AtomicBoolean(true); // Waiting flag

    public FileWriterProcess(int pid, int requiredMemory, int executionTime, String fileName) {
        super(pid, requiredMemory, executionTime);
        this.fileHelper = new FileHelper(fileName);
        this.fileName = fileName;
        startUserInputHandler();
    }

    private void startUserInputHandler() {
        try {
            // Path where IntelliJ compiles classes
            String classpath = "C:\\Users\\ASUS\\OneDrive\\Desktop\\Waseeq\\Coding\\Project\\OS\\MicroKernel\\target\\classes";

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
                remainingTime = 0; // Mark process as completed after input
                System.out.println("[PROCESS] ✅ Process " + pid + " completed after receiving input.");
                finalResult = "done";
                markCompleted();
            } else {
                System.out.println("[WAIT] ⏳ Process " + pid + " waiting for user input...");
            }
        }
    }
}