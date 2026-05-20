package com.departmentstore.api.infrastructure.persistence.converter;

import com.departmentstore.api.domain.enums.PersonType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PersonTypeConverter implements AttributeConverter<PersonType, String> {
    @Override
    public String convertToDatabaseColumn(final PersonType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public PersonType convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }
        return PersonType.fromCode(dbData);
    }

}
