package com.tuanh.mapper;

import com.tuanh.dtos.RentedRoomDto;
import com.tuanh.entities.RentedRoom;

public class RentedRoomMapper {
	public static RentedRoomDto toDto(RentedRoom rentedRoom) {
		return RentedRoomDto.builder()
			.id(rentedRoom.getId())
			.tenantName(rentedRoom.getTenantName())
			.tenantPhone(rentedRoom.getTenantPhone())
			.startDate(rentedRoom.getStartDate())
			.endDate(rentedRoom.getEndDate())
			.contractUrl(rentedRoom.getContractUrl())
			.room(RoomMapper.toDto(rentedRoom.getRoom()))
			.createdAt(rentedRoom.getCreatedAt())
			.updatedAt(rentedRoom.getUpdatedAt())
			.build();
	}

	public static RentedRoom toEntity(RentedRoomDto rentedRoomDto) {
		RentedRoom rentedRoom = new RentedRoom();
		merge(rentedRoom, rentedRoomDto);
		rentedRoom.setCreatedAt(rentedRoomDto.getCreatedAt());
		rentedRoom.setUpdatedAt(rentedRoomDto.getUpdatedAt());
		return rentedRoom;
	}

	public static void merge(RentedRoom rentedRoom, RentedRoomDto rentedRoomDto) {
		rentedRoom.setTenantName(rentedRoomDto.getTenantName());
		rentedRoom.setTenantPhone(rentedRoomDto.getTenantPhone());
		rentedRoom.setStartDate(rentedRoomDto.getStartDate());
		rentedRoom.setEndDate(rentedRoomDto.getEndDate());
		rentedRoom.setContractUrl(rentedRoomDto.getContractUrl());
		rentedRoom.setUpdatedAt(rentedRoomDto.getUpdatedAt());
	}
}
