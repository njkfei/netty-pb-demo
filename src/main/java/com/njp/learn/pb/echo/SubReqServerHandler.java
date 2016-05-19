package com.njp.learn.pb.echo;

import com.njp.learn.model.SubscribeReqProto;
import com.njp.learn.model.SubscribeRespProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by njp on 2016/5/18.
 */
@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
    private int count;
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;

        if(req.getUserName().equalsIgnoreCase("njp")){
            System.out.println("service accept client subscribe req : [" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }


    }


    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private SubscribeRespProto.SubscribeResp resp(int id){
        return  SubscribeRespProto.SubscribeResp.newBuilder().setSubReqID(id)
                .setRespCode(0)
                .setDesc("netty book order succeed, 3 days later ,sent to the designated address;")
                .build();
    }
}
