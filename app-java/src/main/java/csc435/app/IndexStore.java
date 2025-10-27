package csc435.app;

import java.util.HashMap;
import java.util.Map;

public class IndexStore {
    private HashMap<String, HashMap<String, Integer>> index;
    private HashMap<String, HashMap<String, Integer>> mainIndex;

    public IndexStore() {
        index = new HashMap<>();
        mainIndex = new HashMap<>();
    }

    public void updateIndex(String documentPath, HashMap<String, Integer> wordFrequencyMap) {
        index.put(documentPath, wordFrequencyMap);
        aggregateIndex(documentPath, wordFrequencyMap);
    }

    private void aggregateIndex(String documentPath, HashMap<String, Integer> wordFrequencyMap) {
        for (String word : wordFrequencyMap.keySet()) {
            mainIndex.computeIfAbsent(word, k -> new HashMap<>()).put(documentPath, wordFrequencyMap.get(word));
        }
    }

    public HashMap<String, HashMap<String, Integer>> getMainIndex() {
        return mainIndex;
    }
    // public void printIndex() {
    //     for (Map.Entry<String, HashMap<String, Integer>> entry : mainIndex.entrySet()) {
    //         String word = entry.getKey();
    //         HashMap<String, Integer> documentFrequencyMap = entry.getValue();
    //         System.out.println("Word: " + word);
    //         for (Map.Entry<String, Integer> documentEntry : documentFrequencyMap.entrySet()) {
    //             String documentPath = documentEntry.getKey();
    //             int frequency = documentEntry.getValue();
    //             System.out.println("\tDocument: " + documentPath + ", Frequency: " + frequency);
    //         }
    //     }
    // }

    public void lookupIndex() {
        // TO-DO implement index lookup
    }
}


