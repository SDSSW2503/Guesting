package com.samsung.guesting.dto;

import com.samsung.guesting.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamReq {
	
	private String teamName;
	private Integer memberId1;
	private Integer memberId2;
	private Integer memberId3;
	
	public Team toTeam(TeamReq teamReq) {
		return Team.builder()
				   .name(teamReq.getTeamName())
				   .build();
	}
}
