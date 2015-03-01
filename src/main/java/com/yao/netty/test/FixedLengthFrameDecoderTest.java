package com.yao.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by root on 15-3-1.
 */
public class FixedLengthFrameDecoderTest {
    @Test
    public void testFramesDecoded(){

        ByteBuf buf= Unpooled.buffer();
        for (int i=0;i<9;i++){
            buf.writeByte(i);
        }
        ByteBuf input =buf.duplicate();

        EmbeddedChannel channel=new EmbeddedChannel(
                new FixedLengthFrameDecoder(3)
        );
        Assert.assertTrue(channel.writeInbound(input));
        Assert.assertTrue(channel.finish());

//        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
  //      Assert.assertEquals(buf.readBytes(3), channel.readInbound());
    //    Assert.assertEquals(buf.readBytes(3), channel.readInbound());
      //  Assert.assertNull(channel.readInbound());

    }
}
