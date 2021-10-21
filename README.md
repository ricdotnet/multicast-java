
# Java Multicasting Example

This is a Java multicasting example with server and client.



## Run Locally

Clone the project

```bash
  git clone https://github.com/ricdotnet/multicast-java
```

Go to the project directory

```bash
  cd multicast-java
```

Compile the client and the server, order does not matter.

```bash
  javac src/MulticastClient.java
  javac src/MulticastServer.java
```

Run the client and the server, order does not matter.

```bash
  # these two need to be ran in different command line windows
  java src/MulticastClient username
  java src/MulticastServer
```
### More

You can run as many clients as you want. Each one will be subscribed to the multicast
address and will always be listening to inbound messages.

I used a thread for sending and receiving because `socket.reveive()` stops the
the `code loop` and waits for an inbound message before proceeding. Using threads allows
for asynchronous programming.