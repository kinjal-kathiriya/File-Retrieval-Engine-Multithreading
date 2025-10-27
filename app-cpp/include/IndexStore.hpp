#ifndef INDEX_STORE_H
#define INDEX_STORE_H

class IndexStore {
    // TO-DO use a search structure to keep track of the index

    public:
        // constructor
        IndexStore();

        // default virtual destructor
        virtual ~IndexStore() = default;

        // TO-DO re-declare index update and index lookup methods
        void updateIndex();
        void lookupIndex();
};

#endif