package com.samsung.guesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.samsung.guesting.dto.MemberRes;
import com.samsung.guesting.repository.MemberRepository;
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
	
	@Override
	public void run(String... args) throws Exception {
		MemberRes res = memberService.getMyInfo(1);
		System.out.println(res.getTeamRes());
		
	}

}
