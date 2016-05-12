/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.njp.learn.netty;

import com.example.tutorial.AddressBookProtos;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler()  throws Exception{
        AddressBookProtos.Person.PhoneNumber.Builder phone  = AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("15910534204");

        AddressBookProtos.Person.Builder person = AddressBookProtos.Person.newBuilder().addPhone(phone).setId(1).setEmail("njp@sanhao.com")
                .setName("njp");
        AddressBookProtos.AddressBook.Builder ab = AddressBookProtos.AddressBook.newBuilder().addPerson(person);

        FileOutputStream fo = new FileOutputStream("hello");
        ab.build().writeTo(fo);
        fo.close();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ab.build().writeTo(byteArrayOutputStream);

        System.out.println(byteArrayOutputStream.toByteArray().length);

        AddressBookProtos.AddressBook nab = AddressBookProtos.AddressBook.parseFrom(byteArrayOutputStream.toByteArray());

        AddressBookProtos.Person ps = nab.getPerson(0);

        System.out.println(ps.getId());
        System.out.println(ps.getName());
        System.out.println(ps.getEmail());

        AddressBookProtos.Person.PhoneNumber phoneNumber = ps.getPhone(0);

        System.out.println(phoneNumber.getNumber());


        firstMessage = Unpooled.buffer(byteArrayOutputStream.toByteArray().length);

        firstMessage.writeBytes(byteArrayOutputStream.toByteArray());


        byteArrayOutputStream.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
       ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
