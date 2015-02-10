package com.yao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by root on 15-2-10.
 */
public class Server {
    private final int port;
    public Server(int port) {
        this.port = port;
    }
    public static void main(String[]args){
        new Server(9999).start();
    }
    public void start(){
        try {
            CountDownLatch latch=new CountDownLatch(1);
            Selector selector=Selector.open();
            //监听 端口
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket=serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress("127.0.0.1",9999));


            //reactor
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            new Thread(new ReactorTask(selector)).start();

            latch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static class ReactorTask implements Runnable{
        private Selector selector;
        private ReactorTask(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            try {
                while (true){
                    selector.select();
                    Set selectKeys=selector.selectedKeys();
                    Iterator iterator=selectKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key=(SelectionKey)iterator.next();
                        dealIO(key);
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void dealIO(SelectionKey key) throws IOException {
            if(key.isAcceptable()){
                ServerSocketChannel serverSocketChannel=(ServerSocketChannel)key.channel();
                SocketChannel socketChannel=serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }else if(key.isReadable()){
                SocketChannel channel=(SocketChannel)key.channel();
                ByteBuffer byteBuffer=ByteBuffer.allocate(2048);
                int num=channel.read(byteBuffer);
                while (num>0){
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array()));
                    byteBuffer.clear();
                    num=channel.read(byteBuffer);
                }
                channel.register(selector,SelectionKey.OP_WRITE);
            }else if(key.isWritable()){
                SocketChannel channel=(SocketChannel)key.channel();
                ByteBuffer buffer=ByteBuffer.allocate(1024);
                buffer.put("i has received your msg\n".getBytes());
                buffer.flip();
                channel.write(buffer);
                channel.register(selector,SelectionKey.OP_READ);
            }
        }
    }
}
