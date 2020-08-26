package com.redbyte.core.handler;

import com.redbyte.common.NewbieRequest;
import com.redbyte.common.NewbieResponse;
import com.redbyte.constant.HeartBeat;
import com.redbyte.constant.Separators;
import com.redbyte.util.SpringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@Slf4j
public class NewbieServerHandler extends SimpleChannelInboundHandler<NewbieRequest> {

    private Map<String, Object> serviceMap;

    private final ThreadPoolTaskExecutor executor = SpringUtils.getBean("serverHandleThreadPool");

    public NewbieServerHandler(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NewbieRequest request) throws Exception {

        // 心跳检测
        if (HeartBeat.BEAT_ID.equals(request.getRequestId())) {
            log.info("server read heartbeat ping");
            return;
        }

        executor.execute(() -> {
            log.info("request info: {}", request.getRequestId());
            NewbieResponse response = new NewbieResponse();
            response.setRequestId(request.getRequestId());

            try {
                Object handleResult = handle(request);
                response.setData(handleResult);
            } catch (Exception e) {
                response.setErrorMsg(e.getMessage());
                log.error("server handle request error", e);
            }

            channelHandlerContext.writeAndFlush(response).addListener((channelFuture) -> {
                log.info("Send response for request" + response.getRequestId());
            });
        });


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("Server caught exception: " + cause.getMessage());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.channel().close();
            log.warn("Channel Idle in last {} seconds, close it", HeartBeat.BEAT_TIMEOUT);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    private Object handle(NewbieRequest request) throws Exception {

        String version = request.getVersion();
        String className = request.getClassName();
        String serviceKey = className + Separators.SERVICE_SEPARATOR + version;
        Object bean = serviceMap.get(serviceKey);
        if (bean == null) {
            log.error("can not find service implement with interface name:{} and version:{}", className, version);
            return null;
        }

        Class<?> beanClass = bean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        // JDK reflect
/*        Method method = beanClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(bean, parameters);*/

        FastClass fastClass = FastClass.create(beanClass);
        FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);
        return fastMethod.invoke(bean, parameters);
    }
}
