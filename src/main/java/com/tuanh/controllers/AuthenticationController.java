package com.tuanh.controllers;

import com.tuanh.constants.ControllerPath;
import com.tuanh.dtos.ResponseDto;
import com.tuanh.dtos.request.AuthenticationRequestDto;
import com.tuanh.dtos.request.RegisterUserDto;
import com.tuanh.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerPath.AUTH_CONTROLLER)
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {
	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<ResponseDto> registerUser(@RequestBody @Valid RegisterUserDto body) {
		return createSuccessResponse(ResponseDto.success(authenticationService.registerUser(body)));
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDto> loginUser(@RequestBody AuthenticationRequestDto body) {
		return createSuccessResponse(ResponseDto.success(authenticationService.loginUser(body.getUsername(), body.getPassword())));
	}
}
