package org.lilia.api.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;

public class SelectorServer {

    private static final int SERVER_PORT = 1234;
    private static final String BLACK_LIST_IP = "src/main/resources/blackListIp.properties";
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    private Selector selector;

    public static void main(String[] args) throws IOException {
        new SelectorServer().start();
    }

    public void start() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(SERVER_PORT));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started and listening at port = " + SERVER_PORT);

        while (true) {

            selector.select();

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext()) {
                SelectionKey currentKey = keyIterator.next();
                keyIterator.remove();

                if (!currentKey.isValid()) {
                    continue;
                }
                if (currentKey.isAcceptable()) {
                    acceptConnection(currentKey);
                } else if (currentKey.isReadable()) {
                    readMessage(currentKey);
                } else if (currentKey.isWritable()) {
                    write(currentKey);
                }
            }
        }
    }

    public void acceptConnection(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

        SocketChannel clientChannel = serverSocketChannel.accept();

        if (checkIdClient(clientChannel)) {
            clientChannel.shutdownInput();
            System.out.println("clientChannel is connected? = " + clientChannel.isConnected());
        } else {

            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("New client connected: " + clientChannel.getRemoteAddress());
        }
    }

    public void readMessage(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        buffer.clear();

        int bytesRead = clientChannel.read(buffer);

        System.out.println("Got data = " + (char) buffer.get());
        if (bytesRead == -1) {
            key.cancel();
            System.out.println("Connection closed by client = " + clientChannel.getRemoteAddress());
            clientChannel.close();
            return;
        }
        System.out.println("Request received from = " + clientChannel.getRemoteAddress());
        key.interestOps(SelectionKey.OP_WRITE);
    }

    public void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        String response = "Hello from server!";
        buffer.clear();
        buffer.put(response.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        key.interestOps(SelectionKey.OP_READ);
    }

    private boolean checkIdClient(SocketChannel socketChannel) throws IOException {
        SocketAddress clientAddress = socketChannel.getRemoteAddress();
        String stringIpAddress = parseLine(String.valueOf(clientAddress));
        return readAndCheckBlackList(stringIpAddress);
    }

    private Boolean readAndCheckBlackList(String ip) {
        Path path = Paths.get(BLACK_LIST_IP);
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String currentValue;
            while ((currentValue = reader.readLine()) != null) {
                if (Objects.equals(currentValue, ip)) {
                    System.out.println("This IP in black list");
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private String parseLine(String line) {
        String[] newLine = line.split(":");
        return newLine[0];
    }

}

