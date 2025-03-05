package com.samsung.guesting.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.samsung.guesting.dto.RegistReq;
import com.samsung.guesting.dto.RegistRes;
import com.samsung.guesting.entity.House;
import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.entity.Team;
import com.samsung.guesting.exception.CustomException;
import com.samsung.guesting.repository.HouseRepository;
import com.samsung.guesting.repository.MemberRepository;
import com.samsung.guesting.repository.RegistRepository;
import com.samsung.guesting.repository.TeamRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistService {
	private final RegistRepository registRepository;
	private final MemberRepository memberRepository;
	private final TeamRepository teamRepository;
	private final HttpSession session;
	private final HouseRepository houseRepository;
	
	//1. 게스팅 신청 : 상대 팀 아이디를 받아서 게스팅을 신청한다.
	public Regist requestGuesting(Integer receiveTeamId, Integer curTeamId) {
		RegistReq registReq = RegistReq.builder()
				.receiveTeamId(receiveTeamId)
				.sendTeamId(curTeamId)
				.build();
		
		//TODO : Add error checking mechanism (cannot request to more than one team)
		//팀이 존재하는지
		if(teamRepository.findById(receiveTeamId).isEmpty()) {
			throw new CustomException("존재하지 않는 팀입니다.");
		}
		//내가 이미 매칭이 된 상태인지
		if(registRepository.getMatchedTeam(curTeamId).isPresent()) {
			throw new CustomException("이미 팀의 매칭이 완료된 상태입니다.");
		}
		//이미 다른 곳에 요청을 보낸 상태인지
		if(!registRepository.getSentRegists(curTeamId).isEmpty()) {
			throw new CustomException("이미 보낸 매칭 신청이 존재합니다.");
		}
		//상대 팀이 이미 매칭이 된 상태인지
		if(registRepository.getMatchedTeam(registReq.getReceiveTeamId()).isPresent()) {
			throw new CustomException("이미 매칭이 완료된 팀입니다.");
		}
		//fetch all houses, and select one at random
		House house = houseRepository.findAll()
		.stream()
		.skip(new Random().nextInt((int) houseRepository.count()))
		.findFirst()
		.orElseThrow(()-> new CustomException("empty house DB"));
		
		return registRepository.save(registReq.toRegist(house));
	}
	 
	
	//2. 신청 현황 조회 : 나의 팀이 받은 신청과 나의 팀이 보낸 신청을 조회한다.
	public List<RegistRes> viewReceivedRegistRecords(Integer curTeamId) {

		//2) DB를 조회해서 나의 팀이 받은 신청을 모두 꺼낸다.
			List<Regist> registEntities = registRepository.getReceivedRegists(curTeamId);
		
		//3) 리스트의 형태로 리턴한다.
			 return registEntities.stream()
		                .map(RegistRes::new)
		                .collect(Collectors.toList());
	}
	
	//2.5. 신청 현황 조회 : 나의 팀이 보낸 신청을 조회한다.
	public List<RegistRes> viewSentRegistRecords(Integer curTeamId) {
		//2) DB를 조회해서 나의 팀이 받은 신청을 모두 꺼낸다.
		List<Regist> registEntities = registRepository.getSentRegists(curTeamId);
	
		//3) 리스트의 형태로 리턴한다.
		 return registEntities.stream()
	                .map(RegistRes::new)
	                .collect(Collectors.toList());
	}
	
	//3. 내가 받은 신청 중 하나를 수락한다.
	@Transactional
	public void acceptRegist(Integer registId, Integer curTeamId) throws RuntimeException{
		
		//내가 이미 매칭이 된 상태인지
		if(registRepository.getMatchedTeam(curTeamId).isPresent()) {
			throw new CustomException("이미 팀의 매칭이 완료된 상태입니다.");
		}

		Regist regist = registRepository.findById(registId)
				.orElseThrow(()->new RuntimeException("찾을 수 없는 요청입니다."));
		
		//수락으로 바꾸기
		regist.setStatus(0);
		
		//내가 받은 요청 모두 불러오기 후 거절
		registRepository.getReceivedRegists(curTeamId)
				.stream().filter(r->!regist.getRegistId().equals(r.getRegistId())).forEach(r->r.setStatus(1));
				
		//내가 보낸 요청 모두 삭제하기
		List<Regist> sentRegistList =  registRepository.getSentRegists(curTeamId);
		registRepository.deleteAll(sentRegistList);
		
	}
	 
	 
	 //4. 내가 받은 신청 중 하나를 거절한다.
	@Transactional
	public void declineRegist(Integer registReqId, Integer curTeamId) throws RuntimeException{
		Regist receivedRegist = registRepository.findById(registReqId)
				.orElseThrow(()->new CustomException("찾을 수 없는 요청입니다."));
		//거절로 바꾸기
		receivedRegist.setStatus(1);
	}
	 
	 
	 //5. 성사된 매칭을 조회한다.
	public Regist getMyMatching (Integer curTeamId) throws RuntimeException{
		//내 팀 아이디를 이용해 성사된 매칭을 조회한다.
		Regist regist = registRepository.getMatchedTeam(curTeamId).orElseThrow(
				() -> new CustomException("성사된 매칭이 없습니다"));
		return regist;
	}
}
