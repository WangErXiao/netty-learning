package com.yao.netty.server_client.share;

/**
 * Created by root on 15-3-21.
 */
public class AskNettyMessage extends NettyMessage {

    private String body;

    public AskNettyMessage() {
        setMsgType(MsgType.ASK);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
