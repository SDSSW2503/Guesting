package com.samsung.guesting.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.samsung.guesting.dto.MemberReq;
import com.samsung.guesting.dto.MemberRes;
import com.samsung.guesting.entity.Member;
import com.samsung.guesting.exception.AuthException;
import com.samsung.guesting.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	// 회원가입
	@Transactional
	public MemberRes signUp(MemberReq memberReq) {
		Member member = memberRepository.save(memberReq.toMember());
		return new MemberRes(member);
	}
	
	// 로그인
	public MemberRes signIn(Integer memberId, String password) {
		Member member = memberRepository.getByMemberId(memberId).orElseThrow(()-> new AuthException("일치하는 아이디가 없습니다."));
		if (!password.equals(member.getPassword()))
			throw new RuntimeException("틀린 비밀번호입니다.");
		return new MemberRes(member);
	}
	
	// 마이페이지 조회
	public MemberRes getMyInfo(Integer memberId) {
		Member member = memberRepository.getByMemberId(memberId).orElseThrow(()-> new AuthException("다시 로그인 해주세요."));
		
		if (Objects.isNull(member.getTeam())) {
			return new MemberRes(member);
		} else {
			List<Member> sameTeamMemberList = memberRepository.getMembersByTeamId(member.getTeam().getTeamId())
					.stream().filter(m -> !memberId.equals(m.getMemberId())).toList();
			return new MemberRes(member, sameTeamMemberList);
		}
	}
	
	//이도건 추가, 0304
	//get List of members without a team
	public List<MemberRes> getTeamlessMembers(Integer myId){
		return memberRepository.getMembersByTeamIsNull()
				.stream()
				.filter(member -> !member.getMemberId().equals(myId))
				.map(MemberRes::new)
				.collect(Collectors.toList());
	}

}
