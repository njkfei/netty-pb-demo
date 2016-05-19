package com.njp.learn.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * Created by njp on 2016/5/18.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter{
    private final String ECHO_REQ = "hi njp, welcome to netty. $_";
    private int count;

    public EchoClientHandler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
       for(int i = 0; i < 10; i++)
           ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
    }

/*    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.writeAndFlush(firstMessage);
    }*/

    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

        String body = (String)msg;
        System.out.println("this is  : " + ++count  + " times receive server: [ "+ body +
                " ]");


    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        channelHandlerContext.close();
    }
}
