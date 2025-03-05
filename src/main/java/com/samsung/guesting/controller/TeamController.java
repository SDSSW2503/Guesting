package com.samsung.guesting.controller;

import java.util.HashMap;
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

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
public class TeamController {
	
	final TeamService teamService;
	
	@PostMapping("/teams")
	@Operation(summary = "팀 생성", description = "teamName(String), memberId1(Integer), memberId2(Integer), memberId3(Integer)를 받아, 팀을 생성한다.")
	public ResponseEntity<?> createTeams(@RequestBody TeamReq teamReq, @SessionAttribute(name = "memberId") Integer memberId){
		TeamRes teamRes = teamService.addTeam(teamReq, memberId);
		Map<String, Object> response = new HashMap<>();
		response.put("data", teamRes);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/teams")
	@Operation(summary = "매칭 안된 팀 조회", description = "매칭 안된 팀 목록을 리스트로 조회한다.")
	public ResponseEntity<?> getNoMatchedTeams() throws Exception{
		List<TeamRes> teams = teamService.getNoTeams();
		if(teams.isEmpty())
			return new ResponseEntity<>(Map.of("message", "매칭이 되지 않은 팀이 없습니다."),HttpStatus.NO_CONTENT);
		Map<String, Object> response = new HashMap<>();
		response.put("data", teams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
