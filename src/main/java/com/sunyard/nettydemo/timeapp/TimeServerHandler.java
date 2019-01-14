package com.sunyard.nettydemo.timeapp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Date;

/**
 * Created by lww on 2018/10/15.
 */
public class TimeServerHandler extends SimpleChannelInboundHandler{
    private int counter;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf=(ByteBuf)msg;
//        byte[] req=new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body=new String(req,"utf-8")
//                .substring(0,req.length- System.getProperty("line.separator").length());
        String body=(String)msg;
        System.out.println("the time server receive order :"+body+";the counter is "+(++counter));
        String currentTime="Query Time Order".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"bad order";
        ByteBuf res= Unpooled.copiedBuffer((currentTime+"\n").getBytes());
        ctx.write(res);
        System.out.println(ctx.handler());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
