package com.yao.netty.server_client.client;

import com.yao.netty.server_client.share.LoginNettyMessage;
import com.yao.netty.server_client.share.NettyMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by root on 15-3-21.
 */
public class NettyClientBootstrap {
    private int port;
    private String host;
    private SocketChannel socketChannel;
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);
    public NettyClientBootstrap(int port, String host) throws InterruptedException {
        this.port = port;
        this.host = host;
        start();
    }
    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(host,port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new IdleStateHandler(20,10,0));
                socketChannel.pipeline().addLast(new ObjectEncoder());
                socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                socketChannel.pipeline().addLast(new NettyClientHandler("wangerxiao"));
            }
        });
        ChannelFuture future =bootstrap.connect(host,port).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel)future.channel();
            System.out.println("connect server  成功---------");
        }


    }
    public void sendMsg(NettyMessage nettyMessage){
        socketChannel.writeAndFlush(nettyMessage);



    }
    public static void main(String[]args) throws InterruptedException {
            NettyClientBootstrap bootstrap=new NettyClientBootstrap(9999,"localhost");
            bootstrap.sendMsg(new LoginNettyMessage(){{
                setPassword("wangerxiao");
                setUsername("wangerxiao");
                setSeq("wangerxiao");
            }});

    }
}
