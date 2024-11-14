package com.tuanh.controllers.owner;

import com.tuanh.constants.ControllerPath;
import com.tuanh.controllers.BaseController;
import com.tuanh.dtos.ResponseDto;
import com.tuanh.dtos.RoomDto;
import com.tuanh.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerPath.OWNER_ROOM_CONTROLLER)
@RequiredArgsConstructor
public class OwnerRoomController extends BaseController {
	private final RoomService roomService;

	@GetMapping
	public ResponseEntity<ResponseDto> getRoomsByHouseId(@PathVariable Integer houseId) {
		return createSuccessResponse(ResponseDto.success(roomService.findAllByHouseId(houseId)));
	}

	@PostMapping
	public ResponseEntity<ResponseDto> createRoom(
		@PathVariable Integer houseId,
		@RequestBody RoomDto roomDto
	) {
		return createSuccessResponse(ResponseDto.success(roomService.create(houseId, roomDto)));
	}

	@PutMapping("/{roomId}")
	public ResponseEntity<ResponseDto> updateRoom(
		@PathVariable Integer houseId,
		@PathVariable Integer roomId,
		@RequestBody RoomDto roomDto
	) {
		roomDto.setId(roomId);
		return createSuccessResponse(ResponseDto.success(roomService.update(houseId, roomDto)));
	}

	@DeleteMapping("/{roomId}")
	public ResponseEntity<ResponseDto> deleteRoom(
		@PathVariable Integer roomId,
		@PathVariable String houseId
	) {
		roomService.delete(roomId);
		return createSuccessResponse(ResponseDto.success());
	}
}
