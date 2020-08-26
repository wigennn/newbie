package com.redbyte.service;

import com.redbyte.annotation.NewbieService;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@NewbieService(value = HelloWorld.class, version = "1.0.0")
public class HelloWorldImpl implements HelloWorld {

    @Override
    public String hello() {
        return "hello world";
    }
}
