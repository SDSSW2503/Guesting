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
	public TeamRes addTeam(TeamReq teamReq) {
		List<Integer> teamMember = List.of(teamReq.getMemberId1(), teamReq.getMemberId2(), teamReq.getMemberId3());
		
        if (teamMember.size() != 3) {
            throw new RuntimeException("인원수가 맞지 않습니다.");
        }
        
        for (Integer memberId : teamMember) {
            Optional<Member> existingMember = memberRepository.findById(memberId);
            if (existingMember.isPresent() && existingMember.get().getTeam() != null) {
                throw new RuntimeException("이미 팀이 있는 멤버를 포함합니다.");
            }
        }
        
		Team team = teamReq.toTeam(teamReq);
		teamRepository.save(team);
		
		return new TeamRes(team);
	}
	
	//2. 매칭 안된 팀 목록 조회
	public List<TeamRes> getNoTeams() throws Exception{
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
