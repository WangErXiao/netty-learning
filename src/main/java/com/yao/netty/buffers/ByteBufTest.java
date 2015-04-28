package com.yao.netty.buffers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * Created by yaozb on 15-4-28.
 */
public class ByteBufTest {

    @Test
    public void allocateByteBuf(){
        ByteBuf byteBuf= Unpooled.buffer();
        byteBuf.writeBytes("hello world".getBytes());
        System.out.println(new String(byteBuf.array()));
        System.out.println((char) byteBuf.getByte(0));
        System.out.println((char) byteBuf.getByte(1));
        System.out.println((char) byteBuf.getByte(2));
        System.out.println((char) byteBuf.getByte(3));
        System.out.println((char) byteBuf.getByte(4));
        System.out.println((char)byteBuf.getByte(5));
        System.out.println((char)byteBuf.getByte(30));


    }
    @Test
    public void seqenceReadByteBuf(){
        ByteBuf byteBuf= Unpooled.buffer();
        byteBuf.writeBytes("hello world".getBytes());
        while(byteBuf.readableBytes()>0){
            System.out.println((char)byteBuf.readByte());
        }
    }
    @Test
    public void writeByteBuf(){
        ByteBuf byteBuf= Unpooled.buffer(50);
        while(byteBuf.writableBytes()>10){
            byteBuf.writeByte('a');
            byteBuf.writerIndex(byteBuf.writerIndex()+10);
        }
        System.out.println("writerIndex:"+byteBuf.writerIndex());
        System.out.println("readerIndex:"+byteBuf.readerIndex());
        System.out.println("index of 0 is:"+(char)byteBuf.getByte(0));
        System.out.println("index of 11 is:"+(char)byteBuf.getByte(11));
        System.out.println("index of 22 is:"+(char)byteBuf.getByte(22));
        System.out.println("index of 33 is:"+(char)byteBuf.getByte(33));
        System.out.println("index of 40 is:"+(char)byteBuf.getByte(40));
        System.out.println(new String(byteBuf.array()));
    }

    @Test
    public void discardReadBytesTest(){
        ByteBuf byteBuf= Unpooled.buffer();
        byteBuf.writeBytes("hello world".getBytes());
        System.out.println("readerIndex:" + byteBuf.readerIndex());
        System.out.println("writerIndex:" + byteBuf.writerIndex());
        System.out.println((char)byteBuf.readByte());
        System.out.println(new String(byteBuf.array()));
        byteBuf.discardReadBytes();
        System.out.println("readerIndex:" + byteBuf.readerIndex());
        System.out.println("writerIndex:" + byteBuf.writerIndex());
        System.out.println(new String(byteBuf.array()));
    }
    @Test
    public void clearMarkReset(){
        ByteBuf byteBuf= Unpooled.buffer();
        byteBuf.writeBytes("hello world".getBytes());
        System.out.println(new String(byteBuf.array()));
        System.out.println("readerIndex:" + byteBuf.readerIndex());
        System.out.println("writerIndex:" + byteBuf.writerIndex());
        byteBuf.clear();
        System.out.println("after clear");
        System.out.println("readerIndex:" + byteBuf.readerIndex());
        System.out.println("writerIndex:" + byteBuf.writerIndex());
        System.out.println(new String(byteBuf.array()));

        byteBuf.writerIndex(11);
        //在index 为1时mark ，reset之后重新回到mark的地方
        byteBuf.readByte();
        byteBuf.markReaderIndex();
        byteBuf.readByte();
        byteBuf.readByte();
        byteBuf.resetReaderIndex();
        System.out.println("readerIndex:" + byteBuf.readerIndex());
    }

}
