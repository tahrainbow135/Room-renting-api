package com.tuanh.controllers;

import com.tuanh.dtos.ResponseDto;
import com.tuanh.dtos.request.AuthenticationRequestDto;
import com.tuanh.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {
	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<ResponseDto> registerUser(@RequestBody AuthenticationRequestDto body) {
		return createSuccessResponse(ResponseDto.success(authenticationService.registerUser(body.getUsername(), body.getPassword())));
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDto> loginUser(@RequestBody AuthenticationRequestDto body) {
		return createSuccessResponse(ResponseDto.success(authenticationService.loginUser(body.getUsername(), body.getPassword())));
	}
}
