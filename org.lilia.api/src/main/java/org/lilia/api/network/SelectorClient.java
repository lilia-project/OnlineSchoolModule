package org.lilia.api.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SelectorClient {
    public static void main(String[] args) {
    }

    public void start() throws IOException {

        String hostName = "localhost";
        int portNumber = 1234;

        InetSocketAddress inetSocketAddress = new InetSocketAddress(hostName, portNumber);
        SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String inputInfo;

        while ((inputInfo = stdIn.readLine()) != null) {
            byteBuffer.clear();
            byteBuffer.put("info".getBytes());
            byteBuffer.flip();

            socketChannel.write(byteBuffer);

            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array(), 0, byteBuffer.position()));

        }
        socketChannel.close();
    }
}
