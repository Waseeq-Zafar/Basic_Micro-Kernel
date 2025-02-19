package org.example.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileHelper {
    private String fileName;

    public FileHelper(String fileName) {
        this.fileName = fileName;
    }

    // Open file (Check if exists, else create)
    public void openFile() {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("[FILE] 📄 Created new file: " + fileName);
            } else {
                System.out.println("[FILE] 📂 File already exists: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("[ERROR] ❌ Failed to open file.");
            e.printStackTrace();
        }
    }

    // Write to file (Append mode)
    public void writeToFile(String content) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(content + "\n");
            System.out.println("[WRITE] 📝 Data written to file: " + fileName);
        } catch (IOException e) {
            System.out.println("[ERROR] ❌ Failed to write to file.");
            e.printStackTrace();
        }
    }

    // Read file content
    public String readFile() {
        try {
            System.out.println("[READ] 📖 Reading file: " + fileName);
            return Files.lines(Paths.get(fileName)).collect(Collectors.joining(","));
        } catch (IOException e) {
            System.out.println("[ERROR] ❌ Failed to read file.");
            e.printStackTrace();
            return "[ERROR] File Read Failed!";
        }
    }

    // Delete file
    public void deleteFile() {
        File file = new File(fileName);
        if (file.exists() && file.delete()) {
            System.out.println("[DELETE] 🗑️ File deleted: " + fileName);
        } else {
            System.out.println("[ERROR] ❌ File could not be deleted.");
        }
    }
}
