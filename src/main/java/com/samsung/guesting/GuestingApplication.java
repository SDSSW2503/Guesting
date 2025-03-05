package com.samsung.guesting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.samsung.guesting.dto.MemberRes;
import com.samsung.guesting.dto.RegistReq;
import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.repository.MemberRepository;
import com.samsung.guesting.repository.RegistRepository;
import com.samsung.guesting.service.MemberService;
import com.samsung.guesting.service.RegistService;

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
	
	@Autowired 
	private RegistService registService;
	
	@Override
	public void run(String... args) throws Exception {
			
		
	}

}
