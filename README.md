# microservices-kafka
1. [About](#About)
2. [Download and configuration](#Download-and-configuration)
3. [Start Zookeper and Kafka](#Start-Zookeper-and-Kafka)

<a name="About"/>

## About
This repo was created to practice and learn about [Apache Kafka](https://kafka.apache.org/) and [microservices arctecture](https://en.wikipedia.org/wiki/Microservices).
To maintain it simple, I'm only using [Apache Maven](https://maven.apache.org/) and running the project on [Java 11](https://www.oracle.com/java/technologies/javase-downloads.html).

<a name="Download-and-configuration"/>

## Download and configuration
Apart from downloading the Github repository, this project needs [Apache Kafka binaries](https://kafka.apache.org/downloads) after the download, unpack and open the Kafka folder and [start the Zookeeper and Kafka](#Start-Zookeper-and-Kafka).
Open the command line (I'm using [git bash](https://git-scm.com/downloads)).


<a name="Start-Zookeper-and-Kafka"/>

## Start Zookeper and Kafka
The following list of commands will help to start Zookeeper and Kafka.

### to start zookeper with default properties file
``` sh
bin/zookeper-server-start.sh config/zookeper.properties
```
#### to start Kafka with default properties file (it needs to have Zookeeper already running)
``` sh
bin/kafka-server-start.sh config/server.properties
```
### to see all topics options use
``` sh
bin/kafka-topics.sh
```
### to create a new topic
``` sh
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 replication-factor 1 --partirions 1 --topic NOME_TOPICO
```
### to start a producer to send messages
``` sh
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
```
### to show all messages from a topic and keep listening
``` sh
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NOME_TOPICO --from-beginning
```
### to describe every topic created
``` sh
bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```
### to change a topic partition size
``` sh
bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic [TOPIC_NAME] --partitions [NUMBER_OF_PARTITIONS]
```
### to show consumers status
``` sh
bin/kafka-consumer-groups.sh -all-groups --boostrap-server localhost:9092 --describe
```
*It is important to notice that the [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) version needed to run the commands above is 1.8.*
