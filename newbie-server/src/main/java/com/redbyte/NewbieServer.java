package com.redbyte;

import com.redbyte.annotation.NewbieService;
import com.redbyte.core.NettyServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
public class NewbieServer extends NettyServer implements ApplicationContextAware, InitializingBean, DisposableBean {

    public NewbieServer(String serverAddress, String registryAddress) {
        super(serverAddress, registryAddress);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(NewbieService.class);
        serviceBeanMap.values().stream().forEach(bean -> {
            NewbieService newbieService = bean.getClass().getAnnotation(NewbieService.class);
            String serviceName = newbieService.value().getName();
            String version = newbieService.version();
            super.addService(serviceName, version, bean);
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.start();
    }

    @Override
    public void destroy() throws Exception {
        super.stop();
    }

}
