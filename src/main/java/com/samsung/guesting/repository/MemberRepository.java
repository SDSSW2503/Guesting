package com.samsung.guesting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samsung.guesting.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	// 현재 팀이 없는 유저들만 필터링하여 조회 (1. query method로 구현)
	List<Member> findAllByTeamIsNull();
	
	// 현재 팀이 없는 유저들만 필터링하여 조회 (2. JPQL로 구현)
	@Query(value = "SELECT m FROM Member m WHERE m.team is null")
	List<Member> getMembersByTeamIsNull();
	
	// 멤버아이디로 멤버 찾아오는 메서드
	@Query(value = "SELECT m FROM Member m WHERE m.memberId = :memberId")
	Optional<Member> getByMemberId(@Param("memberId") Integer memberId);

	// 팀아이디로 해당 팀에 속한 멤버 정보 가져오는 메서드
	@Query(value = "SELECT m FROM Member m JOIN FETCH m.team WHERE m.team.teamId = :teamId")
	List<Member> getMembersByTeamId(@Param(value = "teamId") Integer teamId);
}
