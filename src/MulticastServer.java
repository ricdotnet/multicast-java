import java.net.*;
import java.io.*;

public class MulticastServer {

  InetAddress group;
  MulticastSocket socket;
  DatagramPacket data;
  byte[] buf = new byte[1028];

  private void startServer() {
    try {
      group = InetAddress.getByName("225.1.1.1");
      socket = new MulticastSocket(9090);
      socket.joinGroup(group);

      while(true) {
        data = new DatagramPacket(buf, 0, buf.length);
        socket.receive(data);

        String print = new String(data.getData(), data.getOffset(), data.getLength());
        System.out.println(print);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {

    MulticastServer main = new MulticastServer();

    main.startServer();
  }

}
