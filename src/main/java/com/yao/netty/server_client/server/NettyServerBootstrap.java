package com.yao.netty.server_client.server;

import com.yao.netty.server_client.share.AskNettyMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-3-21.
 */
public class NettyServerBootstrap {
    private int port;
    private SocketChannel socketChannel;
    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    private void bind() throws InterruptedException {
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup worker=new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(boss,worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();

                p.addLast(new ObjectEncoder());
                p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                p.addLast(new NettyServerHandler());
            }
        });
        ChannelFuture f= bootstrap.bind(port).sync();
        if(f.isSuccess()){
            System.out.println("server start---------------");
        }
    }

    public static void main(String []args) throws InterruptedException {
        NettyServerBootstrap bootstrap=new NettyServerBootstrap(9999);
        while (true){
            SocketChannel channel=NettyChannelMap.INSTANCE.get("wangerxiao");
            if(channel!=null){
                channel.writeAndFlush(new AskNettyMessage());
            }else{
                TimeUnit.SECONDS.sleep(2);
                System.out.println("loop ---------");
            }
        }
    }
}
