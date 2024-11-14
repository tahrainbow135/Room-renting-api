package com.tuanh.controllers;

import com.tuanh.constants.ControllerPath;
import com.tuanh.dtos.HouseDto;
import com.tuanh.dtos.ResponseDto;
import com.tuanh.dtos.UserDto;
import com.tuanh.services.HouseService;
import com.tuanh.services.JwtAuthenticationManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ControllerPath.HOUSE_CONTROLLER)
@RequiredArgsConstructor
public class HouseController extends BaseController {
	private final HouseService houseService;
	private final JwtAuthenticationManager jwtAuthenticationManager;

	@GetMapping
	public ResponseEntity<ResponseDto> findAll() {
		return createSuccessResponse(ResponseDto.success(houseService.findAll()));
	}

	@GetMapping("/{houseId}")
	public ResponseEntity<ResponseDto> findById(@PathVariable Integer houseId) {
		return createSuccessResponse(ResponseDto.success(houseService.findById(houseId)));
	}

	@PostMapping
	public ResponseEntity<ResponseDto> createHouse(@RequestBody @Valid HouseDto houseDto) {
		Integer userId = jwtAuthenticationManager.getUserId();
		houseDto.setOwner(UserDto.builder().id(userId).build());
		return createSuccessResponse(ResponseDto.success(houseService.createHouse(houseDto)));
	}
}
