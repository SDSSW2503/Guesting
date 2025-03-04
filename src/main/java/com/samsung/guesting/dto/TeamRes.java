package com.samsung.guesting.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.samsung.guesting.entity.Member;
import com.samsung.guesting.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamRes {
	private Integer teamId;
	private String name;
	private List<MemberRes> memberResList;
	
	public TeamRes(Team team) {
        teamId = team.getTeamId();
        name = team.getName();
   }

	public TeamRes(Team team, List<Member> memberList) {
        teamId = team.getTeamId();
        name = team.getName();
        memberResList = memberList.stream()
        		.map(MemberRes::new)
        		.collect(Collectors.toList());
   }
}
