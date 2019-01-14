package com.sunyard.javanio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * client也可以将Channel注册到选择器上
 * Created by lww on 2018/10/12.
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        Logger logger=LoggerFactory.getLogger(NioClient.class);
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("localhost",8080));
        logger.info("connected!");
        ByteBuffer byteBuffer=ByteBuffer.allocate(64);
        byteBuffer.put("Hello server".getBytes());
        //buffer从写模式转变为读模式
        byteBuffer.flip();
        //若果buffer没有写出去，会一直阻塞
        socketChannel.write(byteBuffer);
        logger.info("sending msg...");
        byteBuffer.clear();
        socketChannel.read(byteBuffer);
        logger.info("server:"+new String(byteBuffer.array()).trim());
        Thread.sleep(3000);
        socketChannel.close();
    }
}
