package com.yao.nio;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-10.
 */
public class Client {
    private final String host;
    private final int port;
    private SocketChannel socketChannel;
    private Selector selector;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public static void main(String[]args) throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(1);
        Client client=new Client("127.0.0.1",9999);
        client.start();
        latch.await();
    }
    public void start(){
        try {
            this.socketChannel=SocketChannel.open();
            socketChannel.configureBlocking(false);
            this.selector=Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress(host, port));
            new Thread(new ReactorTask(selector)).start();
        } catch (IOException e) {
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
            if(key.isReadable()){
                SocketChannel socketChannel=(SocketChannel)key.channel();
                ByteBuffer byteBuffer=ByteBuffer.allocate(2048);
                int num=socketChannel.read(byteBuffer);
                while (num>0){
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array()));
                    byteBuffer.clear();
                    num=socketChannel.read(byteBuffer);
                }
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(key.isWritable()){
                SocketChannel socketChannel=(SocketChannel)key.channel();
                ByteBuffer buffer=ByteBuffer.allocate(1024);
                buffer.put(("i send your " + new Random().nextInt(10) + "").getBytes());
                buffer.flip();
                socketChannel.write(buffer);
                socketChannel.register(selector, SelectionKey.OP_READ);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(key.isConnectable()){
                SocketChannel socketChannel=(SocketChannel)key.channel();
                // 判断此通道上是否正在进行连接操作。
                // 完成套接字通道的连接过程。
                if (socketChannel.isConnectionPending()) {
                    socketChannel.finishConnect();
                    System.out.println("完成连接!");
                    ByteBuffer byteBuffer= ByteBuffer.allocate(1024);
                    byteBuffer.put("Hello,Server".getBytes());
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                }
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
        }
    }

}
