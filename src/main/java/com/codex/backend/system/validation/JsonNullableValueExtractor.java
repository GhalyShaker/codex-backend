package com.codex.backend.system.validation;

import org.openapitools.jackson.nullable.JsonNullable;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;

public class JsonNullableValueExtractor
        implements ValueExtractor<JsonNullable<@ExtractedValue ?>> {

    @Override
    public void extractValues(
            JsonNullable<?> value,
            ValueReceiver receiver) {

        if (value != null && value.isPresent()) {
            receiver.value(null, value.get());
        }
    }
}
