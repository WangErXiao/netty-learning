package com.yao.netty.objecho;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by root on 15-3-21.
 */
public class ObjEchoClient {

    private String host;
    private int port;
    public ObjEchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjEchoClientHandler());
                        }
                    });
            // Start the connection attempt.
            b.connect(host, port).sync().channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public  static void main(String[]args) throws InterruptedException {
        ObjEchoClient objEchoClient=new ObjEchoClient("127.0.0.1",9999);
        objEchoClient.start();
    }
}
