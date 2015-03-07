package com.yao.netty.transferobj;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by root on 15-3-7.
 */
public class Server {

    public static void main(String[]args) throws InterruptedException {
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(1),new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelHandlerInitializer()).bind(9999).sync().channel().closeFuture().sync();

    }
}
