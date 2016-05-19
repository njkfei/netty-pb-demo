package com.njp.learn.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.SystemPropertyUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by njp on 2016/5/18.
 */
public class TimeClientHandler  extends ChannelInboundHandlerAdapter{
    private int counter;
    private  byte[] req;

    public TimeClientHandler(){
        req = ( "QUERY TIME ORDER" + SystemPropertyUtil.get("line.separator") ).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
        for(int i = 0; i < 100;i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

/*    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.writeAndFlush(firstMessage);
    }*/

    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        ByteBuf buf = (ByteBuf)msg;

        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        String body = null;
        try {
            body = new String(req,"utf-8");
            System.out.println("Now is : " + body +
             " ; counter is : " + ++counter);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        channelHandlerContext.close();
    }
}
