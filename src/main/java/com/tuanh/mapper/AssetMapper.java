package com.tuanh.mapper;

import com.tuanh.dtos.AssetDto;
import com.tuanh.entities.Asset;

public class AssetMapper {
	public static AssetDto toDto(Asset asset) {
		return AssetDto.builder()
				.id(asset.getId())
				.name(asset.getName())
				.imageUrl(asset.getImageUrl())
				.room(RoomMapper.toDto(asset.getRoom()))
				.createdAt(asset.getCreatedAt())
				.updatedAt(asset.getUpdatedAt())
				.build();
	}

	public static Asset toEntity(AssetDto assetDto) {
		Asset asset = new Asset();
		asset.setId(assetDto.getId());
		asset.setName(assetDto.getName());
		asset.setImageUrl(assetDto.getImageUrl());
		return asset;
	}

	public static void merge(Asset asset, AssetDto assetDto) {
		asset.setName(assetDto.getName());
		asset.setImageUrl(assetDto.getImageUrl());
	}
}
