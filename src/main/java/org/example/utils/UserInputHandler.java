package org.example.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class UserInputHandler {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("[ERROR] ❌ No file name provided.");
            return;
        }

        String fileName = args[0];
        Scanner scanner = new Scanner(System.in);

        System.out.print("[INPUT] 📝 Enter text to write in file: ");
        String userInput = scanner.nextLine();

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(userInput + "\n");
            System.out.println("[WRITE] 📝 Data written to file: " + fileName);
        } catch (IOException e) {
            System.err.println("[ERROR] ❌ Failed to write to file.");
            e.printStackTrace();
        }
    }
}
