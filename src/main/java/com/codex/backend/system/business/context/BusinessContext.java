package com.codex.backend.system.business.context;

import java.util.HashMap;
import java.util.Map;

public class BusinessContext {

    private final Object target;

    private final String operation;

    private final Map<String, Object> attributes;

    public BusinessContext(
            Object target,
            String operation) {

        this.target = target;
        this.operation = operation;
        this.attributes = new HashMap<>();

    }

    public Object target() {

        return target;

    }

    public String operation() {

        return operation;

    }

    public Map<String, Object> attributes() {

        return attributes;

    }

    public <T> void put(
            String key,
            T value) {

        attributes.put(key, value);

    }

    @SuppressWarnings("unchecked")
    public <T> T get(
            String key) {

        return (T) attributes.get(key);

    }

}
