package com.samsung.guesting.entity.staticField;

import jakarta.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<Gender, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Gender attribute) {
		if(attribute == null) {
            return null;
        }
        return attribute.getDbValue();
	}

	@Override
	public Gender convertToEntityAttribute(Integer dbData) {
		if(dbData == null){
            return null;
        }
        return Gender.enumOf(dbData);
	}

}
