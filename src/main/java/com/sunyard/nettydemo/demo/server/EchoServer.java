package com.sunyard.nettydemo.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by lww on 2018/9/28.
 */
public class EchoServer {
    private final int port = 8080;

    public static void main(String[] args) throws Exception {
        new EchoServer().start();
    }

    /*
    The following steps are required in bootstrapping:
    ■ Create a ServerBootstrap instance to bootstrap and bind the server.
    ■ Create and assign an NioEventLoopGroup instance to handle event processing,
        such as accepting new connections and reading/writing data.
    ■ Specify the local InetSocketAddress to which the server binds.
    ■ Initialize each new Channel with an EchoServerHandler instance.
    ■ Call ServerBootstrap.bind() to bind the server.
     */
    public void start() throws Exception {
        EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
