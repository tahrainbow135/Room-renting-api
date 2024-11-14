package com.tuanh.controllers.owner;

import com.tuanh.constants.ControllerPath;
import com.tuanh.controllers.BaseController;
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
@RequestMapping(ControllerPath.OWNER_HOUSE_CONTROLLER)
@RequiredArgsConstructor
public class OwnerHouseController extends BaseController {
	private final HouseService houseService;
	private final JwtAuthenticationManager jwtAuthenticationManager;

	@GetMapping
	public ResponseEntity<ResponseDto> findAll() {
		Integer ownerId = jwtAuthenticationManager.getUserId();
		return createSuccessResponse(ResponseDto.success(houseService.findByOwner(ownerId)));
	}

	@PutMapping("/{houseId}")
	public ResponseEntity<ResponseDto> updateHouse(@PathVariable Integer houseId, @RequestBody @Valid HouseDto houseDto) {
		Integer userId = jwtAuthenticationManager.getUserId();
		houseDto.setOwner(UserDto.builder().id(userId).build());
		houseDto.setId(houseId);
		return createSuccessResponse(ResponseDto.success(houseService.updateHouse(houseDto)));
	}

	@DeleteMapping("/{houseId}")
	public ResponseEntity<ResponseDto> deleteHouse(@PathVariable Integer houseId) {
		houseService.deleteHouse(houseId);
		return createSuccessResponse(ResponseDto.success());
	}
}
