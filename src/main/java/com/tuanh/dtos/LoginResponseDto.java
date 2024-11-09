package com.tuanh.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
	private UserDto user;
	private String jwt;
}
