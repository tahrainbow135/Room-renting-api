package com.tuanh.constants;

public enum Message {
	// Common
	OK("OK"),
	UNAUTHORIZED("Unauthorized"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	PERMISSION_DENIED("You do not have permission to access this resource!"),

	// General
	START_DATE_AFTER_END_DATE("Start date is after end date"),

	// Auth
	INVALID_CREDENTIALS("Invalid credentials"),
	USER_ALREADY_EXISTS("User already exists"),

	// Users
	USER_NOT_FOUND("User not found"),

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
	TENANTS_EXCEED_CAPACITY("Number of tenants exceeds room capacity of %d"),

	// Assets
	ASSET_NOT_FOUND("Asset not found in room"),

	// Invoices
	INVOICE_NOT_FOUND("Invoice not found"),
	INVALID_ELECTRICITY_NUM("Electricity number must be greater than the previous invoice"),
	INVALID_WATER_NUM("Water number must be greater than the previous invoice"),
	INVOICE_ALREADY_PAID("Invoice has already been paid"),
	INVOICE_NOT_BELONG_TO_RENTED_ROOM("Invoice does not belong to rented room"),
	;

	private final String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage(Object... args) {
		return String.format(message, args);
	}
}
