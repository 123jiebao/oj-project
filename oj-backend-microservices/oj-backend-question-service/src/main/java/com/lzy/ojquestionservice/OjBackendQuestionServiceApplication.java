package com.lzy.ojquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;

@SpringBootApplication()
@MapperScan("com.lzy.ojquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.lzy")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lzy.ojserviceclient.service"})
public class OjBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OjBackendQuestionServiceApplication.class);
        app.setDefaultProperties(
                Collections.singletonMap("spring.config.location", "classpath:/application.yml")
        );
        app.run(args);
    }

}
