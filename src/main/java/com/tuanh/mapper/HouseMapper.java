package com.tuanh.mapper;

import com.tuanh.dtos.HouseDto;
import com.tuanh.entities.House;

public class HouseMapper {
	public static HouseDto toDto(House house) {
		return HouseDto.builder()
			.id(house.getId())
			.name(house.getName())
			.addressLine(house.getAddressLine())
			.ward(house.getWard())
			.district(house.getDistrict())
			.city(house.getCity())
			.floorCount(house.getFloorCount())
			.owner(UserMapper.toUserDto(house.getOwner()))
			.createdAt(house.getCreatedAt())
			.updatedAt(house.getUpdatedAt())
			.build();
	}

	public static House toEntity(HouseDto houseDto) {
		House house = new House();
		house.setName(houseDto.getName());
		house.setAddressLine(houseDto.getAddressLine());
		house.setWard(houseDto.getWard());
		house.setDistrict(houseDto.getDistrict());
		house.setCity(houseDto.getCity());
		house.setFloorCount(houseDto.getFloorCount());
		house.setCreatedAt(houseDto.getCreatedAt());
		house.setUpdatedAt(houseDto.getUpdatedAt());
		return house;
	}

	public static void merge(House house, HouseDto houseDto) {
		house.setName(houseDto.getName());
		house.setAddressLine(houseDto.getAddressLine());
		house.setWard(houseDto.getWard());
		house.setDistrict(houseDto.getDistrict());
		house.setCity(houseDto.getCity());
		house.setFloorCount(houseDto.getFloorCount());
		house.setUpdatedAt(houseDto.getUpdatedAt());
	}
}
