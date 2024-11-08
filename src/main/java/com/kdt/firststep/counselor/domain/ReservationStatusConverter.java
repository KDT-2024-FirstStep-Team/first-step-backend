package com.kdt.firststep.counselor.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class ReservationStatusConverter implements AttributeConverter<ReservationStatus, String> {

    @Override
    public String convertToDatabaseColumn(ReservationStatus attribute) {
        return attribute != null ? attribute.getStatus() : null;
    }

    @Override
    public ReservationStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Arrays.stream(ReservationStatus.values())
                .filter(status -> status.getStatus().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown database value:" + dbData));
    }
}