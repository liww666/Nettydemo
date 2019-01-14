package com.sunyard.nettydemo.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by lww on 2018/9/28.
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        new EchoClient("localhost",8080).start();
    }

    /*
    Here are the main steps recorded in the preceding build log:
    ■ Maven determines the build order: first the parent pom.xml, and then the
        modules (subprojects).
    ■ If the Netty artifacts aren’t found in the user’s local repository, Maven will
        download them from the public Maven repositories (not shown here).
    ■ The clean and compile phases of the build lifecycle are run.
    ■ The maven-jar-plugin is executed.
     */
    public void start() throws Exception{
        EchoClientHandler clientHandler=new EchoClientHandler();
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap b=new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
