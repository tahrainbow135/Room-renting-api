package com.tuanh.services;

import com.tuanh.constants.Message;
import com.tuanh.dtos.AssetDto;
import com.tuanh.entities.Asset;
import com.tuanh.entities.Room;
import com.tuanh.exceptions.HttpException;
import com.tuanh.mapper.AssetMapper;
import com.tuanh.repository.AssetRepository;
import com.tuanh.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetService {
	private final AssetRepository assetRepository;
	private final RoomRepository roomRepository;

	public List<AssetDto> findAllByRoomId(Integer id) {
		return assetRepository.findAllByRoomId(id).stream()
				.map(AssetMapper::toDto)
				.toList();
	}

	public AssetDto findById(Integer roomId, Integer id) {
		Asset asset = assetRepository.findById(id)
			.orElseThrow(() -> HttpException.notFound(Message.ASSET_NOT_FOUND.getMessage()));

		if (!asset.getRoom().getId().equals(roomId)) {
			throw HttpException.notFound(Message.ASSET_NOT_FOUND.getMessage());
		}

		return AssetMapper.toDto(asset);
	}

	public AssetDto create(Integer roomId, AssetDto assetDto) {
		Room room = roomRepository.findById(roomId)
			.orElseThrow(() -> HttpException.notFound(Message.ROOM_NOT_FOUND.getMessage()));

		Asset asset = AssetMapper.toEntity(assetDto);
		asset.setRoom(room);
		return AssetMapper.toDto(assetRepository.save(asset));
	}

	public AssetDto update(Integer roomId, Integer assetId, AssetDto assetDto) {
		Asset asset = assetRepository.findById(assetId)
			.orElseThrow(() -> HttpException.notFound(Message.ASSET_NOT_FOUND.getMessage()));

		if (!asset.getRoom().getId().equals(roomId)) {
			throw HttpException.notFound(Message.ASSET_NOT_FOUND.getMessage());
		}

		AssetMapper.merge(asset, assetDto);
		return AssetMapper.toDto(assetRepository.save(asset));
	}

	public void delete(Integer roomId, Integer assetId) {
		Asset asset = assetRepository.findById(assetId)
			.orElseThrow(() -> HttpException.notFound(Message.ASSET_NOT_FOUND.getMessage()));

		if (!asset.getRoom().getId().equals(roomId)) {
			throw HttpException.notFound(Message.ASSET_NOT_FOUND.getMessage());
		}

		assetRepository.delete(asset);
	}
}
