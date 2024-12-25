package com.huijia.sharing.module.system.handler;

/**
 * @author huijia
 * @date 2024/12/25 16:40
 */
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class LongDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid long value: " + value);
        }
    }
}

