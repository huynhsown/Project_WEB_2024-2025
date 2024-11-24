package com.adidark.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParseJson {

    @Autowired
    private ObjectMapper objectMapper;

    public List<String> one(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<List<String>>() {});
    }

}
