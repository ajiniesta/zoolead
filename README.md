# ZooLeader

Test project combining with JavaFX to show the leader election algorithm provided by Apache Curator.

# Execute this test

First you need to compile, then execute the server side(optional) and finally executes as many clients as you wish for tests.

## Compile the code

This project is a maven project, that will generate an fat jar with all the dependencies embedded to ease the test.

To generate the binary files, just build the project as a regular Maven project.

```
$ mvn clean install
```

## Server side
You can do the test with your own server, or start a single zookeeper test server.

To start the server:

```
$ java -cp target/zooleader-1.0-SNAPSHOT-jar-with-dependencies.jar com.iniesta.zooleader.StartTestZKServer 2181
```

In this case the server will start in the port *2181*.

## Client side
The client is a small JavaFX Application that will start the different clients, and will show in green the current leader.

Please experiment, adding and removing clients, and creating clients for differents znode paths for leader election.

To start the client:


```
$ java -cp target/zooleader-1.0-SNAPSHOT-jar-with-dependencies.jar com.iniesta.zooleader.fx.Client --connect=localhost:2181 --leaderpath=/zoolead
```

Where *localhost:2181* is connection string to the zookeeper server and */zoolead* is the znode used by the leader election algorithm to make the synchronizations between all the connected clients.

Have a great visit to the zoo! :squirrel: :dromedary_camel: :leopard: :crocodile: :dolphin: :koala: :bear: :elephant: :monkey: :smile: :octocat:
