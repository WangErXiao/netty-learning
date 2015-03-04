package com.yao.netty.proxy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by root on 15-3-4.
 */
public class HexDumpProxyInitializer extends ChannelInitializer<SocketChannel>{
    private final String remoteHost;
    private final int remotePort;
    public HexDumpProxyInitializer(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        socketChannel.pipeline().addLast(
                new LoggingHandler(LogLevel.INFO)
                ,new HexDumpProxyFrontedHandler(remoteHost,remotePort)
        );

    }
}
