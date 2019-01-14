package com.sunyard.nettydemo.timeapp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lww on 2018/10/15.
 */
public class TimeClientHandler extends SimpleChannelInboundHandler {
    private static Logger logger=LoggerFactory.getLogger(TimeClientHandler.class);
    private final ByteBuf firstMsg;
    private int counter;

    public TimeClientHandler() {
        byte[] req="QUERY TIME ORDER".getBytes();
        firstMsg=Unpooled.buffer(req.length);
        firstMsg.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for(int i=0;i<100;i++){
            ByteBuf buf=Unpooled.buffer(124);
            buf.writeBytes(("QUERY TIME ORDER\n").getBytes());
           ctx.writeAndFlush(buf);
            System.out.println("---------------send query------------");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf=(ByteBuf)msg;
//        byte[] req=new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body=new String(req,"utf-8");
        String body=(String)msg;
        System.out.println("now is:"+body+";the counter is :"+(++counter));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("exception:"+cause.getMessage());
        ctx.close();
    }
}
