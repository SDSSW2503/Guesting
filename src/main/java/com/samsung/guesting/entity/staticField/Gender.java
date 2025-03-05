package com.samsung.guesting.entity.staticField;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
	
	FEMALE(0, "여성", "Female"),
	MALE(1, "남성", "Male")
	;
	
	private Integer dbValue;
	private String koreanValue;
	private String englishValue;
	
	public static Gender enumOf(Integer dbValue) {
		return Arrays.stream(Gender.values())
				.filter(g -> g.getDbValue().equals(dbValue))
				.findAny().orElse(null);
	}

	public static Gender enumOf(String koreanValue) {
		return Arrays.stream(Gender.values())
				.filter(g -> g.getKoreanValue().equals(koreanValue))
				.findAny().orElse(null);
	}
}
