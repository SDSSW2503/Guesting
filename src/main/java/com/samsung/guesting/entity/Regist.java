package com.samsung.guesting.entity;

import java.time.LocalDateTime;

import com.samsung.guesting.entity.staticField.Status;
import com.samsung.guesting.entity.staticField.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Regist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "regist_id")
	private Integer registId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_team_id", nullable = false)
    private Team sendTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_team_id", nullable = false)
    private Team receiveTeam;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "status", nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

	@Override
	public String toString() {
		return "Regist [registId=" + registId + ", sendTeam=" + sendTeam.getTeamId() + ", receiveTeam=" + receiveTeam.getTeamId() + ", regDate="
				+ regDate + ", status=" + status + ", house=" + house.getHouseId() + "]";
	}
    
    
}
