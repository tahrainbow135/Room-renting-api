package com.tuanh.mapper;

import com.tuanh.dtos.UserDto;
import com.tuanh.entities.User;

public class UserMapper {
	public static UserDto toUserDto(User user) {
		return UserDto.builder()
			.id(user.getId())
			.username(user.getUsername())
			.fullName(user.getFullName())
			.authorities(user.getAuthorities())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
