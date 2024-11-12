package com.tuanh.mapper;

import com.tuanh.dtos.RoomDto;
import com.tuanh.entities.Room;

public class RoomMapper {
	public static RoomDto toDto(Room room) {
		return RoomDto.builder()
			.id(room.getId())
			.name(room.getName())
			.capacity(room.getCapacity())
			.description(room.getDescription())
			.house(HouseMapper.toDto(room.getHouse()))
			.createdAt(room.getCreatedAt())
			.updatedAt(room.getUpdatedAt())
			.build();
	}

	public static Room toEntity(RoomDto roomDto) {
		Room room = new Room();
		merge(room, roomDto);
		room.setCreatedAt(roomDto.getCreatedAt());
		room.setUpdatedAt(roomDto.getUpdatedAt());
		return room;
	}

	public static void merge(Room room, RoomDto roomDto) {
		room.setName(roomDto.getName());
		room.setCapacity(roomDto.getCapacity());
		room.setDescription(roomDto.getDescription());
	}
}
