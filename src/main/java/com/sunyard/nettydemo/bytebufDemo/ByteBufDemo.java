package com.sunyard.nettydemo.bytebufDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by lww on 2018/10/17.
 */
public class ByteBufDemo {
    public static void main(String[] args) {
        ByteBuf buf=Unpooled.buffer(64);
        buf.writeBytes("hello byteBuf".getBytes());
        System.out.println("rIdx:"+buf.readerIndex()+"|wIdx:"+buf.writerIndex());
        System.out.println(System.getProperty("line.separator").length());
    }
}
