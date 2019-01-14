package com.sunyard.nettydemo.demo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * The EchoServerHandler implements the business logic.
 * Server端不用SimpleChannelInboundHandlerAdaoter原因
 * In the client, when channelRead0() completes, you have the incoming message and
 * you’re done with it. When the method returns, SimpleChannelInboundHandler takes
 * care of releasing the memory reference to the ByteBuf that holds the message.
 * In EchoServerHandler you still have to echo the incoming message to the sender,
 * and the write() operation, which is asynchronous, may not complete until after
 * channelRead() returns (shown in listing 2.1). For this reason EchoServerHandler
 * extends ChannelInboundHandlerAdapter, which doesn’t release the message at
 * this point.
 * The message is released in channelReadComplete() in the EchoServerHandler
 * when writeAndFlush() is called (listing 2.1).
 * Created by lww on 2018/9/28.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println(
                "Server received: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
