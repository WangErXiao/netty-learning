package com.yao.netty.server_client.client;

import com.yao.netty.server_client.share.*;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-3-21.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<NettyMessage> {
    final String seq;
    public NettyClientHandler(String seq) {
        this.seq = seq;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    final PingNettyMessage pingNettyMessage=new PingNettyMessage();
                    pingNettyMessage.setSeq(seq);
                    ctx.writeAndFlush(pingNettyMessage);
                    System.out.println("writer_idle----------");
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive------client");
        final PingNettyMessage pingNettyMessage=new PingNettyMessage();
        pingNettyMessage.setSeq(seq);
        ctx.writeAndFlush(pingNettyMessage);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage) throws Exception {
        System.out.println("server receive msg-------------");
        MsgType msgType=((NettyMessage)nettyMessage).getMsgType();
        switch (msgType){
            case  LOGIN:{
                LoginNettyMessage loginNettyMessage=new LoginNettyMessage();
                loginNettyMessage.setPassword("wangerxiao");
                loginNettyMessage.setUsername("wangerxiao");
                loginNettyMessage.setSeq(seq);
                channelHandlerContext.writeAndFlush(loginNettyMessage);
            }break;
            case ASK:{
                ReplyNettyMessage replyNettyMessage=new ReplyNettyMessage();
                replyNettyMessage.setSeq(seq);
                replyNettyMessage.setBody("this is reply content:" + Math.random());
                channelHandlerContext.writeAndFlush(replyNettyMessage);
            }
            default:{}break;
        }
        ReferenceCountUtil.release(nettyMessage);
    }


}
