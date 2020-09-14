package com.msp.jio.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid Token") 
public class InvalidNumberException extends Exception {
InvalidNumberException(String message) {
	System.out.println(message);
}

public InvalidNumberException() {
	// TODO Auto-generated constructor stub
}
}