package com.sunyard.nettydemo.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lww on 2018/9/28.
 */
public class PlainNioServer {
    final int port=8081;
    public void start() throws Exception{
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket=serverSocketChannel.socket();
        InetSocketAddress address=new InetSocketAddress(port);
        socket.bind(address);
        /*
        Opens the Selector for handlingchannels
         */
        Selector selector=Selector.open();
        /*
        Registers the ServerSocket with the
        Selector to accept connections
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        for(;;){
            try {
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                // handle exception
                break;
            }
            Set<SelectionKey> readyKeys= selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key=iterator.next();
                iterator.remove();
                try{
                    if(key.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE |
                                SelectionKey.OP_READ, msg.duplicate());
                        System.out.println(
                                "Accepted connection from " + client);
                    }
                    if (key.isWritable()){
                        SocketChannel client =
                                (SocketChannel)key.channel();
                        ByteBuffer buffer =
                                (ByteBuffer)key.attachment();
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {
                                break;
                            }
                        }
                        client.close();
                    }
                }catch (Exception e){
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
// ignore on close
                    }
                }

            }
        }

    }

    public static void main(String[] args)throws Exception{
        new PlainNioServer().start();
    }
}
