package org.jsp.user_management.exceptionclasses;

import lombok.Builder;

@Builder
public class UserNotFoundException extends RuntimeException {

	private String message;

	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
