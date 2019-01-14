package com.sunyard.javanio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lww on 2018/10/12.
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        Logger logger=LoggerFactory.getLogger(NioServer.class);
        Selector selector=Selector.open();
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open().bind(new InetSocketAddress("localhost",8080));
        serverSocketChannel.configureBlocking(false);
//        int ops = serverSocketChannel.validOps();
//        serverSocketChannel.register(selector,ops,null);
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        logger.info("server started...");
        while (true){
            int num=selector.select();
            Set<SelectionKey> keys=selector.selectedKeys();
            Iterator<SelectionKey> iterator=keys.iterator();
            ByteBuffer byteBuffer=ByteBuffer.allocate(64);
            while (iterator.hasNext()){
                SelectionKey key=iterator.next();
                if(key.isAcceptable()){
                    logger.info("a new connection...");
                    SocketChannel channel=serverSocketChannel.accept();
                    channel.configureBlocking(false);
//                    channel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                    channel.register(selector,SelectionKey.OP_READ);

                    logger.info("connection accepted:"+channel.getRemoteAddress());
                }
                else if(key.isReadable()){
                    SocketChannel channel=(SocketChannel)key.channel();
                    channel.read(byteBuffer);
                    logger.info("client:"+new String(byteBuffer.array()).trim());
                    byteBuffer.clear();
//                    byte[] bytes="Hello client".getBytes();
//                    byteBuffer.put(bytes);
//                    byteBuffer.flip();
//                    //buffer->channel
//                    channel.write(byteBuffer);
//                    byteBuffer.clear();
//                    channel.close();
                    channel.register(selector,SelectionKey.OP_WRITE);


                }else if(key.isWritable()){
                    //如果向Selector注册了写操作，就要有写判断
                    logger.info("key:writable");
                    SocketChannel clientChannel=(SocketChannel)key.channel();
                    byte[] bytes="Hello client".getBytes();
                    byteBuffer.put(bytes);
                    byteBuffer.flip();
                    //buffer->channel
                    clientChannel.write(byteBuffer);
                    byteBuffer.clear();
                    clientChannel.close();
                }
                iterator.remove();
            }
        }

    }
}
