package com.samsung.guesting.controller;

import java.util.List;

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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RegistController {
	private final RegistService registService;
	
	// 게스팅 신청
	@PostMapping("/guestings")
	public ResponseEntity<?> requestGuesting(@RequestBody Integer teamId
										   , @SessionAttribute(name = "memberId") Integer memberId
	) {
		registService.requestGuesting(memberId, teamId);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	// 내가 받은 신청 현황 조회
	@GetMapping("/receivedRegists")
	public ResponseEntity<?> viewReceivedRegistRecords(@SessionAttribute(name = "memberId") Integer memberId) {
		List<RegistRes> registResList = registService.viewReceivedRegistRecords(memberId);
		
		return ResponseEntity.status(HttpStatus.OK).body(registResList);
	}
	
	// 내가 보낸 신청 현황 조회
	@GetMapping("/sentRegists")
	public ResponseEntity<?> viewSentRegistRecords(@SessionAttribute(name = "memberId") Integer memberId) {
		List<RegistRes> registResList = registService.viewSentRegistRecords(memberId);
		
		return ResponseEntity.status(HttpStatus.OK).body(registResList);
	}
	
	// 받은 신청 수락
	@PutMapping("/regists/accept")
	public ResponseEntity<?> acceptRegist(@RequestBody Integer registId,
										  @SessionAttribute(name = "memberId") Integer memberId
	) {
		registService.acceptRegist(registId, memberId);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	// 받은 신청 거절
	@PutMapping("/regists/decline")
	public ResponseEntity<?> declineRegist(@RequestBody Integer registId,
										  @SessionAttribute(name = "memberId") Integer memberId
	) {
		registService.declineRegist(registId, memberId);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	// 내 매칭 조회
	@GetMapping("/myMatchings")
	public ResponseEntity<?> getMyMatching(@SessionAttribute(name = "memberId") Integer memberId) {
		RegistRes registRes = registService.getMyMatching(memberId);
		
		return ResponseEntity.status(HttpStatus.OK).body(registRes);
	}
	

}
