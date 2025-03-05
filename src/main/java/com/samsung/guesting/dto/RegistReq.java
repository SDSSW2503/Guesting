package com.samsung.guesting.dto;

import java.time.LocalDateTime;

import com.samsung.guesting.entity.House;
import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RegistReq {
	private Integer sendTeamId;
	private Integer receiveTeamId;
	
	public Regist toRegist(House house) {
		return Regist.builder()
				.sendTeam(Team.builder().teamId(sendTeamId).build())
				.receiveTeam(Team.builder().teamId(receiveTeamId).build())
				.status(2)
				.regDate(LocalDateTime.now())
				.house(house)
				.build();
	}
}
