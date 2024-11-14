package com.tuanh.mapper;

import com.tuanh.dtos.RentedRoomDto;
import com.tuanh.entities.RentedRoom;

public class RentedRoomMapper {
	public static RentedRoomDto toDto(RentedRoom rentedRoom) {
		return RentedRoomDto.builder()
			.id(rentedRoom.getId())
			.tenantName(rentedRoom.getTenantName())
			.tenantPhone(rentedRoom.getTenantPhone())
			.numberOfTenants(rentedRoom.getNumberOfTenants())
			.startDate(rentedRoom.getStartDate())
			.endDate(rentedRoom.getEndDate())
			.paymentDay(rentedRoom.getPaymentDay())
			.contractUrl(rentedRoom.getContractUrl())
			.price(rentedRoom.getPrice())
			.electricityPrice(rentedRoom.getElectricityPrice())
			.waterPrice(rentedRoom.getWaterPrice())
			.internetPrice(rentedRoom.getInternetPrice())
			.generalPrice(rentedRoom.getGeneralPrice())
			.initElectricityNum(rentedRoom.getInitElectricityNum())
			.initWaterNum(rentedRoom.getInitWaterNum())
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
		rentedRoom.setNumberOfTenants(rentedRoomDto.getNumberOfTenants());
		rentedRoom.setStartDate(rentedRoomDto.getStartDate());
		rentedRoom.setEndDate(rentedRoomDto.getEndDate());
		rentedRoom.setPaymentDay(rentedRoomDto.getPaymentDay());
		rentedRoom.setContractUrl(rentedRoomDto.getContractUrl());
		rentedRoom.setPrice(rentedRoomDto.getPrice());
		rentedRoom.setElectricityPrice(rentedRoomDto.getElectricityPrice());
		rentedRoom.setWaterPrice(rentedRoomDto.getWaterPrice());
		rentedRoom.setInternetPrice(rentedRoomDto.getInternetPrice());
		rentedRoom.setGeneralPrice(rentedRoomDto.getGeneralPrice());
		rentedRoom.setInitElectricityNum(rentedRoomDto.getInitElectricityNum());
		rentedRoom.setInitWaterNum(rentedRoomDto.getInitWaterNum());
	}
}
