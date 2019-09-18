# Flink HTTP Connector

`flink-connector-http` is a [Flink Streaming Connector](https://ci.apache.org/projects/flink/flink-docs-stable/dev/connectors/) for invoking HTTPs APIs with data from any source.



## Build & Run

### Requirements

To build `flink-connector-http` you need to have [maven](https://maven.apache.org/) installed.



### Steps

To build `flink-connector-http` you must to run the next command:

```
mvn clean install
```



This command will install all the components in your `.m2` directory. To use you only must to add the next dependency in your `pom.xml` file:

```
<dependency>
  <groupId>net.galgus</groupId>
  <artifactId>flink-connector-http</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```