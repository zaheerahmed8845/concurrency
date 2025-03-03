**Assumptions**

1. We are not using benchmarking tool(JMH), thus the test will not include.
    1. WarmUp
    2. Multi Instance JVM runs
2. The Throughput(Write/Read) is calculated within the RunController.java in code. Below read to write ratios
   are calculated.\
   R : W = 1 : 1\
   R : W = 5 : 1\
   R : W = 1 : 5

**Components**

1. Controller: This starts the Write and Read Processors and calculates the throughput of the application.
    1. runs : Number of times the processes runs. At the end of the run it captures the throughput for each individual
       run and calculates the average.
    2. algo : This accepts a numerical value and which algo to run, below is the mapping.\
       1 -> StatisticsWithoutStorageImpl.java\
       2 -> StatisticsWithStorageCOWAImpl.java\
       3 -> StatisticsWithStorageCLQImpl.java\
       4 -> StatisticsWithALLockImpl.java
2. Write Processor: It calls the Statistics.event() method.
    1. NoOfThreads=The number of write processor threads that are started.
    2. Count=The counts each threads call the Statistics.event() method by a single thread. It uses a random generator
       to generate the value.
    3. Interval=The delay between calling the Statistics.event() method by a single thread.
3. Read Processor: It calls the statistics read methods - min(), max(), mean(), variance().
    1. NoOfThreads=The number of read processor threads that are started.
    2. Count=The counts each threads call the statistics read methods by a single thread. For each call, it will call a
       single read method randomly.
    3. Interval=The delay between calling the statistics read methods by a single thread.

**Run Types**

1. No Storage/Historical Events :
    1. Refer the class : StatisticsWithoutStorageImpl.java
    2. This is a blocking(not lock free) - the write method and read(variance) method are implemented by a lock.
    3. In this we do not persist any data.
    4. All the read values are computed at the time of addition.
    5. The read values are consistent.
2. Storage/Historical Events :
    1. ConcurrentLinkedQueue :
        1. Refer the class: StatisticsWithStorageCLQImpl.java
        2. This is a lock free and non-blocking for all the read and write operations.
        3. In this we persist all the data.
        4. The read values are computed for each call.
        5. High compute read operations(variance) is calculated using Completable Future.
        6. The read values are not consistent.
    2. CopyOnWriteArrayList
        1. Refer the class: StatisticsWithStorageCOWAImpl.java
        2. This is a lock free and non-blocking for all the read and write operations.
        3. In this we persist all the data.
        4. The read values are computed for each call.
        5. High compute read operations(variance) is calculated using Parallel Stream.
        6. The read values are not consistent.
    3. Lock and Array List
        1. Refer the class : StatisticsWithALLockImpl.java
        2. This is a blocking(not lock free) - the write method and all read methods are implemented by a lock.
        3. In this we persist all the data.
        4. The read values are computed for each call.
        5. High compute read operations(variance) is calculated using Parallel Stream.
        6. The read values are consistent.

**Throughput Performance**

1. Read : Write = 1 : 1

   | RunType                             | Operation Type  | No of Threads  | Count   | Throughput(requests/sec) |
      |-------------------------------------|-----------------|----------------|---------|--------------------------|
   | No Storage                          | Write           | 10             | 5000    | 50k                      |
   |                                     | Read            | 10             | 5000    | 50k                      |
   | Storage - <br>ConcurrentLinkedQueue | Write           | 10             | 5000    | 50k                      |
   |                                     | Read            | 10             | 5000    | 22k                      |
   | Storage - <br>CopyOnWriteArrayList  | Write           | 10             | 5000    | 50k                      |
   |                                     | Read            | 10             | 5000    | 25k                      |
   | Storage - <br>Lock and Array List   | Write           | 10             | 5000    | 50k                      |
   |                                     | Read            | 10             | 5000    | 50k                      |

2. Read : Write = 1 : 5

   | RunType                             | Operation Type  | No of Threads | Count   | Throughput(requests/sec) |
      |-------------------------------------|-----------------|---------------|---------|--------------------------|
   | No Storage                          | Write           | 50            | 5000    | 250k                     |
   |                                     | Read            | 10            | 5000    | 50k                      |
   | Storage - <br>ConcurrentLinkedQueue | Write           | 50            | 5000    | 250k                     |
   |                                     | Read            | 10            | 5000    | 3.5k                     |
   | Storage - <br>CopyOnWriteArrayList  | Write           | 50            | 5000    | 16k                      |
   |                                     | Read            | 10            | 5000    | 16k                      |
   | Storage - <br>Lock and Array List   | Write           | 50            | 5000    | 208k                     |
   |                                     | Read            | 10            | 5000    | 41k                      |

3. Read : Write = 5 : 1

   | RunType                             | Operation Type  | No of Threads | Count    | Throughput(requests/sec) |
      |-------------------------------------|-----------------|---------------|----------|--------------------------|
   | No Storage                          | Write           | 10            | 5000     | 50k                      |
   |                                     | Read            | 50            | 5000     | 250k                     |
   | Storage - <br>ConcurrentLinkedQueue | Write           | 10            | 5000     | 6k                       |
   |                                     | Read            | 50            | 5000     | 26k                      | 
   | Storage - <br>CopyOnWriteArrayList  | Write           | 10            | 5000     | 9k                       |
   |                                     | Read            | 50            | 5000     | 3.7k                     |
   | Storage - <br>Lock and Array List   | Write           | 10            | 5000     | 30k                      |
   |                                     | Read            | 50            | 5000     | 152k                     |

**Trade-off**

| RunType                             | Write Consistency | Read Consistency |
|-------------------------------------|-------------------|------------------|
| No Storage                          | Yes               | Yes              |
| Storage - <br>ConcurrentLinkedQueue | Yes               | No               |
| Storage - <br>CopyOnWriteArrayList  | Yes               | No               |
| Storage - <br>Lock and Array List   | Yes               | Yes              |

**FAQ**

**1**: When using ConcurrentLinkedQueue for high compute read task like variance why was
CompletableFuture used instead of Parallel Stream?\
**A**: The performance of parallelstream is good with arraylist but poor with LinkedList,
Since ConcurrentLinkedQueue uses the underlying datastructure as LinkedList, hence the queue partition
and parallel execution needed to be written explicitly.

**2**: Why was only the variance() method had parallelism enabled and not the other read methods?\
**A**: I have tested with inserting 500M writes and then performing the read computations, the other read
operations where performing better sequentially compared to parallel tasks. Since variance was performing bad
hence it had computation paralley enabled.

**3**: The executor used in CompletableFuture uses "Runtime.getRuntime().availableProcessors()" threads,
why have you not provided fixed no of threads?\
**A**: Since we do not have IO operation and only compute, hence providing a number of threads greater or lesser
than the core available will not make it efficient.

**4**: Why you could not test with inserts more than 500M?\
**A**: My machines resources max could stretch till. For going beyond this my RAM would not suffice and would
require an external resource or file system for storage which would require additional effort and beyond the scope
of this problem statement.

**5**: In the class StatisticsWithStorageCLQImpl, the size and add to the queue can have a race condition?\
**A**: Yes, this can happen the size is used to decide if it is parallel or sequential processing and does
not impact any of the read computations.

**6**: Why is the Single Writer Principle not used in the above algorithms?\
**A**: It could not make the throughput performance any better. For the above tests, but we could use it where the
writes are less and reads are more(read-heavy application). All the min(), max(), mean() and variance() will be
calculated
as part of write method.

**Improvements in the Statstistics Interface**

1. The current methods do not have a limit on which the min(), max(), mean() and variance() are calculated.
   This could be improved by having a limit. For example: Find the min() value for the last 1000 elements.

