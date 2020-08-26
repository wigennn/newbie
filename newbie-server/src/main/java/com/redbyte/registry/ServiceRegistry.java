package com.redbyte.registry;

import com.redbyte.zookeeper.ZkClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@Slf4j
public class ServiceRegistry {

    private List<String> pathList = new ArrayList<>();

    private ZkClient zkClient;

    public ServiceRegistry(String registryAddress) {
        zkClient = new ZkClient(registryAddress);
    }

    /**
     * 注册服务
     */
    public void registerService(String host, Integer port, Map<String, Object> serviceBeanMap) {

    }

    /**
     * 注销服务
     */
    public void unregisterService() {

    }
}
