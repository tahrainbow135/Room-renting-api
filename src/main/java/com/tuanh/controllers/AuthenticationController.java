package com.tuanh.controllers;

import com.tuanh.dtos.LoginResponseDto;
import com.tuanh.dtos.RegistrationDto;
import com.tuanh.models.ApplicationUser;
import com.tuanh.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ApplicationUser registerUser(@RequestBody RegistrationDto body) {
		return authenticationService.registerUser(body.getUsername(), body.getPassword());
	}

	@PostMapping("/login")
	public LoginResponseDto loginUser(@RequestBody RegistrationDto body) {
		return authenticationService.loginUser(body.getUsername(), body.getPassword());
	}
}   
