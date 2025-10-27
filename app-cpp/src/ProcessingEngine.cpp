#include "ProcessingEngine.hpp"

ProcessingEngine::ProcessingEngine(std::shared_ptr<IndexStore> store, int numWorkerThreads) :
        store(store), numWorkerThreads(numWorkerThreads) {
    // TO-DO implement constructor
}

void ProcessingEngine::indexFiles() {
    // TO-DO implement index files
    // TO-DO crawl dataset and find all folders
    // TO-DO create the worker threads
    // TO-DO each worker thread receives a subset of the folders and runs the following operations:
    // - For each folder crawl the folder and find all documents
    // - For each document extract and count the words, add them to a local hash table and update the index store
    // TO-DO the main thread needs to wait for all of the worker threads to finish executing and join them
}

void ProcessingEngine::searchFiles() {
    // TO-DO implement search files
}
