package com.njp.learn.pb.echo;

import com.njp.learn.model.SubscribeReqProto;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by njp on 2016/5/18.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter{
    private final String ECHO_REQ = "hi njp, welcome to netty. $_";
    private int count;

    public SubReqClientHandler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
       for(int i = 0; i < 10; i++)
           ctx.writeAndFlush(subReq(i));
    }

/*    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.writeAndFlush(firstMessage);
    }*/

    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

        System.out.println("this is  : " + ++count  + " times receive server: [ "+ msg +
                " ]");


    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        channelHandlerContext.close();
    }

    private SubscribeReqProto.SubscribeReq subReq(int id){
        List<String> address = new ArrayList<String>();
        address.add("beijing njp");
        address.add("shanghai njp");
        return
                SubscribeReqProto.SubscribeReq.newBuilder()
                .setSubReqID(id)
                .addAllAddress(address)
                .setProductName("netty in action")
                .setUserName("njp")
                .build();
    }
}
