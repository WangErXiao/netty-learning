package com.yao.netty.server_client.server;

import com.yao.netty.server_client.share.LoginNettyMessage;
import com.yao.netty.server_client.share.MsgType;
import com.yao.netty.server_client.share.NettyMessage;
import com.yao.netty.server_client.share.ReplyNettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-3-21.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<NettyMessage>{

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.INSTANCE.remove((SocketChannel) ctx.channel());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive------server");
        ctx.writeAndFlush(new LoginNettyMessage());
        System.out.println("reply ping");
    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, NettyMessage msg) throws Exception {
        System.out.println("server receive msg-------------");
        NettyMessage message=(NettyMessage)msg;
        MsgType msgType=message.getMsgType();
        SocketChannel socketChannel=(SocketChannel)NettyChannelMap.INSTANCE.get(message.getSeq());
        if(socketChannel==null){
            switch (msgType){
                case LOGIN:{
                    LoginNettyMessage loginNettyMessage=(LoginNettyMessage)msg;
                    if("wangerxiao".equals(loginNettyMessage.getUsername())&&"wangerxiao".equals(loginNettyMessage.getPassword())){
                        NettyChannelMap.INSTANCE.add(loginNettyMessage.getSeq(),(SocketChannel)channelHandlerContext.channel());
                        System.out.println("登录成功");
                    }
                }break;
                case PING:{
                   SocketChannel tmp=(SocketChannel)channelHandlerContext.channel();
                   tmp.writeAndFlush(new LoginNettyMessage());
                   System.out.println("reply ping");
                }break;
            }
        }
       switch (msgType){
           case REPLY:{
               ReplyNettyMessage replyNettyMessage=(ReplyNettyMessage)msg;
               System.out.println("reply:"+replyNettyMessage.getBody()+"  xxxx:"+Math.random());
           }
       }
       ReferenceCountUtil.release(msg);
    }

}
