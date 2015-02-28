package com.yao.netty.idlehandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by root on 15-2-28.
 */
public class TestIdleServer {
    public static void main(String[]args){
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup).
                    channel(NioServerSocketChannel.class).
                    childHandler(new IdleStateHandlerInitializer());
            Channel ch = bootstrap.bind(9999).sync().channel();
            System.err.println("start------------");
            ch.closeFuture().sync();
            System.err.println("end--------------");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
