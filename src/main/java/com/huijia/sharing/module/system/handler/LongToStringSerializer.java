package com.huijia.sharing.module.system.handler;

/**
 * @author huijia
 * @date 2024/12/25 16:22
 */
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class LongToStringSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value > 9007199254740991L || value < -9007199254740991L) { // 超出精度
            gen.writeString(value.toString());
        } else {
            gen.writeNumber(value);
        }
    }
}