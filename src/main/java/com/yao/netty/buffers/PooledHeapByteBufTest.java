package com.yao.netty.buffers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Test;

import java.nio.ByteOrder;


/**
 * Created by yaozb on 15-4-28.
 */
public class PooledHeapByteBufTest {
    @Test
    public void allocateByteBuf() throws InterruptedException {
        ByteBuf buffer;
        buffer = PooledByteBufAllocator.DEFAULT.heapBuffer(256,256);
        byte[]bytes=buffer.array();
        buffer = PooledByteBufAllocator.DEFAULT.heapBuffer(256,512);
        System.out.println(buffer.array()==bytes);
    }
}

