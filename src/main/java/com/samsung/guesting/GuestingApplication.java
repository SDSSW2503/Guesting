package com.samsung.guesting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.samsung.guesting.dto.MemberRes;
import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.repository.MemberRepository;
import com.samsung.guesting.repository.RegistRepository;
import com.samsung.guesting.service.MemberService;

@SpringBootApplication
public class GuestingApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(GuestingApplication.class, args);
	}

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private RegistRepository registRepository;
	
	@Override
	public void run(String... args) throws Exception {
		/*RegistRepository Test*/
		
		//getReceivedRegists : 팀이 받은 요청 모두 조회
		registRepository.getReceivedRegists(1)
		.stream().forEach(System.out::println);
		
		
		//getSentRegists : 팀이 보낸 요청 모두 조회
		registRepository.getSentRegists(2)
		.stream().forEach(System.out::println);
		
		//getMatchedTeam : 매칭된 팀 조회
		//need mechanism to prevent
			//1. multiple requests coming from me
			//2. multiple matchings
		System.out.println("Matched Team : ");
		registRepository.getMatchedTeam(1) 
		.stream().forEach(System.out::println);
	}

}
