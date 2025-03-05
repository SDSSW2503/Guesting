package com.samsung.guesting.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.samsung.guesting.dto.RegistRes;
import com.samsung.guesting.entity.House;
import com.samsung.guesting.entity.Member;
import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.entity.Team;
import com.samsung.guesting.entity.staticField.Status;
import com.samsung.guesting.exception.CustomException;
import com.samsung.guesting.repository.HouseRepository;
import com.samsung.guesting.repository.MemberRepository;
import com.samsung.guesting.repository.RegistRepository;
import com.samsung.guesting.repository.TeamRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistService {
	private final RegistRepository registRepository;
	private final MemberRepository memberRepository;
	private final TeamRepository teamRepository;
	private final HouseRepository houseRepository;
	
	//1. 게스팅 신청 : 상대 팀 아이디를 받아서 게스팅을 신청한다.
	public Regist requestGuesting(Integer memberId, Integer otherTeamId) {
		Member member = memberRepository.getByMemberId(memberId);
		if (Objects.isNull(member)) throw new RuntimeException("다시 로그인 해주세요.");
		Team curTeam = member.getTeam();
		
		if (Objects.isNull(curTeam)) throw new RuntimeException("팀 가입 후 이용해주세요.");
				
		//팀이 존재하는지
		if(teamRepository.findById(otherTeamId).isEmpty()) {
			throw new CustomException("존재하지 않는 팀입니다.");
		}
		//내가 이미 매칭이 된 상태인지
		if(registRepository.getMatchedTeam(curTeam.getTeamId()).isPresent()) {
			throw new CustomException("이미 팀의 매칭이 완료된 상태입니다.");
		}
		//이미 다른 곳에 요청을 보낸 상태인지
		if(!registRepository.getSentRegists(curTeam.getTeamId()).isEmpty()) {
			throw new CustomException("이미 보낸 매칭 신청이 존재합니다.");
		}
		//상대 팀이 이미 매칭이 된 상태인지
		if(registRepository.getMatchedTeam(otherTeamId).isPresent()) {
			throw new CustomException("이미 매칭이 완료된 팀입니다.");
		}
  
  		//fetch all houses, and select one at random
		House house = houseRepository.findAll()
		.stream()
		.skip(new Random().nextInt((int) houseRepository.count()))
		.findFirst()
		.orElseThrow(()-> new CustomException("empty house DB"));
  
		Regist regist = Regist.builder()
				.sendTeam(Team.builder().teamId(curTeam.getTeamId()).build())
				.receiveTeam(Team.builder().teamId(otherTeamId).build())
				.regDate(LocalDateTime.now())
				.status(Status.PENDING)
        .house(house)
				.build();
		
		return registRepository.save(regist);
	}
	 
	//2. 신청 현황 조회 : 나의 팀이 받은 신청을 조회한다.
	public List<RegistRes> viewReceivedRegistRecords(Integer memberId) {
		//1) session 에서 나의 팀 아이디를 꺼낸다.
		Member member = memberRepository.getByMemberId(memberId);
		if (Objects.isNull(member)) throw new RuntimeException("다시 로그인 해주세요.");
		Team curTeam = member.getTeam();
		
		if (Objects.isNull(curTeam)) throw new RuntimeException("팀 가입 후 이용해주세요.");
		//2) DB를 조회해서 나의 팀이 받은 신청을 모두 꺼낸다.
		List<Regist> registEntities = registRepository.getReceivedRegists(curTeam.getTeamId());
		
		//3) 리스트의 형태로 리턴한다.
		 return registEntities.stream()
	                .map(RegistRes::new)
	                .collect(Collectors.toList());
	}
	
	//2.5. 신청 현황 조회 : 나의 팀이 보낸 신청을 조회한다.
	public List<RegistRes> viewSentRegistRecords(Integer memberId) {
		//1) session 에서 나의 팀 아이디를 꺼낸다.
		Member member = memberRepository.getByMemberId(memberId);
		if (Objects.isNull(member)) throw new RuntimeException("다시 로그인 해주세요.");
		Team curTeam = member.getTeam();
		
		if (Objects.isNull(curTeam)) throw new RuntimeException("팀 가입 후 이용해주세요.");

	  //2) DB를 조회해서 나의 팀이 받은 신청을 모두 꺼낸다.
		List<Regist> registEntities = registRepository.getSentRegists(curTeam.getTeamId());
	
		//3) 리스트의 형태로 리턴한다.
		 return registEntities.stream()
	                .map(RegistRes::new)
	                .collect(Collectors.toList());
	}
	
	//3. 내가 받은 신청 중 하나를 수락한다.
	@Transactional
	public void acceptRegist(Integer registId, Integer memberId) throws RuntimeException{
		Member member = memberRepository.getByMemberId(memberId);
		if (Objects.isNull(member)) throw new RuntimeException("다시 로그인 해주세요.");
		Team curTeam = member.getTeam();
		
		if (Objects.isNull(curTeam)) throw new RuntimeException("팀 가입 후 이용해주세요.");

		Regist regist = registRepository.findById(registId)
				.orElseThrow(()->new RuntimeException("찾을 수 없는 요청입니다."));
		
		if (!Objects.equals(regist.getReceiveTeam().getTeamId(), curTeam.getTeamId()))
			throw new RuntimeException("자신이 소속된 팀의 게스팅만 수락할 수 있습니다..");
		
		//수락으로 바꾸기
		regist.setStatus(Status.ACCEPTED);
		
		//내가 받은 요청 모두 불러오기
		registRepository.getReceivedRegists(curTeam.getTeamId())
				.stream().filter(r->!regist.getRegistId().equals(r.getRegistId())).forEach(r->r.setStatus(Status.DECLINED));
				
		//내가 보낸 요청 모두 삭제하기
		List<Regist> sentRegistList =  registRepository.getSentRegists(curTeam.getTeamId());
		registRepository.deleteAll(sentRegistList);
		
	}
	 
	 
	 //4. 내가 받은 신청 중 하나를 거절한다.
	@Transactional
	public void declineRegist(Integer registId, Integer memberId) throws RuntimeException{
		Member member = memberRepository.getByMemberId(memberId);
		if (Objects.isNull(member)) throw new RuntimeException("다시 로그인 해주세요.");
		Team curTeam = member.getTeam();
		
		if (Objects.isNull(curTeam)) throw new RuntimeException("팀 가입 후 이용해주세요.");
		
		Regist regist = registRepository.findById(registId)
				.orElseThrow(()->new RuntimeException("찾을 수 없는 요청입니다."));
		
		if (!Objects.equals(regist.getReceiveTeam().getTeamId(), curTeam.getTeamId()))
			throw new RuntimeException("자신이 소속된 팀의 게스팅만 거절할 수 있습니다..");

		//거절로 바꾸기
		regist.setStatus(Status.DECLINED);
	}
	 
	 
	 //5. 성사된 매칭을 조회한다.
	public RegistRes getMyMatching (Integer memberId) throws RuntimeException{
		Member member = memberRepository.getByMemberId(memberId);
		if (Objects.isNull(member)) throw new RuntimeException("다시 로그인 해주세요.");
		Team curTeam = member.getTeam();
		
		if (Objects.isNull(curTeam)) throw new RuntimeException("팀 가입 후 이용해주세요.");
		
		//내 팀 아이디를 이용해 성사된 매칭을 조회한다.
		Regist regist = registRepository.getMatchedTeam(curTeam.getTeamId())
				.orElseThrow(() -> new RuntimeException("성사된 매칭이 존재하지 않습니다."));
		return new RegistRes(regist);
	}
}
