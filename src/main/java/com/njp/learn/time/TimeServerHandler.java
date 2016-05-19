package com.njp.learn.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by njp on 2016/5/18.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private int count;

    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)



        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];

        buf.readBytes(req);

        String body = null;
        try {
            body = new String(req,"utf-8").substring(0,req.length - System.getProperty("line.separator").length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("the time server receive order : " + body
         + " ; the counter is : " + ++count);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                new Date(System.currentTimeMillis()).toString()
                : "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }


    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
