package com.yao.netty.server_client.share;

/**
 * Created by root on 15-3-21.
 */
public class PingNettyMessage extends NettyMessage {
    private String ping;

    public PingNettyMessage() {
        setMsgType(MsgType.PING);
    }
    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }
}
