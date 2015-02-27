package com.yao.netty.buffers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by root on 15-2-12.
 */
public class ByteBufRandomAccess {
    public static void main(String[]args){

        final ByteBuf buf= Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("hello world!\r\n", Charset.forName("UTF-8"))
        );
        System.out.println(buf.capacity());
        System.out.println(buf.readableBytes());
        System.out.println(buf.writableBytes());
        for (int i=0;i<buf.readableBytes();i++){
            System.out.println((char)buf.getByte(i));
        }
    }
}
