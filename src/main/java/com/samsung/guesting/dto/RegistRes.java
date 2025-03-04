package com.samsung.guesting.dto;

import java.time.LocalDateTime;

import com.samsung.guesting.entity.Regist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RegistRes {
	private Integer registId;
	private TeamRes sendTeam;
	private TeamRes receiveTeam;
	private LocalDateTime regDate;
	private Integer status;
	private HouseRes house;
	
	public RegistRes(Regist regist) {
	    this.registId = regist.getRegistId();
	    this.sendTeam = new TeamRes(regist.getSendTeam());
	    this.receiveTeam = new TeamRes(regist.getReceiveTeam());
	    this.regDate = regist.getRegDate();
	    this.house = new HouseRes(regist.getHouse());
	    this.status = regist.getStatus();
	}
}
