package com.samsung.guesting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.samsung.guesting.dto.RegistRes;
import com.samsung.guesting.service.RegistService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RegistController {
	private final RegistService registService;
	
	@PostMapping("/guestings")
	@Operation(summary = "게스팅 신청", description = "신청하고자 하는 teamId(Integer)를 받아, 게스팅을 신청한다.")
	public ResponseEntity<?> requestGuesting(@RequestBody Integer teamId
										   , @SessionAttribute(name = "memberId") Integer memberId
	) {
		registService.requestGuesting(memberId, teamId);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/receivedRegists")
	@Operation(summary = "내가 받은 신청 현황 조회", description = "내가 받은 신청 현황을 리스트로 조회한다.")
	public ResponseEntity<?> viewReceivedRegistRecords(@SessionAttribute(name = "memberId") Integer memberId) {
		List<RegistRes> registResList = registService.viewReceivedRegistRecords(memberId);
		Map<String, Object> response = new HashMap<>();
		response.put("data", registResList);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/sentRegists")
	@Operation(summary = "내가 보낸 신청 현황 조회", description = "내가 보낸 신청 현황을 리스트로 조회한다.")
	public ResponseEntity<?> viewSentRegistRecords(@SessionAttribute(name = "memberId") Integer memberId) {
		List<RegistRes> registResList = registService.viewSentRegistRecords(memberId);
		Map<String, Object> response = new HashMap<>();
		response.put("data", registResList);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/regists/accept")
	@Operation(summary = "받은 신청 수락", description = "수락할 registId(Integer)를 받아, 해당 Regist를 수락하고 다른 신청들은 전부 거절한다.")
	public ResponseEntity<?> acceptRegist(@RequestBody Integer registId,
										  @SessionAttribute(name = "memberId") Integer memberId
	) {
		registService.acceptRegist(registId, memberId);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@PutMapping("/regists/decline")
	@Operation(summary = "받은 신청 거절", description = "거절할 registId(Integer)를 받아, 해당 Regist를 거절한다.")
	public ResponseEntity<?> declineRegist(@RequestBody Integer registId,
										  @SessionAttribute(name = "memberId") Integer memberId
	) {
		registService.declineRegist(registId, memberId);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/myMatchings")
	@Operation(summary = "내 매칭 조회", description = "현재 매칭된 정보를 조회한다.")
	public ResponseEntity<?> getMyMatching(@SessionAttribute(name = "memberId") Integer memberId) {
		RegistRes registRes = registService.getMyMatching(memberId);
		Map<String, Object> response = new HashMap<>();
		response.put("data", registRes);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

}
