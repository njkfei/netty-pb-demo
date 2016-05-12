package com.njp.learn.netty;

import com.example.tutorial.AddressBookProtos;
import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.FileInputStream;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

        System.out.println(msg.toString());

        ByteBuf in = (ByteBuf) msg;

        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);

        System.out.println("receive data size : " + bytes.length);

        try {
            AddressBookProtos.AddressBook ab = AddressBookProtos.AddressBook.parseFrom(bytes);

            AddressBookProtos.Person person = ab.getPerson(0);

            System.out.println(person.getId());
            System.out.println(person.getName());
            System.out.println(person.getEmail());

            AddressBookProtos.Person.PhoneNumber phoneNumber = person.getPhone(0);

            System.out.println(phoneNumber.getNumber());

        } catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
           ReferenceCountUtil.release(msg); // (2)
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}