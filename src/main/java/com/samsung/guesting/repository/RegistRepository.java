package com.samsung.guesting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samsung.guesting.entity.Regist;

public interface RegistRepository extends JpaRepository<Regist, Integer>{
	
	//팀이 받은 요청 모두 조회
	@Query(value = "select r from Regist r where r.receiveTeam.teamId = :teamId")
	List<Regist> getReceivedRegists(@Param("teamId") Integer teamId);
	
	//팀이 보낸 요청 모두 조회
	@Query(value = "select r from Regist r where r.sendTeam.teamId = :teamId")
	List<Regist> getSentRegists(@Param("teamId") Integer teamId);
	
	//매칭된 팀 조회
	//0 : accept 1 : decline 2 : pending
	@Query(value = "select r from Regist r where r.status = 0 and (r.sendTeam.teamId = :teamId or receiveTeam.teamId = :teamId)")
	Optional<Regist> getMatchedTeam(Integer teamId);

}
