package csc435.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessingEngine {
    private IndexStore store;
    private long totalBytesProcessed; // Variable to accumulate total bytes processed
    private int numWorkerThreads; // Number of worker threads

    public ProcessingEngine(IndexStore store, int numWorkerThreads) {
        this.store = store;
        this.numWorkerThreads = numWorkerThreads;
        totalBytesProcessed = 0; // Initialize total bytes processed to 0
    }

    public void indexFiles(String directoryPath) {
        // Get the list of subdirectories
        List<Path> subdirectories = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Path.of(directoryPath))) {
            for (Path filePath : directoryStream) {
                if (Files.isDirectory(filePath)) {
                    subdirectories.add(filePath);
                }
            }
        } catch (IOException e) {
            System.out.println("Error accessing directory: " + e.getMessage());
            return;
        }

        // Divide folders among worker threads
        int numSubdirectories = subdirectories.size();
        int subdirectoriesPerThread = numSubdirectories / numWorkerThreads;
        int remainingSubdirectories = numSubdirectories % numWorkerThreads;

        int startIndex = 0;
        for (int i = 0; i < numWorkerThreads; i++) {
            int endIndex = startIndex + subdirectoriesPerThread + (i < remainingSubdirectories ? 1 : 0);
            List<Path> foldersForThread = subdirectories.subList(startIndex, endIndex);
            startIndex = endIndex;

            // Print assigned folders for the current thread
            System.out.println("Worker Thread " + (i + 1) + " assigned " + foldersForThread.size() + " folders:");
            for (Path folder : foldersForThread) {
                System.out.println(folder.toString());
            }

            // Process folders for each thread
            processFolders(foldersForThread);
        }
System.out.println("Completed indexing using " + numWorkerThreads + " worker threads");
        System.out.println("Completed indexing " + totalBytesProcessed + " bytes of data");
        System.out.println("Completed indexing in " + calculateElapsedTime() + " seconds");
    }

    private void processFolders(List<Path> foldersForThread) {
        for (Path folder : foldersForThread) {
            indexFilesRecursive(folder);
        }
    }

    private void indexFilesRecursive(Path directoryPath) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path filePath : directoryStream) {
                if (Files.isDirectory(filePath)) {
                    // Recursively index files in subdirectories
                    indexFilesRecursive(filePath);
                } else if (Files.isRegularFile(filePath)) {
                    // Index regular files
                    indexFile(filePath);
                }
            }
        } catch (IOException e) {
            System.out.println("Error indexing files: " + e.getMessage());
        }
    }

    private void indexFile(Path filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            HashMap<String, Integer> wordFrequencyMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\W+"); // Split by non-alphanumeric characters

                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                    }
                }
            }

            // Update total bytes processed
            synchronized (this) {
                totalBytesProcessed += Files.size(filePath);
            }

            store.updateIndex(filePath.toString(), wordFrequencyMap);

        } catch (IOException e) {
            System.out.println("Error indexing file " + filePath + ": " + e.getMessage());
        }
    }

    private double calculateElapsedTime() {
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1e9; // Convert to seconds
    }

    public void searchFiles(String query) {
        // Split the query by " AND " to handle multiple words separated by "AND"
        String[] keywords = query.split(" AND ");
    
        // Initialize combinedDocumentFrequencyMap with the document frequency map of the first keyword
        HashMap<String, Integer> combinedDocumentFrequencyMap = new HashMap<>();
        if (store.getMainIndex().containsKey(keywords[0])) {
            combinedDocumentFrequencyMap.putAll(store.getMainIndex().get(keywords[0]));
        }
    
        // Iterate through the rest of the keywords
        for (int i = 1; i < keywords.length; i++) {
            String keyword = keywords[i];
            if (store.getMainIndex().containsKey(keyword)) {
                HashMap<String, Integer> documentFrequencyMap = store.getMainIndex().get(keyword);
                // Retain only documents that contain the current keyword
                combinedDocumentFrequencyMap.keySet().retainAll(documentFrequencyMap.keySet());
            } else {
                // If any keyword is not found in the index, there can't be any documents satisfying the query
                combinedDocumentFrequencyMap.clear();
                break;
            }
        }
    
        // Perform intersection and sum frequencies for documents containing all keywords
        HashMap<String, Integer> searchResults = new HashMap<>();
        for (String document : combinedDocumentFrequencyMap.keySet()) {
            int totalFrequency = 0;
            for (String keyword : keywords) {
                totalFrequency += store.getMainIndex().get(keyword).getOrDefault(document, 0);
            }
            searchResults.put(document, totalFrequency);
        }
    
        // Print top results if there are any matching documents
        if (!searchResults.isEmpty()) {
            printTopResults(query, searchResults);
        } else {
            System.out.println("No documents found containing all the keywords in the query.");
        }
    }
private void printTopResults(String query, HashMap<String, Integer> documentFrequencyMap) {
            // Sort the documents by frequency in descending order
            List<Map.Entry<String, Integer>> sortedDocuments = new ArrayList<>(documentFrequencyMap.entrySet());
            sortedDocuments.sort((a, b) -> b.getValue().compareTo(a.getValue()));
    
            // Print the top 10 results
            System.out.println("Search results for \"" + query + "\" (top 10):");
            for (int i = 0; i < Math.min(sortedDocuments.size(), 10); i++) {
                Map.Entry<String, Integer> entry = sortedDocuments.get(i);
                System.out.println("* " + entry.getKey() + " " + entry.getValue());
            }
        }
}
