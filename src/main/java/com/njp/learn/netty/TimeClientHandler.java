package com.njp.learn.netty;

import com.example.tutorial.AddressBookProtos;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.io.UnsupportedEncodingException;

/**
 * Created by njp on 2016/5/18.
 */
public class TimeClientHandler  extends ChannelInboundHandlerAdapter{
    private  final ByteBuf firstMessage;

    public TimeClientHandler(){
        byte[] req = "QUERY TIME ORDER".getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(firstMessage);
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
            System.out.println("Now is : " + body);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        channelHandlerContext.close();
    }
}
