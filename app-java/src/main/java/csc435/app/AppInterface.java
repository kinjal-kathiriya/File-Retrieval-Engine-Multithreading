package csc435.app;

import java.lang.System;
import java.util.Scanner;
import java.nio.file.*;
import java.io.IOException;

public class AppInterface {
    private ProcessingEngine engine;

    public AppInterface(ProcessingEngine engine) {
        this.engine = engine;
        // TO-DO implement constructor
    }

    public void readCommands() {
        // TO-DO implement the read commands method
        Scanner sc = new Scanner(System.in);
        String command;
        
        while (true) {
            System.out.print("> ");
            command = sc.nextLine();

            // Check for various commands
            if (command.equals("quit")) {
                // Quit the program
                break;
            } else if (command.startsWith("read")) {
                // Read files from the specified dataset path
                String[] tokens = command.split("\\s+");
                if (tokens.length != 2) {
                    System.out.println("Usage: read <dataset-path>");
                    continue;
                }
                String datasetPath = tokens[1];
                retrieveFiles(datasetPath);
            } else if (command.startsWith("index ")) {
                // Index files from a directory
                String directoryPath = command.substring(6); // Extract directory path
                engine.indexFiles(directoryPath);
            } else if (command.startsWith("search ")) {
                // Search for files based on query
                String query = command.substring(7); // Extract query
                engine.searchFiles(query);
            } else {
                // Unrecognized command
                System.out.println("Unrecognized command!");
            }
        }

        sc.close();
}

private void retrieveFiles(String datasetPath) {
    try {
        int fileCount = countFilesInDirectory(Paths.get(datasetPath));
        System.out.println("Total files read: " + fileCount);
    } catch (IOException e) {
        System.out.println("Error reading files: " + e.getMessage());
    }
}

private int countFilesInDirectory(Path directory) throws IOException {
    int fileCount = 0;
    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
        for (Path filePath : directoryStream) {
            if (Files.isDirectory(filePath)) {
                // Recursively count files in subdirectories
                fileCount += countFilesInDirectory(filePath);
            } else if (Files.isRegularFile(filePath)) {
                // Increment file count for regular files
                fileCount++;
            }
        }
    }
    return fileCount;
}

}
