import java.io.*;
import java.net.*;

public class MulticastClient {

  InetAddress group;
  MulticastSocket socket;
  DatagramPacket data;
  BufferedReader reader;
  byte[] buf = new byte[1028];
  byte[] bufIn = new byte[1028];

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

      System.out.print(">> ");
      while(true) {
        String msg = reader.readLine();

        if(msg.equals("CLOSE")) {
          msg = "Good bye!";
          socket.leaveGroup(group);
          break;
        }

        buf = msg.getBytes();
        socket.send(new DatagramPacket(buf, buf.length, group, 9090));
        System.out.print(">> ");
      }
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
        System.out.println(new String(data.getData(), data.getOffset(), data.getLength()));
        System.out.print(">> ");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    MulticastClient main = new MulticastClient();

    main.startClient();

    new Thread(() -> {
      main.sender();
    }).start();
    new Thread(() -> {
      main.receiver();
    }).start();
  }

}
