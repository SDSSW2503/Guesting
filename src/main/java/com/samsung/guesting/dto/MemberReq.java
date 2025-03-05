package com.samsung.guesting.dto;

import com.samsung.guesting.entity.Member;
import com.samsung.guesting.entity.staticField.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MemberReq {
	
	private String password;
	private String name;
	private String intro;
	private String gender;
	
	public Member toMember() {
		return Member.builder()
				.password(password)
				.name(name)
				.intro(intro)
				.gender(Gender.valueOf(gender))
				.build();
				
	}
}
