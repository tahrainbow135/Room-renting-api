package com.tuanh.constants;

public enum Message {
	// Common
	OK("OK"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	PERMISSION_DENIED("You do not have permission to access this resource!"),

	// Users
	USER_NOT_FOUND("User not found"),
	USER_EMAIL_NOT_FOUND("User with email %s not found"),
	USER_EMAIL_ALREADY_EXISTS("User with email %s already exists"),
	USER_PASSWORD_CHANGED_SUCCESSFULLY("User password changed successfully"),
	USER_ROLE_ASSIGNED_SUCCESSFULLY("User role assigned successfully"),

	// Roles
	ROLE_NOT_FOUND("Role not found"),

	// Houses
	HOUSE_NOT_FOUND("House not found"),
	;

	private final String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage(Object... args) {
		return String.format(message, args);
	}
}
