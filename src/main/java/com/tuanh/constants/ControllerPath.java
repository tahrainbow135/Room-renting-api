package com.tuanh.constants;

public class ControllerPath {
	// User
	public static final String AUTH_CONTROLLER = "/auth";
	public static final String HOUSE_CONTROLLER = "/houses";

	// Owner
	public static final String OWNER_HOUSE_CONTROLLER = "/owner/houses";
	public static final String OWNER_ROOM_CONTROLLER = "/owner/houses/{houseId}/rooms";
	public static final String OWNER_RENTED_ROOM_CONTROLLER = "/owner/rooms/{roomId}/rented-rooms";
	public static final String OWNER_ASSET_CONTROLLER = "/owner/rooms/{roomId}/assets";
	public static final String OWNER_INVOICE_CONTROLLER = "/owner/rented-rooms/{rentedRoomId}/invoices";
}
