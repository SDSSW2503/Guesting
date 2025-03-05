package com.samsung.guesting;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.samsung.guesting.controller.MemberController;
import com.samsung.guesting.dto.LoginReq;
import com.samsung.guesting.dto.MemberRes;
import com.samsung.guesting.dto.RegistReq;
import com.samsung.guesting.entity.Regist;
import com.samsung.guesting.repository.MemberRepository;
import com.samsung.guesting.repository.RegistRepository;
import com.samsung.guesting.service.MemberService;
import com.samsung.guesting.service.RegistService;

import jakarta.servlet.http.HttpSession;

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
