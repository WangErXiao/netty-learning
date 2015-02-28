package com.yao.netty.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by root on 15-2-28.
 */
public class TestDelimiterServer {
    public static void main(String[]args){

        EventLoopGroup boss=new NioEventLoopGroup(1);
        EventLoopGroup worker=new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new LineBasedHandlerInitializer());
            Channel ch = bootstrap.bind(9999).sync().channel();
            System.out.println("Starting----------");
            ch.closeFuture().sync();
            System.out.println("Ending  ----------");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
