package com.samsung.guesting.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.samsung.guesting.dto.TeamReq;
import com.samsung.guesting.dto.TeamRes;
import com.samsung.guesting.entity.Member;
import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.entity.Team;
import com.samsung.guesting.exception.BusinessException;
import com.samsung.guesting.repository.MemberRepository;
import com.samsung.guesting.repository.RegistRepository;
import com.samsung.guesting.repository.TeamRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
	private final TeamRepository teamRepository;
	private final RegistRepository registRepository;
	private final MemberRepository memberRepository;
	
	//1. 팀 생성
	@Transactional
	public TeamRes addTeam(TeamReq teamReq, Integer leaderId) {
		List<Integer> teamMember = List.of(teamReq.getMemberId1(), teamReq.getMemberId2(), teamReq.getMemberId3(), leaderId);
		
        if (teamMember.size() != 4) {
            throw new BusinessException("인원수가 맞지 않습니다.");
        }
        
        Team team = Team.builder()
        		.name(teamReq.getTeamName())
        		.build();
        teamRepository.save(team);
        
        teamMember.forEach(memberId -> {
        	Member member = memberRepository.findById(memberId)
        			.orElseThrow(() -> new BusinessException("존재하지 않는 아이디의 멤버입니다."));
            if (member.getTeam() != null) 
                throw new BusinessException("이미 팀이 있는 멤버를 포함합니다.");
            member.setTeam(team);
        });
        
		return new TeamRes(team);
	}
	
	//2. 매칭 안된 팀 목록 조회
	public List<TeamRes> getNoTeams() {
		List<Team> allTeams = teamRepository.findAll();
		
		List<Team> matchedTeams = allTeams.stream()
				.filter(team->isTeamMatched(team.getTeamId()))  // TODO: 여기 코드말고, JPQL 한번에 가능할 듯함..?!
				.collect(Collectors.toList());
		
		allTeams.removeAll(matchedTeams);
		
		return allTeams.stream().map(team -> {
			List<Member> memberList = memberRepository.getMembersByTeamId(team.getTeamId());
			return new TeamRes(team, memberList);
		}).collect(Collectors.toList());
	}
	
	private boolean isTeamMatched(Integer teamId) {
		Optional<Regist> matchedTeam = registRepository.getMatchedTeam(teamId);
        return matchedTeam.isPresent();
    }
}
