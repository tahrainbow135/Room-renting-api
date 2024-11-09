package com.tuanh.controllers;

import com.tuanh.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController extends BaseController {

	@GetMapping("/")
	public ResponseEntity<ResponseDto> helloUserController() {
		return createSuccessResponse(ResponseDto.success("Hello from UserController!"));
	}
}
