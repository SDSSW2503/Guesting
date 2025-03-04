package com.samsung.guesting.dto;

import com.samsung.guesting.entity.House;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HouseReq {
	private String name;
	private String addr;
	
	public House toHouse() {
		return House.builder()
				.name(name)
				.addr(addr)
				.build();
	}
}
