package com.tuanh.controllers.owner;

import com.tuanh.constants.ControllerPath;
import com.tuanh.controllers.BaseController;
import com.tuanh.dtos.RentedRoomDto;
import com.tuanh.dtos.ResponseDto;
import com.tuanh.services.RentedRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerPath.OWNER_RENTED_ROOM_CONTROLLER)
@RequiredArgsConstructor
public class OwnerRentedRoomController extends BaseController {
	private final RentedRoomService rentedRoomService;

	@GetMapping
	public ResponseEntity<ResponseDto> getRentedRoomsByRoomId(@PathVariable Integer roomId) {
		return createSuccessResponse(ResponseDto.success(rentedRoomService.findAllByRoomId(roomId)));
	}

	@GetMapping("/{rentedRoomId}")
	public ResponseEntity<ResponseDto> getRentedRoomById(@PathVariable Integer roomId, @PathVariable Integer rentedRoomId) {
		return createSuccessResponse(ResponseDto.success(rentedRoomService.findById(roomId, rentedRoomId)));
	}

	@PostMapping
	public ResponseEntity<ResponseDto> createRentedRoom(
		@PathVariable Integer roomId,
		@RequestBody @Valid RentedRoomDto rentedRoomDto
	) {
		return createSuccessResponse(ResponseDto.success(rentedRoomService.create(roomId, rentedRoomDto)));
	}

	@DeleteMapping("/{rentedRoomId}")
	public ResponseEntity<ResponseDto> endRentedRoom(
		@PathVariable Integer roomId,
		@PathVariable Integer rentedRoomId
	) {
		return createSuccessResponse(ResponseDto.success(rentedRoomService.endRentedRoom(roomId, rentedRoomId)));
	}
}
