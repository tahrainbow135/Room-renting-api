package com.tuanh.controllers.owner;

import com.tuanh.constants.ControllerPath;
import com.tuanh.controllers.BaseController;
import com.tuanh.dtos.AssetDto;
import com.tuanh.dtos.ResponseDto;
import com.tuanh.services.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerPath.OWNER_ASSET_CONTROLLER)
@RequiredArgsConstructor
public class OwnerAssetController extends BaseController {
	private final AssetService assetService;

	@GetMapping
	public ResponseEntity<ResponseDto> getAssetsInRoom(@PathVariable Integer roomId) {
		return createSuccessResponse(ResponseDto.success(assetService.findAllByRoomId(roomId)));
	}

	@GetMapping("/{assetId}")
	public ResponseEntity<ResponseDto> getAsset(@PathVariable Integer roomId, @PathVariable Integer assetId) {
		return createSuccessResponse(ResponseDto.success(assetService.findById(roomId, assetId)));
	}

	@PostMapping
	public ResponseEntity<ResponseDto> createAsset(@PathVariable Integer roomId, @RequestBody @Valid AssetDto assetDto) {
		return createSuccessResponse(ResponseDto.success(assetService.create(roomId, assetDto)));
	}

	@PutMapping("/{assetId}")
	public ResponseEntity<ResponseDto> updateAsset(
		@PathVariable Integer roomId,
		@PathVariable Integer assetId,
		@RequestBody @Valid AssetDto assetDto
	) {
		return createSuccessResponse(ResponseDto.success(assetService.update(roomId, assetId, assetDto)));
	}

	@DeleteMapping("/{assetId}")
	public ResponseEntity<ResponseDto> deleteAsset(@PathVariable Integer roomId, @PathVariable Integer assetId) {
		assetService.delete(roomId, assetId);
		return createSuccessResponse(ResponseDto.success());
	}
}
