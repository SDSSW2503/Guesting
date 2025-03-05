package com.samsung.guesting.dto;

import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.entity.Team;
import com.samsung.guesting.entity.staticField.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistReq {
	private Integer sendTeamId;
	private Integer receiveTeamId;	
	
	public Regist toRegist() {
		return Regist.builder()
				.sendTeam(Team.builder().teamId(sendTeamId).build())
				.receiveTeam(Team.builder().teamId(receiveTeamId).build())
				.status(Status.PENDING)
				.build();
	}
}
