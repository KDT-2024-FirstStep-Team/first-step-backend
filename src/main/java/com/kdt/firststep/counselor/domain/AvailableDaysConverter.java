package com.kdt.firststep.counselor.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class AvailableDaysConverter implements AttributeConverter<AvailableDays, String> {

    @Override
    public String convertToDatabaseColumn(AvailableDays attribute) {
        return attribute != null ? attribute.getDay() : null;
    }

    @Override
    public AvailableDays convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Arrays.stream(AvailableDays.values())
                .filter(e -> e.getDay().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown database value:" + dbData));
    }
}

