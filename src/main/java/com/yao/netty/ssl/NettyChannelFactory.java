package com.yao.netty.ssl;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by root on 15-3-16.
 */
public enum NettyChannelFactory {
    INSTANCE;
    private Map<String,Channel> group=new ConcurrentHashMap<String, Channel>();
    public void add(String key,Channel channel){
        group.put(key,channel);
    }
    public boolean remove(Channel o){
        if (null == o) {
            return false;
        }
        for (String key : group.keySet()) {
              if(group.get(key).equals(o)){
                  group.remove(key);
                  return true;
              }
        }
        return false;
    }
    public Channel get(String key){
        return group.get(key);
    }
}
