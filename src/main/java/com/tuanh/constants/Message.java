package com.tuanh.constants;

public enum Message {
	// Common
	OK("OK"),
	UNAUTHORIZED("Unauthorized"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	PERMISSION_DENIED("You do not have permission to access this resource!"),

	// General
	START_DATE_AFTER_END_DATE("Start date is after end date"),

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

	// Rooms
	ROOM_NOT_FOUND("Room not found"),

	// Rented rooms
	RENTED_ROOM_NOT_FOUND("Rented room not found"),
	ROOM_ALREADY_RENTED("Room is already rented"),
	RENTED_ROOM_NOT_IN_ROOM("Rented room is not in the room"),
	;

	private final String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage(Object... args) {
		return String.format(message, args);
	}
}
