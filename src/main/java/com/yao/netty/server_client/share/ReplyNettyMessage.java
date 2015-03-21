package com.yao.netty.server_client.share;

/**
 * Created by root on 15-3-21.
 */
public class ReplyNettyMessage extends NettyMessage {

    private String body;

    public ReplyNettyMessage() {
        setMsgType(MsgType.REPLY);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
