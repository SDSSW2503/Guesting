package com.samsung.guesting.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthException extends RuntimeException {
	private String message;
	private HttpStatus httpStatus;
	
	public AuthException(String message) {
		this.message = message;
		this.httpStatus = HttpStatus.FORBIDDEN;
	}
}
