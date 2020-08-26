package com.redbyte.server;

import com.redbyte.NewbieServer;
import com.redbyte.service.HelloWorld;
import com.redbyte.service.HelloWorldImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@SpringBootApplication
public class ServerTest {

    public static void main(String[] args) {

        SpringApplication.run(ServerTest.class, args);

        HelloWorld helloWorld = new HelloWorldImpl();
        NewbieServer newbieServer = new NewbieServer("127.0.0.1:8080", "127.0.0.1:2181");
        newbieServer.addService(helloWorld.getClass().getName(), "", helloWorld);

        try {
            newbieServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
