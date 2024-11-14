package com.tuanh.services;

import com.tuanh.constants.Message;
import com.tuanh.dtos.RoomDto;
import com.tuanh.entities.House;
import com.tuanh.entities.Room;
import com.tuanh.entities.User;
import com.tuanh.exceptions.HttpException;
import com.tuanh.mapper.RoomMapper;
import com.tuanh.repository.HouseRepository;
import com.tuanh.repository.RoomRepository;
import com.tuanh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
	private final RoomRepository roomRepository;
	private final HouseRepository houseRepository;
	private final UserRepository userRepository;

	private final JwtAuthenticationManager jwtAuthenticationManager;

	public List<RoomDto> findAllByHouseId(Integer houseId) {
		return roomRepository.findAllByHouseId(houseId).stream().map(RoomMapper::toDto).toList();
	}

	public RoomDto findById(Integer id) {
		return roomRepository.findById(id).map(RoomMapper::toDto)
			.orElseThrow(() -> HttpException.notFound(Message.ROOM_NOT_FOUND.getMessage()));
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
