package com.samsung.guesting.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException {
	private String message;
	private HttpStatus httpStatus;
	
	public BusinessException(String message) {
		this.message = message;
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
}
