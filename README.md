# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.3/maven-plugin/reference/html/#build-image)
* [Spring Batch](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#howto.batch)

### Guides
The following guides illustrate how to use some features concretely:

* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)

### Business Data

For this kata, you can find some made-up data in `src/main/resources/sample-data.csv`. The file contains 100_000 couple of
[firstname, lastname], one on each row.

To initialize the database you can find in `src/main/resources/schema-all.sql` script that can be used to create people table.
> **_NOTE:_** Spring Boot runs schema-@@platform@@.sql automatically during startup. -all is the default for all platforms.

# TODO

## Create a business class that represent a people

## Create an Intermediate Processor
A common paradigm in batch processing is to ingest data, transform it, and then pipe it out somewhere else.
Here, you need to write a simple transformer that converts the names to uppercase. Implements the `ItemProcessor` interface to do it.
> **_NOTE:_** The input and output types need not be the same. In fact, after one source of data is read, sometimes the application’s data flow needs a different data type.


## Create a reader
The reader must be capable to read data from `src/main/resources/sample-data.csv`. https://docs.spring.io/spring-batch/reference/readersAndWriters.html

## Create a writer
The writer must be able to write data to embedded Hyper sql database. https://docs.spring.io/spring-batch/reference/readersAndWriters.html

## Create a batch step

Create a batch step that is able to coordinate the reader, the writer and the processor. https://docs.spring.io/spring-batch/reference/step.html

## Create a listener

Create a listener to get notified when a job ends. https://docs.spring.io/spring-batch/reference/spring-batch-integration/sub-elements.html#providing-feedback-with-informational-messages

## Create a batch job

Create a batch job named `importUserJob` that starts the created steps and notify when ends. https://docs.spring.io/spring-batch/reference/job.html

# Source
All the info and the content is inspired by https://spring.io/guides/gs/batch-processing