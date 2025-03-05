package com.samsung.guesting.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// 미로그인 상태와 같은, 인증/인가 예외
	@ExceptionHandler(value = AuthException.class)
	public ProblemDetail authExceptionHandle(AuthException e) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(e.getHttpStatus().value());
		problemDetail.setDetail(e.getMessage());
		problemDetail.setProperty("timestamp", LocalDateTime.now());
		return problemDetail;		
	}
	
	// 비즈니스로직에서 탐지한 예외
	@ExceptionHandler(value = BusinessException.class)
	public ProblemDetail businessExceptionHandle(BusinessException e) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(e.getHttpStatus().value());
		problemDetail.setDetail(e.getMessage());
		problemDetail.setProperty("timestamp", LocalDateTime.now());
		return problemDetail;		
	}
	
	
	// 이외의 모든 서버 에러
	@ExceptionHandler(value = Exception.class)
	public ProblemDetail boardSearchExceptionHandle(Exception e) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setDetail(e.getMessage());
		problemDetail.setProperty("timestamp", LocalDateTime.now());
		return problemDetail;		
	}

}
