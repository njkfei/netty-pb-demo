package com.njp.learn.echofix.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by njp on 2016/5/18.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private int count;
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

        String body = (String)msg;

        System.out.println("this is  : " + ++count  + " times receive client: [ "+ body +
         " ]");
        body += "$_";

        ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
        ctx.write(resp);
    }


    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
