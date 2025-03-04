package com.samsung.guesting.dto;

import com.samsung.guesting.entity.Member;

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
	private Integer gender;
	
	public Member toMember() {
		return Member.builder()
				.password(password)
				.name(name)
				.intro(intro)
				.gender(gender)
				.build();
				
	}
}
