package com.huijia.sharing.module.system.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author huijia
 * @date 2024/12/21 11:21
 */
public class CustomLongSerializer extends JsonSerializer<Long> {

    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (value != null && Math.abs(value) > MAX_SAFE_INTEGER) {
            // 超过 JavaScript 最大安全整数，转为 String
            gen.writeString(value.toString());
        } else {
            // 在精度范围内，保持 Long 类型
            gen.writeNumber(value);
        }
    }
}
