package com.kasmooi.invoice.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Utility {
    public static String jsonString(Object value) {
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("Error convert to json string", e);
        }
        return json;
    }
}
