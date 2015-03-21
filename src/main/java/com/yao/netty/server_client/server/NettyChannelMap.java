package com.yao.netty.server_client.server;


import io.netty.channel.socket.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by root on 15-3-21.
 */
public enum  NettyChannelMap {
    INSTANCE;
    private Map<String,SocketChannel> channelMap=new ConcurrentHashMap<String, SocketChannel>();
    public void add(String id,SocketChannel socketChannel){
        channelMap.put(id,socketChannel);
    }
    public void remove(SocketChannel socketChannel){
        for (Map.Entry entry:channelMap.entrySet()){
            if (entry.getValue()==socketChannel){
                channelMap.remove(entry.getKey());
            }
        }
    }
    public SocketChannel get(String id){
       return  channelMap.get(id);
    }
}
