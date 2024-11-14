package com.tuanh.services;

import com.tuanh.constants.Message;
import com.tuanh.dtos.RentedRoomDto;
import com.tuanh.entities.RentedRoom;
import com.tuanh.entities.Room;
import com.tuanh.entities.User;
import com.tuanh.exceptions.HttpException;
import com.tuanh.mapper.RentedRoomMapper;
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
public class RentedRoomService {
	private final RentedRoomRepository rentedRoomRepository;
	private final RoomRepository roomRepository;
	private final JwtAuthenticationManager jwtAuthenticationManager;
	private final UserRepository userRepository;

	public List<RentedRoomDto> findAllByRoomId(Integer roomId) {
		return rentedRoomRepository.findAllByRoomId(roomId)
			.stream()
			.map(RentedRoomMapper::toDto)
			.toList();
	}

	public RentedRoomDto findById(Integer roomId, Integer id) {
		RentedRoomDto rentedRoomDto = rentedRoomRepository.findById(id)
			.map(RentedRoomMapper::toDto)
			.orElseThrow(() -> HttpException.notFound(Message.RENTED_ROOM_NOT_FOUND.getMessage()));

		if (!rentedRoomDto.getRoom().getId().equals(roomId)) {
			throw HttpException.badRequest(Message.RENTED_ROOM_NOT_IN_ROOM.getMessage());
		}

		return rentedRoomDto;
	}

	public RentedRoomDto create(Integer roomId, RentedRoomDto rentedRoomDto) {
		LocalDate startDate = rentedRoomDto.getStartDate();
		LocalDate endDate = rentedRoomDto.getEndDate();

		// Check if the start date is after the end date
		if (startDate.isAfter(endDate)) {
			throw HttpException.badRequest(Message.START_DATE_AFTER_END_DATE.getMessage());
		}

		Room room = roomRepository.findById(roomId)
			.orElseThrow(() -> HttpException.notFound(Message.ROOM_NOT_FOUND.getMessage()));

		if (rentedRoomDto.getNumberOfTenants() > room.getCapacity()) {
			throw HttpException.badRequest(Message.TENANTS_EXCEED_CAPACITY.getMessage(room.getCapacity()));
		}

		User owner = room.getHouse().getOwner();
		User currentUser = userRepository.findById(jwtAuthenticationManager.getUserId())
			.orElseThrow(() -> HttpException.unauthorized(Message.UNAUTHORIZED.getMessage()));

		// Check if the current user is the owner of the room
		if (!owner.getId().equals(currentUser.getId())) {
			throw HttpException.forbidden(Message.PERMISSION_DENIED.getMessage());
		}

		// Check if the room is already rented
		if (rentedRoomRepository.checkIfRoomIsRentedInPeriod(roomId, startDate, endDate)) {
			throw HttpException.badRequest(Message.ROOM_ALREADY_RENTED.getMessage());
		}

		RentedRoom rentedRoom = RentedRoomMapper.toEntity(rentedRoomDto);
		rentedRoom.setRoom(room);

		return RentedRoomMapper.toDto(rentedRoomRepository.save(rentedRoom));
	}

	public RentedRoomDto endRentedRoom(Integer roomId, Integer id) {
		RentedRoom rentedRoom = rentedRoomRepository.findById(id)
			.orElseThrow(() -> HttpException.notFound(Message.RENTED_ROOM_NOT_FOUND.getMessage()));

		// Check if the rented room is in the room
		if (!rentedRoom.getRoom().getId().equals(roomId)) {
			throw HttpException.badRequest(Message.RENTED_ROOM_NOT_IN_ROOM.getMessage());
		}

		User owner = rentedRoom.getRoom().getHouse().getOwner();
		User currentUser = userRepository.findById(jwtAuthenticationManager.getUserId())
			.orElseThrow(() -> HttpException.unauthorized(Message.UNAUTHORIZED.getMessage()));

		// Check if the current user is the owner of the room
		if (!owner.getId().equals(currentUser.getId())) {
			throw HttpException.forbidden(Message.PERMISSION_DENIED.getMessage());
		}

		rentedRoom.setEndDate(LocalDate.now());

		return RentedRoomMapper.toDto(rentedRoomRepository.save(rentedRoom));
	}
}
