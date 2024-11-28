package com.tuanh.services;

import com.tuanh.constants.Message;
import com.tuanh.dtos.RoomDto;
import com.tuanh.entities.House;
import com.tuanh.entities.Room;
import com.tuanh.entities.User;
import com.tuanh.exceptions.HttpException;
import com.tuanh.mapper.RoomMapper;
import com.tuanh.repository.HouseRepository;
import com.tuanh.repository.RentedRoomRepository;
import com.tuanh.repository.RoomRepository;
import com.tuanh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
	private final RoomRepository roomRepository;
	private final HouseRepository houseRepository;
	private final UserRepository userRepository;
	private final RentedRoomRepository rentedRoomRepository;

	private final JwtAuthenticationManager jwtAuthenticationManager;

	public List<RoomDto> findAllByHouseId(Integer houseId) {
		List<RoomDto> rooms = roomRepository.findAllByHouseId(houseId).stream().map(RoomMapper::toDto).toList();
		LocalDate now = LocalDate.now();
		for (RoomDto room : rooms) {
			// Check if the room is rented currently
			room.setIsCurrentlyRented(rentedRoomRepository.existsByRoomIdAndEndDateAfter(room.getId(), now));
		}

		return rooms;
	}

	public RoomDto findById(Integer id) {
		RoomDto room = roomRepository.findById(id).map(RoomMapper::toDto)
			.orElseThrow(() -> HttpException.notFound(Message.ROOM_NOT_FOUND.getMessage()));

		LocalDate now = LocalDate.now();
		room.setIsCurrentlyRented(rentedRoomRepository.existsByRoomIdAndEndDateAfter(room.getId(), now));

		return room;
	}

	public RoomDto create(Integer houseId, RoomDto roomDto) {
		Integer userId = jwtAuthenticationManager.getUserId();
		User owner = userRepository.findById(userId)
			.orElseThrow(() -> HttpException.unauthorized(Message.UNAUTHORIZED.getMessage()));

		House house = houseRepository.findById(houseId)
			.orElseThrow(() -> HttpException.notFound(Message.HOUSE_NOT_FOUND.getMessage()));

		if (!house.getOwner().getId().equals(owner.getId())) {
			throw HttpException.forbidden(Message.PERMISSION_DENIED.getMessage());
		}

		Room room = RoomMapper.toEntity(roomDto);
		room.setHouse(house);

		return RoomMapper.toDto(roomRepository.save(room));
	}

	public RoomDto update(Integer houseId, RoomDto roomDto) {
		Integer userId = jwtAuthenticationManager.getUserId();
		User owner = userRepository.findById(userId)
			.orElseThrow(() -> HttpException.unauthorized(Message.UNAUTHORIZED.getMessage()));

		Room room = roomRepository.findById(roomDto.getId())
			.orElseThrow(() -> HttpException.notFound(Message.ROOM_NOT_FOUND.getMessage()));

		if (!room.getHouse().getOwner().getId().equals(owner.getId())) {
			throw HttpException.forbidden(Message.PERMISSION_DENIED.getMessage());
		}

		RoomMapper.merge(room, roomDto);

		return RoomMapper.toDto(roomRepository.save(room));
	}

	public void delete(Integer roomId) {
		Integer userId = jwtAuthenticationManager.getUserId();
		User owner = userRepository.findById(userId)
			.orElseThrow(() -> HttpException.unauthorized(Message.UNAUTHORIZED.getMessage()));

		Room room = roomRepository.findById(roomId)
			.orElseThrow(() -> HttpException.notFound(Message.ROOM_NOT_FOUND.getMessage()));

		if (!room.getHouse().getOwner().getId().equals(owner.getId())) {
			throw HttpException.forbidden(Message.PERMISSION_DENIED.getMessage());
		}

		roomRepository.deleteById(roomId);
	}
}
