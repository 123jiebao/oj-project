package com.lzy.oj.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Spring MVC Json 配置
 *
 */
@JsonComponent
public class JsonConfig {

    /**
     * 添加 Long 转 json 精度丢失的配置
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        // 创建 ObjectMapper 对象
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // 创建 SimpleModule 对象
        SimpleModule module = new SimpleModule();
        // 添加 Long 类型的序列化器
        module.addSerializer(Long.class, ToStringSerializer.instance);
        // 添加 Long 类型的基本类型的序列化器
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // 注册 SimpleModule 对象
        objectMapper.registerModule(module);
        // 返回 ObjectMapper 对象
        return objectMapper;
    }
}