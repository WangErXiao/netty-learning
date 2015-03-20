package com.yao.netty.ssl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Created by root on 15-3-16.
 */
public class NettyServerImpl implements INettyServer {

    private EventLoopGroup bossGroup=new NioEventLoopGroup();
    private EventLoopGroup workerGroup=new NioEventLoopGroup();

    @Override
    public void start() throws Exception {
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 256);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                SSLEngine engine = ServerSSLContextFactory.getSSLContext().createSSLEngine();
                engine.setUseClientMode(false);
                p.addLast(new SslHandler(engine));
                p.addLast(new ObjectEncoder());
                p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                //p.addLast(new NettyServerHandler(callBacker, frontConfigManager, frontMachineManager));
            }
        });

        ChannelFuture f = bootstrap.bind(9999).sync();
        if (f.isSuccess()) {
            System.out.println("success--------");
        }
    }
    public static void main(String[]args) throws Exception {
        INettyServer server=new NettyServerImpl();
        server.start();
    }
}
