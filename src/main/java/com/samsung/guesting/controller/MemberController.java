package com.samsung.guesting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.samsung.guesting.dto.LoginReq;
import com.samsung.guesting.dto.MemberReq;
import com.samsung.guesting.dto.MemberRes;
import com.samsung.guesting.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody MemberReq memberReq) {
		MemberRes memberRes = memberService.signUp(memberReq);
		
		return ResponseEntity.status(HttpStatus.OK).body(memberRes);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginReq loginReq, 
								   HttpSession session) {
		MemberRes memberRes = memberService.signIn(loginReq.getId(), loginReq.getPassword());
		
		session.setAttribute("memberId", memberRes.getMemberId());
		System.out.println("JSESSIONID :"+session.getId());
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/members")
	public ResponseEntity<?> getTeamlessMembers(@SessionAttribute(name = "memberId") Integer memberId){
		List<MemberRes> memberResList = memberService.getTeamlessMembers(memberId);
		Map<String, Object> response = new HashMap<>();
		response.put("data", memberResList);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
		
	}
	@GetMapping("/mypage")
	public ResponseEntity<?> myPage(@SessionAttribute(name = "memberId")Integer memberId){
		MemberRes memberRes = memberService.getMyInfo(memberId);
		Map<String, Object> response = new HashMap<>();
		response.put("data", memberRes);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
}
