package com.redbyte.core;

import com.redbyte.core.handler.NewbieServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
public class NewbieServerInitializer extends ChannelInitializer<SocketChannel> {

    private Map<String, Object> serviceMap;

    public NewbieServerInitializer(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 0, 90, TimeUnit.SECONDS))
                .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                .addLast(new NewbieServerHandler(serviceMap))
        ;
    }
}
