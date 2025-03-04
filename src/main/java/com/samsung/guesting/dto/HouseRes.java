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
public class HouseRes {
	private Integer houseId;
	private String name;
	private String addr;
	
	public HouseRes(House house) {
        houseId = house.getHouseId();
        name=house.getName();
        addr=house.getAddr();
   }
	
}
