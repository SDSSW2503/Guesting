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
	
	private String name;
	
	public Team toTeam(TeamReq teamReq) {
		return Team.builder()
				   .name(teamReq.getName())
				   .build();
	}
}
