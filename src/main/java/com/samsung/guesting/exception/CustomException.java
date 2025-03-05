package com.samsung.guesting.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO: 삭제 예정
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
	private String message;
	private HttpStatus httpStatus;
	
	public CustomException(String message) {
		this.message = message;
		this.httpStatus = HttpStatus.FORBIDDEN;
	}
}
