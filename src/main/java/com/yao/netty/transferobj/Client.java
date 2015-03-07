package com.yao.netty.transferobj;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-3-7.
 */
public class Client {
    public static void main(String[]args) throws InterruptedException {
        Bootstrap bootstrap=new Bootstrap();
        Channel channel= bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelHandlerInitializer())
                .connect("127.0.0.1", 9999).channel();
        while (true){
            TimeUnit.SECONDS.sleep(1);
            if(channel.isWritable()){
                channel.writeAndFlush(new Dog(){{
                    setName("dog");
                    setAge(1);
                }}).sync();
            }
        }
       // channel.close();
    }
}
