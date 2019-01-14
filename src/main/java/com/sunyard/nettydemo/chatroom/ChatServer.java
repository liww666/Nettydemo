package com.sunyard.nettydemo.chatroom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * Created by lww on 2018/10/8.
 */
public class ChatServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup boos=new NioEventLoopGroup();
        EventLoopGroup worker=new NioEventLoopGroup();
        ChannelGroup group=new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
        try{
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boos,worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitialzer(group));
            ChannelFuture future=bootstrap.bind(8089).sync();
            future.channel().closeFuture().sync();
        }finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
