package com.yao.nio.socketchannel;

import org.junit.Test;
import org.omg.CORBA.TIMEOUT;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by yaozb on 15-4-20.
 *

 #!/bin/bash

 i=100
 while [ $i -ne 0 ]
 do
 telnet localhost 9999 | awk 'NR==4 {print $0}';
 let i=($i-1);
 done

 *
 *
 *
 *
 *
 */
public class SocketChannelIsOpen {
    @Test
    public void testIsOpen() throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //listening-------------
        serverSocketChannel.bind(new InetSocketAddress(9999));



        ExecutorService executor= Executors.newFixedThreadPool(4);
        executor.submit(new MyAcceptorTask(serverSocketChannel));
        executor.submit(new MyAcceptorTask(serverSocketChannel));
        executor.submit(new MyAcceptorTask(serverSocketChannel));
        executor.submit(new MyAcceptorTask(serverSocketChannel));
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        }
    }

    class MyAcceptorTask implements Runnable{
        ServerSocketChannel serverSocketChannel;
        public MyAcceptorTask(ServerSocketChannel serverSocketChannel) {
            this.serverSocketChannel = serverSocketChannel;
        }

        @Override
        public void run() {
            if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
                while (true) {
                    SocketChannel channel = null;
                    try {
                        channel = serverSocketChannel.accept();
                        Socket socket = channel.socket();
                        socket.getOutputStream().write(("hello !!"+ Thread.currentThread().getId()+"\n").getBytes());
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

