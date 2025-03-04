package com.samsung.guesting.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.samsung.guesting.dto.RegistReq;
import com.samsung.guesting.dto.RegistRes;
import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.entity.Team;
import com.samsung.guesting.repository.MemberRepository;
import com.samsung.guesting.repository.RegistRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistService {
	private final RegistRepository registRepository;
	private final MemberRepository memberRepository;
	private final HttpSession session;
	
	//1. 게스팅 신청 : 상대 팀 아이디를 받아서 게스팅을 신청한다.
	public Regist requestGuesting(RegistReq registReq, Integer curTeamId) {
		registReq.setSendTeamId(curTeamId); 
		
		//TODO : Add error checking mechanism (cannot request to more than one team)
		
		return registRepository.save(registReq.toRegist());
	}
	 
	
	//2. 신청 현황 조회 : 나의 팀이 받은 신청과 나의 팀이 보낸 신청을 조회한다.
	public List<RegistRes> viewReceivedRegistRecords() {
		//1) session 에서 나의 팀 아이디를 꺼낸다.
			Integer curTeamId = (Integer)session.getAttribute("curTeamId");
		//2) DB를 조회해서 나의 팀이 받은 신청을 모두 꺼낸다.
			List<Regist> registEntities = registRepository.getReceivedRegists(curTeamId);
		
		//3) 리스트의 형태로 리턴한다.
			 return registEntities.stream()
		                .map(RegistRes::new)
		                .collect(Collectors.toList());
	}
	
	//2.5. 신청 현황 조회 : 나의 팀이 보낸 신청을 조회한다.
	public List<RegistRes> viewSentRegistRecords(RegistReq registReq) {
		//1) session 에서 나의 팀 아이디를 꺼낸다.
		Integer curTeamId = (Integer)session.getAttribute("curTeamId");
		//2) DB를 조회해서 나의 팀이 받은 신청을 모두 꺼낸다.
		List<Regist> registEntities = registRepository.getSentRegists(curTeamId);
	
		//3) 리스트의 형태로 리턴한다.
		 return registEntities.stream()
	                .map(RegistRes::new)
	                .collect(Collectors.toList());
	}
	
	//3. 내가 받은 신청 중 하나를 수락한다.
	@Transactional
	public void acceptRegist(RegistReq registReq, Integer curTeamId) throws RuntimeException{
		Regist regist = registRepository.findById(registReq.getSendTeamId())
				.orElseThrow(()->new RuntimeException("찾을 수 없는 요청입니다."));
		
		//수락으로 바꾸기
		regist.setStatus(0);
		
		//내가 받은 요청 모두 불러오기
		registRepository.getReceivedRegists(curTeamId)
				.stream().filter(r->!regist.getRegistId().equals(r.getRegistId())).forEach(r->r.setStatus(1));
				
		//내가 보낸 요청 모두 삭제하기
		List<Regist> sentRegistList =  registRepository.getSentRegists(curTeamId);
		registRepository.deleteAll(sentRegistList);
		
	}
	 
	 
	 //4. 내가 받은 신청 중 하나를 거절한다.
	@Transactional
	public void declineRegist(RegistReq registReq, Integer curTeamId) throws RuntimeException{
		Regist receivedRegist = registRepository.findById(registReq.getSendTeamId())
				.orElseThrow(()->new RuntimeException("찾을 수 없는 요청입니다."));
		//거절로 바꾸기
		receivedRegist.setStatus(1);
	

	}
	 
	 
	 //5. 성사된 매칭을 조회한다.
}
