package com.samsung.guesting.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustomException extends RuntimeException{
	private String message;
	private HttpStatus httpStatus;
	public CustomException(String message, HttpStatus status) {
		super();
		this.message = message;
		this.httpStatus = status;
	}
	public CustomException(String message) {
		this(message,HttpStatus.EXPECTATION_FAILED);
	}
	
	
}
