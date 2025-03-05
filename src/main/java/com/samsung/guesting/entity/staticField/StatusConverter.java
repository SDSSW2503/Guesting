package com.samsung.guesting.entity.staticField;

import jakarta.persistence.AttributeConverter;

public class StatusConverter implements AttributeConverter<Status, Integer> {
	
	@Override
	public Integer convertToDatabaseColumn(Status attribute) {
		if(attribute == null) {
            return null;
        }
        return attribute.getDbValue();
	}

	@Override
	public Status convertToEntityAttribute(Integer dbData) {
		if(dbData == null){
            return null;
        }
        return Status.enumOf(dbData);
	}

}
