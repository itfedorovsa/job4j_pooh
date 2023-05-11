# job4j_pooh

Pooh JMS Project

# About:
<ul>
 <li>This project is analogous to the asynchronous RabbitMQ queue</li>
 <li>The application starts Socket and waits for clients</li>
 <li>There are two types of clients: senders (publisher) and recipients (subscriber)</li>
 <li>HTTP is used as the protocol. Messages in JSON format</li>
 <li>There are two modes: queue, topic</li>
 <li>There is no synchronization in the code. Everything is done on Executors and concurrent collections</li>
</ul>


# Used technologies

Implemented with:
<ul>
 <li>JDK 17</li>
 <li>Maven 3.8.5</li>
 <li>Java Concurrency (classes from the java.util.concurrent package)</li>
 <li>Sockets</li>
 <li>Java IO</li>
 <li>GSON 2.9.0</li>
 <li>JUnit 5</li>
</ul>

# Environment requirements

<ul>
 <li>Create .jar file via maven command "mvn package"</li>
 <li>Go to the Target folder and check the presence of "job4j_pooh-1.0-SNAPSHOT.jar" file</li>
 <li>Open the command line, go to the Target folder</li>
 <li>Run this file through "java -jar job4j_pooh-1.0-SNAPSHOT.jar" command</li>
</ul>

Contact me: itfedorovsa@gmail.com

