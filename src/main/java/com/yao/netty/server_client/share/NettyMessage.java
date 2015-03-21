package com.yao.netty.server_client.share;

import java.io.Serializable;

/**
 * Created by root on 15-3-21.
 */
public class NettyMessage  implements Serializable{
    private static final long serialVersionUID = 1L;
    private String seq;
    private MsgType msgType;
    public MsgType getMsgType() {
        return msgType;
    }
    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }
}
