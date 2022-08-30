# job4j_pooh

Pooh JMS Project

Description:
 -This project is analogous to the asynchronous RabbitMQ queue.
 -The application starts Socket and waits for clients.
 -There are two types of clients: senders (publisher) and recipients (subscriber).
 -HTTP is used as the protocol. Messages in JSON format.
 -There are two modes: queue, topic.
 -There is no synchronization in the code. Everything is done on Executors and concurrent collections.

Technologies:
 -Java Concurrency (classes from the java.util.concurrent package)
 -Sockets
 -Java IO
 -GSON Library