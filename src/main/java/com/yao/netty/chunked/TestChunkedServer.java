package com.yao.netty.chunked;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.File;

/**
 * Created by root on 15-3-1.
 */
public class TestChunkedServer {
    public static void main(String[]args){
        File file=new File("/home/yao/Downloads/twemproxy-master.zip");
        EventLoopGroup boss=new NioEventLoopGroup(1);
        EventLoopGroup worker=new NioEventLoopGroup();
        try {

            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss,worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChunkedWriteHandlerInitializer(file));

            io.netty.channel.Channel future= bootstrap.bind(9999).sync().channel();
            System.out.println("starting---------");
            future.closeFuture().sync();

        }catch (Exception e){

        }finally {

        }
    }
}
