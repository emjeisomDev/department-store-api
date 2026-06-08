package com.departmentstore.api.infrastructure.persistence.converter;

import com.departmentstore.api.domain.enums.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Character> {

    @Override
    public Character convertToDatabaseColumn(Gender attribute) {

        if (attribute == null) {
            return null;
        }

        return switch (attribute) {
            case FEMALE -> 'F';
            case MALE -> 'M';
            case OTHER -> 'O';
            case NOT_INFORMED -> 'N';
        };
    }

    @Override
    public Gender convertToEntityAttribute(Character dbData) {

        if (dbData == null) {
            return null;
        }

        return switch (dbData) {
            case 'F' -> Gender.FEMALE;
            case 'M' -> Gender.MALE;
            case 'O' -> Gender.OTHER;
            case 'N' -> Gender.NOT_INFORMED;
            default -> throw new IllegalArgumentException("Invalid gender: " + dbData);
        };
    }

}
