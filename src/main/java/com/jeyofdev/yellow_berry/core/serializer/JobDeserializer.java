package com.jeyofdev.yellow_berry.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jeyofdev.yellow_berry.core.enums.JobEnum;

import java.io.IOException;

public class JobDeserializer extends JsonDeserializer<JobEnum> {
    @Override
    public JobEnum deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String value = p.getText().toUpperCase();
        return JobEnum.valueOf(value);
    }
}
