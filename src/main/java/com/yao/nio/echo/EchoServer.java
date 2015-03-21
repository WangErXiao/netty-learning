package com.yao.nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by root on 15-3-21.
 */
public class EchoServer {
    private int port;
    public EchoServer(int port) {
        this.port=port;
    }
    public void start() throws IOException {
        Selector selector=Selector.open();
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        new Thread(new EchoHandler(selector,serverSocketChannel)).start();
    }
    public static void main(String[]args) throws IOException {
        EchoServer echoServer=new EchoServer(9999);
        echoServer.start();
    }
}
