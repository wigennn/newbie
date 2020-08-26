package com.redbyte.core;

import com.redbyte.constant.Separators;
import com.redbyte.registry.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@Slf4j
public class NettyServer extends Server {

    private Thread thread;

    private ServiceRegistry serviceRegistry;

    private Map<String, Object> serviceMap = new HashMap<>();

    private String serverAddress;

    public NettyServer(String serverAddress, String registryAddress) {
        this.serverAddress = serverAddress;
        serviceRegistry = new ServiceRegistry(registryAddress);
    }

    public void addService(String serviceName, String version, Object bean) {
        String serviceKey = serviceName + Separators.SERVICE_SEPARATOR + version;
        serviceMap.put(serviceKey, bean);
    }

    @Override
    public void start() throws Exception {
        thread = new Thread(() -> {

            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new NewbieServerInitializer(serviceMap))
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                String[] array = serverAddress.split(":");
                String host = array[0];
                Integer port = Integer.parseInt(array[1]);

                ChannelFuture future = bootstrap.bind(host, port).sync();

                // 注册服务
                if (serviceRegistry != null) {
                    serviceRegistry.registerService(host, port, serviceMap);
                }

                future.channel().closeFuture().sync();
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                serviceRegistry.unregisterService();
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });

        thread.start();
    }

    @Override
    public void stop() throws Exception {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}
