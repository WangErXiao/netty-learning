package com.yao.nio.echo;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Created by root on 15-3-21.
 */
public class EchoHandler implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    public EchoHandler(Selector selector,ServerSocketChannel serverSocketChannel) {
        this.selector=selector;
        this.serverSocketChannel=serverSocketChannel;
    }
    @Override
    public void run() {
        while (true){
            try {
               selector.select();
               Set<SelectionKey> keys= selector.selectedKeys();
               for (SelectionKey key:keys){
                  dealIO(key);
               }
               keys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void dealIO(SelectionKey key) throws IOException {
        if (key.isAcceptable()){
            SocketChannel socketChannel=serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        }else if(key.isReadable()){
            SocketChannel socketChannel=(SocketChannel)key.channel();
            ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
            while(socketChannel.read(byteBuffer)>0){
                 byteBuffer.flip();
                 socketChannel.write(byteBuffer);
                 byteBuffer.clear();
            }
            socketChannel.register(selector, SelectionKey.OP_READ);
        }

    }
}
