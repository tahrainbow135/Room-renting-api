package com.tuanh.dtos;

import com.tuanh.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto extends BaseDto {
	private String username;
	private Set<Role> authorities;
}
