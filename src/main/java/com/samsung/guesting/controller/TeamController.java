package com.samsung.guesting.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.samsung.guesting.dto.TeamReq;
import com.samsung.guesting.dto.TeamRes;
import com.samsung.guesting.entity.Member;
import com.samsung.guesting.entity.Team;
import com.samsung.guesting.service.TeamService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
public class TeamController {
	
	final TeamService teamService;
	
	//createTeams..팀생성
	@PostMapping("/teams")
	public ResponseEntity<?> createTeams(@RequestBody TeamReq teamReq, @SessionAttribute(name = "memberId") Integer memberId){
		TeamRes teamRes = teamService.addTeam(teamReq, memberId);
		return new ResponseEntity<>(teamRes, HttpStatus.OK);
	}
	
	
	//showTeams .. 매칭안된팀 조회
	@GetMapping("/teams")
	public ResponseEntity<?> getNoMatchedTeams() throws Exception{
		List<TeamRes> teams = teamService.getNoTeams();
		if(teams.isEmpty())
			return new ResponseEntity<>(Map.of("message", "매칭이 되지 않은 팀이 없습니다."),HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(teams, HttpStatus.OK);
	}
	
}
