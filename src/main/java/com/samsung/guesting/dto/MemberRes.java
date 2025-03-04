package com.samsung.guesting.dto;


import java.util.List;

import com.samsung.guesting.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRes {

	private Integer memberId;
	private String name;
	private String intro;
	private Integer gender;
	private TeamRes teamRes;
	
	public MemberRes(Member member) {
		memberId = member.getMemberId();
		name = member.getName();
		intro = member.getIntro();
		gender = member.getGender();
		teamRes = new TeamRes(member.getTeam());
	}
	
	public MemberRes(Member member, List<Member> memberList) {
		memberId = member.getMemberId();
		name = member.getName();
		intro = member.getIntro();
		gender = member.getGender();
		teamRes = new TeamRes(member.getTeam(), memberList);
	}
}
