# File-Retrieval-Engine-Multithreading

### Requirements

If you are implementing your solution in C++ you will need to have GCC 12.x and CMake 3.22.x installed on your system.
On Ubuntu 22.04 LTS you can install GCC and set it as default compiler using the following commands:

```
sudo apt install g++-12 gcc-12 cmake
sudo update-alternatives --remove-all gcc
sudo update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-11 110
sudo update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-12 120
sudo update-alternatives --remove-all g++
sudo update-alternatives --install /usr/bin/g++ g++ /usr/bin/g++-11 110
sudo update-alternatives --install /usr/bin/g++ g++ /usr/bin/g++-12 120
```

If you are implementing your solution in Java you will need to have Java 17.x and Maven 3.6.x installed on your systems.
On Ubuntu 22.04 LTS you can install Java and Maven using the following commands:

```
sudo apt install openjdk-17-jdk maven

```

### Setup

There are 5 datasets (Dataset1, Dataset2, Dataset3, Dataset4, Dataset5) that you need to use to evaluate the indexing performance of your solution.
Before you can evaluate your solution you need to download the datasets. You can download the datasets from the following link:

https://depauledu-my.sharepoint.com/:f:/g/personal/aorhean_depaul_edu/EgmxmSiWjpVMi8r6QHovyYIB-XWjqOmQwuINCd9N_Ppnug?e=TLBF4V

After you finished downloading the datasets copy them to the dataset directory (create the directory if it does not exist).
Here is an example on how you can copy Dataset1 to the remote machine and how to unzip the dataset:

```
remote-computer$ mkdir datasets
local-computer$ scp Dataset1.zip cc@<remote-ip>:<path-to-repo>/datasets/.
remote-computer$ cd <path-to-repo>/datasets
remote-computer$ unzip Dataset1.zip
```

### Java solution
#### How to build/compile

To build the Java solution use the following commands:
```
cd app-java
mvn compile
mvn package
```

#### How to run application

To run the Java solution (after you build the project) use the following command:
```
java -cp target/app-java-1.0-SNAPSHOT.jar csc435.app.FileRetrievalEngine <number of worker threads>
```

#### Example

```
java -cp target/app-java-1.0-SNAPSHOT.jar csc435.app.FileRetrievalEngine 4
> index /home/kinjal/datasets/Dataset1
Completed indexing using  worker threads
Completed indexing 192889895 bytes of data
Completed indexing in 3.64E-7 seconds
> search Worms
Search completed in 3.2 seconds
Search results (top 10):
* Dataset1/folder6/document200.txt 10
* Dataset1/folder14/document417.txt 3
* Dataset1/folder6/document424.txt 3
* Dataset1/folder11/document79.txt 1
* Dataset1/folder12/document316.txt 1
* Dataset1/folder13/document272.txt 1
* Dataset1/folder13/document38.txt 1
* Dataset1/folder15/document351.txt 1
* Dataset1/folder1/document260.txt 1
* Dataset1/folder4/document101.txt 1
> search distortion AND adaptation
Search completed in 4.52 seconds
Search results (top 10):
* Dataset1/folder6/document200.txt 46
* Dataset1/folder13/document38.txt 4
* Dataset1/folder6/document408.txt 3
* Dataset1/folder7/document298.txt 2
* Dataset1/folder10/document107.txt 2
* Dataset1/folder10/document27.txt 2
* Dataset1/folder14/document145.txt 2
* Dataset1/folder15/document351.txt 2
* Dataset1/folder9/document77.txt 2
* Dataset1/folder5/document216.txt 2
> quit
```
