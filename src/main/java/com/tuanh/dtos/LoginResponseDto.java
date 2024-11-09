package com.tuanh.dtos;

import com.tuanh.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
	private ApplicationUser user;
	private String jwt;
}
