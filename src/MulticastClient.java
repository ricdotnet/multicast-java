import java.io.*;
import java.net.*;

public class MulticastClient {

  InetAddress group;
  MulticastSocket socket;
  DatagramPacket data;
  BufferedReader reader;
  byte[] buf = new byte[1028];
  byte[] bufIn = new byte[1028];

  private String username;

  private void startClient() {
    try {
      group = InetAddress.getByName("225.1.1.1");
      socket = new MulticastSocket(9090);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void sender() {
    try {
      reader = new BufferedReader(new InputStreamReader(System.in));

      while(true) {
        String msg = reader.readLine();

        if(msg.equals("CLOSE")) {
          buf = (username + ": Good bye!").getBytes();
          socket.send(new DatagramPacket(buf, buf.length, group, 9090));
          socket.leaveGroup(group);
          break;
        }

        buf = (username + ": " + msg).getBytes();
        socket.send(new DatagramPacket(buf, buf.length, group, 9090));
      }

      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void receiver() {
    try {
      socket.joinGroup(group);
      while (true) {
        data = new DatagramPacket(bufIn, 0, bufIn.length);
        socket.receive(data);
        String msgIn = new String(data.getData(), data.getOffset(), data.getLength());
        if(!msgIn.contains(username)) {
          System.out.println(new String(data.getData(), data.getOffset(), data.getLength()));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    MulticastClient main = new MulticastClient();
    main.username = args[0];

    main.startClient();

    new Thread(() -> {
      main.sender();
    }).start();
    new Thread(() -> {
      main.receiver();
    }).start();
  }

}
