package com.huijia.sharing.core.config;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * @author huijia
 * @date 2024/12/25 15:01
 */
@Configuration
public class JacksonConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 把Long类型序列化为String
            builder.serializerByType(Long.class, ToStringSerializer.instance);
        };
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new CustomDeserializerModule());
//        return objectMapper;
//    }
}
