package com.njp.learn.time2.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.SystemPropertyUtil;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by njp on 2016/5/18.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private int count;

    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)



        String body = (String)msg;

        System.out.println("the time server receive order : " + body
         + " ; the counter is : " + ++count);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                new Date(System.currentTimeMillis()).toString()
                : "BAD ORDER";

        currentTime = currentTime + SystemPropertyUtil.get("line.separator");
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
