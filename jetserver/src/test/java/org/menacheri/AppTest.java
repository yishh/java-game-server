package org.menacheri;

import static org.junit.Assert.assertEquals;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;
import org.menacheri.util.NettyUtils;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @Test
	public void nettyUtilStringWriteRead()
	{
		String msg = "Hello World!";
		ChannelBuffer stringBuffer = NettyUtils.writeString(msg);
		String reply = NettyUtils.readString(stringBuffer);
		assertEquals(msg, reply);
	}
	
    @Test
	public void nettyUtilMultiStringWriteRead()
	{
		String hello = "Hello ";
		String world = "World!";
		ChannelBuffer stringBuffer1 = NettyUtils.writeString(hello);
		ChannelBuffer stringBuffer2 = NettyUtils.writeString(world);
		ChannelBuffer stringBuffer = ChannelBuffers.wrappedBuffer(stringBuffer1,stringBuffer2);
		String helloReply = NettyUtils.readString(stringBuffer);
		String worldReply = NettyUtils.readString(stringBuffer);
		assertEquals(hello, helloReply);
		assertEquals(worldReply, world);
	}
	
    @Test
	public void nettyUtilVarArgsStringWriteRead()
	{
		String hello = "Hello ";
		String world = "World!";
		ChannelBuffer stringBuffer = NettyUtils.writeStrings(hello,world);
		String helloReply = NettyUtils.readString(stringBuffer);
		String worldReply = NettyUtils.readString(stringBuffer);
		assertEquals(hello, helloReply);
		assertEquals(worldReply, world);
	}
}
