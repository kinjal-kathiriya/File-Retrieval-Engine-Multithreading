package csc435.app;



public class FileRetrievalEngine {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -cp target/app-java-1.0-SNAPSHOT.jar csc435.app.FileRetrievalEngine <number of worker threads>");
            return;
        }

        int numWorkerThreads = Integer.parseInt(args[0]);

        if (numWorkerThreads <= 0) {
            System.out.println("Number of worker threads must be a positive integer.");
            return;
        }

        // Create an IndexStore object
        IndexStore store = new IndexStore();

        // Create a ProcessingEngine object with the specified number of worker threads
        ProcessingEngine engine = new ProcessingEngine(store, numWorkerThreads);

        // Create an AppInterface object and pass the ProcessingEngine object to it
        AppInterface appInterface = new AppInterface(engine);

        // Call the readCommands method of the AppInterface to start the application
        appInterface.readCommands();
    }
}





