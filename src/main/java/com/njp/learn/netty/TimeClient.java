package com.njp.learn.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by njp on 2016/5/18.
 */
public class TimeClient {

    public void connect(int port,String host) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap sb = new Bootstrap();
        try {
            sb.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture f = sb.connect(host,port).sync();

            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        try {
            new TimeClient().connect(9999,"127.0.0.1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
