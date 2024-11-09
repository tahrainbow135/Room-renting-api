package com.tuanh.controllers;

import com.tuanh.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController extends BaseController {

	@GetMapping("/")
	public ResponseEntity<ResponseDto> helloAdminController() {
		return createSuccessResponse(ResponseDto.success("Hello from AdminController!"));
	}
}
