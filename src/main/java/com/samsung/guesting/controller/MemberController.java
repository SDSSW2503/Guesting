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

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@PostMapping("/signUp")
	@Operation(summary = "회원가입", description = "password(String), name(String), intro(String), gender(여성 or 남성)를 받아, 회원가입한다.")
	public ResponseEntity<?> signUp(@RequestBody MemberReq memberReq) {
		MemberRes memberRes = memberService.signUp(memberReq);
		
		return ResponseEntity.status(HttpStatus.OK).body(memberRes);
	}
	
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "id(Integer)와 password(String)를 받아, 로그인한다. (세션에 memberId 저장됨)")
	public ResponseEntity<?> login(@RequestBody LoginReq loginReq, 
								   HttpSession session) {
		MemberRes memberRes = memberService.signIn(loginReq.getId(), loginReq.getPassword());
		
		session.setAttribute("memberId", memberRes.getMemberId());
		System.out.println("JSESSIONID :"+session.getId());
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/members")
	@Operation(summary = "(팀에 소속되지 않은) 멤버목록 조회", description = "팀에 소속되지 않은 멤버목록(본인 제외)을 조회한다.")
	public ResponseEntity<?> getTeamlessMembers(@SessionAttribute(name = "memberId") Integer memberId){
		List<MemberRes> memberResList = memberService.getTeamlessMembers(memberId);
		Map<String, Object> response = new HashMap<>();
		response.put("data", memberResList);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
		
	}
	@GetMapping("/mypage")
	@Operation(summary = "마이페이지 조회", description = "멤버아이디, 이름, 자기소개, 성별, 팀정보를 조회한다.")
	public ResponseEntity<?> myPage(@SessionAttribute(name = "memberId")Integer memberId){
		MemberRes memberRes = memberService.getMyInfo(memberId);
		Map<String, Object> response = new HashMap<>();
		response.put("data", memberRes);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
}
