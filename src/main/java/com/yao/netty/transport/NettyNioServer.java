package com.yao.netty.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by root on 15-2-12.
 */
public class NettyNioServer {
    public void server(int port) throws InterruptedException {
        final ByteBuf buf= Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("hello world!\r\n", Charset.forName("UTF-8"))
        );
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            ctx.write(buf.duplicate())
                                                    .addListener(ChannelFutureListener.CLOSE);
                                            ctx.flush();

                                        }
                                    }
                            );
                        }
                    });
            ChannelFuture f = bootstrap.bind().sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
    public static void main(String[]args){
        try {
            new NettyNioServer().server(9999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
