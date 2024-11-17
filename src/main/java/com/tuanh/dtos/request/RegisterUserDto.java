package com.tuanh.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Password is required")
	private String password;

	@NotBlank(message = "Full name is required")
	private String fullName;

	@NotBlank(message = "Email is required")
	@Email(message = "Email is invalid")
	private String email;

	@NotBlank(message = "Phone is required")
	private String phone;
}
