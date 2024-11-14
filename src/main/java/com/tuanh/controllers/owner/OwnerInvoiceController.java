package com.tuanh.controllers.owner;

import com.tuanh.constants.ControllerPath;
import com.tuanh.controllers.BaseController;
import com.tuanh.dtos.InvoiceDto;
import com.tuanh.dtos.ResponseDto;
import com.tuanh.dtos.request.UpdateInvoicePaymentDateDto;
import com.tuanh.services.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerPath.OWNER_INVOICE_CONTROLLER)
@RequiredArgsConstructor
public class OwnerInvoiceController extends BaseController {
	private final InvoiceService invoiceService;

	@GetMapping
	public ResponseEntity<ResponseDto> findAllInvoiceOfRentedRoom(@PathVariable Integer rentedRoomId) {
		return createSuccessResponse(ResponseDto.success(invoiceService.findAllByRentedRoomId(rentedRoomId)));
	}

	@GetMapping("/{invoiceId}")
	public ResponseEntity<ResponseDto> findInvoiceById(@PathVariable Integer rentedRoomId, @PathVariable Integer invoiceId) {
		return createSuccessResponse(ResponseDto.success(invoiceService.findById(rentedRoomId, invoiceId)));
	}

	@PostMapping
	public ResponseEntity<ResponseDto> createInvoice(@PathVariable Integer rentedRoomId, @RequestBody @Valid InvoiceDto invoiceDto) {
		return createSuccessResponse(ResponseDto.success(invoiceService.create(rentedRoomId, invoiceDto)));
	}

	@PutMapping("/{invoiceId}/payment-date")
	public ResponseEntity<ResponseDto> updatePaymentDate(
		@PathVariable Integer rentedRoomId,
		@PathVariable Integer invoiceId,
		@RequestBody @Valid UpdateInvoicePaymentDateDto updateInvoicePaymentDateDto) {
		return createSuccessResponse(ResponseDto.success(invoiceService.updatePaymentDate(rentedRoomId, invoiceId, updateInvoicePaymentDateDto)));
	}

	@DeleteMapping("/{invoiceId}")
	public ResponseEntity<ResponseDto> deleteInvoice(@PathVariable Integer rentedRoomId, @PathVariable Integer invoiceId) {
		invoiceService.delete(rentedRoomId, invoiceId);
		return createSuccessResponse(ResponseDto.success());
	}
}
