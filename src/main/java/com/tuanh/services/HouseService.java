package com.tuanh.services;

import com.tuanh.constants.Message;
import com.tuanh.dtos.HouseDto;
import com.tuanh.entities.House;
import com.tuanh.entities.Role;
import com.tuanh.entities.User;
import com.tuanh.exceptions.HttpException;
import com.tuanh.mapper.HouseMapper;
import com.tuanh.repository.HouseRepository;
import com.tuanh.repository.RoleRepository;
import com.tuanh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HouseService {
	private final HouseRepository houseRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public List<HouseDto> findAll() {
		return houseRepository.findAll().stream()
			.map(HouseMapper::toDto)
			.toList();
	}

	public List<HouseDto> findByOwner(Integer ownerId) {
		User owner = userRepository.findById(ownerId)
			.orElseThrow(() -> HttpException.badRequest(Message.USER_NOT_FOUND.getMessage()));

		Role ownerRole = roleRepository.findByAuthority("OWNER")
			.orElseThrow(() -> HttpException.badRequest(Message.ROLE_NOT_FOUND.getMessage()));

		if (!owner.getAuthorities().contains(ownerRole)) {
			throw HttpException.forbidden(Message.PERMISSION_DENIED.getMessage());
		}

		return houseRepository.findAllByOwnerId(ownerId).stream()
			.map(HouseMapper::toDto)
			.toList();
	}

	public HouseDto findById(Integer id) {
		House house = houseRepository.findById(id)
			.orElseThrow(() -> HttpException.badRequest(Message.HOUSE_NOT_FOUND.getMessage()));

		return HouseMapper.toDto(house);
	}

	public HouseDto createHouse(HouseDto houseDto) {
		User owner = userRepository.findById(houseDto.getOwner().getId())
			.orElseThrow(() -> HttpException.badRequest(Message.USER_NOT_FOUND.getMessage()));

		House house = HouseMapper.toEntity(houseDto);


		Role ownerRole = roleRepository.findByAuthority("OWNER")
			.orElseThrow(() -> HttpException.badRequest(Message.ROLE_NOT_FOUND.getMessage()));
		owner.getAuthorities().add(ownerRole);
		owner = userRepository.save(owner);

		house.setOwner(owner);

		return HouseMapper.toDto(houseRepository.save(house));
	}

	public HouseDto updateHouse(HouseDto houseDto) {
		House house = houseRepository.findById(houseDto.getId())
			.orElseThrow(() -> HttpException.badRequest(Message.HOUSE_NOT_FOUND.getMessage()));

		if (!house.getOwner().getId().equals(houseDto.getOwner().getId())) {
			throw HttpException.badRequest(Message.PERMISSION_DENIED.getMessage());
		}

		HouseMapper.merge(house, houseDto);

		return HouseMapper.toDto(houseRepository.save(house));
	}

	public void deleteHouse(Integer id) {
		House house = houseRepository.findById(id)
			.orElseThrow(() -> HttpException.badRequest(Message.HOUSE_NOT_FOUND.getMessage()));

		houseRepository.delete(house);
	}
}
