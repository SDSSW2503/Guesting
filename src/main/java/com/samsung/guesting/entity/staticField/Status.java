package com.samsung.guesting.entity.staticField;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
	
	ACCEPTED(0, "수락됨", "Accepted"),
	DECLINED(1, "거절됨", "Declined"),
	PENDING(2, "대기 중", "Pending")
	;
	
	private Integer dbValue;
	private String koreanValue;
	private String englishValue;

	public static Status enumOf(Integer dbValue) {
		return Arrays.stream(Status.values())
				.filter(g -> g.getDbValue().equals(dbValue))
				.findAny().orElse(null);
	}
}
