package com.yao.nio.socketchannel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Channel;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * Created by yaozb on 15-4-20.
 */
public class SocketChannelIsOpen {
    @Test
    public void testIsOpen() throws IOException {
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        Channel xx= SelectorProvider.provider().inheritedChannel();
        System.out.println(xx);
        if(serverSocketChannel!=null&&serverSocketChannel.isOpen()){
            while (true){
                SocketChannel channel=serverSocketChannel.accept();
                Socket socket=channel.socket();
                socket.getOutputStream().write("hello !!\n".getBytes());
                socket.close();
            }
            //channel.configureBlocking(false);

        }
    }
}
