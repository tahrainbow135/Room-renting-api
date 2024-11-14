package com.tuanh.services;

import com.tuanh.constants.Message;
import com.tuanh.dtos.InvoiceDto;
import com.tuanh.dtos.request.UpdateInvoicePaymentDateDto;
import com.tuanh.entities.Invoice;
import com.tuanh.entities.RentedRoom;
import com.tuanh.exceptions.HttpException;
import com.tuanh.mapper.InvoiceMapper;
import com.tuanh.repository.InvoiceRepository;
import com.tuanh.repository.RentedRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceService {
	private final InvoiceRepository invoiceRepository;
	private final RentedRoomRepository rentedRoomRepository;

	public List<InvoiceDto> findAllByRentedRoomId(Integer rentedRoomId) {
		RentedRoom rentedRoom = rentedRoomRepository.findById(rentedRoomId)
			.orElseThrow(() -> HttpException.notFound(Message.RENTED_ROOM_NOT_FOUND.getMessage()));

		return invoiceRepository.findAllByRentedRoomId(rentedRoomId)
			.stream()
			.map(InvoiceMapper::toDto)
			.toList();
	}

	public InvoiceDto findById(Integer rentedRoomId, Integer invoiceId) {
		RentedRoom rentedRoom = rentedRoomRepository.findById(rentedRoomId)
			.orElseThrow(() -> HttpException.notFound(Message.RENTED_ROOM_NOT_FOUND.getMessage()));

		Invoice invoice = invoiceRepository.findById(invoiceId)
			.orElseThrow(() -> HttpException.notFound(Message.INVOICE_NOT_FOUND.getMessage()));

		if (!invoice.getRentedRoom().getId().equals(rentedRoomId)) {
			throw HttpException.badRequest(Message.INVOICE_NOT_BELONG_TO_RENTED_ROOM.getMessage());
		}

		return InvoiceMapper.toDto(invoice);
	}

	public InvoiceDto create(Integer rentedRoomId, InvoiceDto invoiceDto) {
		RentedRoom rentedRoom = rentedRoomRepository.findById(rentedRoomId)
			.orElseThrow(() -> HttpException.notFound(Message.RENTED_ROOM_NOT_FOUND.getMessage()));

		List<Invoice> invoices = invoiceRepository.findAllByRentedRoomId(rentedRoomId);

		if (!invoices.isEmpty()) {
			return handleInvoiceFromSecondMonth(invoices, rentedRoom, invoiceDto);
		} else {
			return handleFirstInvoice(rentedRoom, invoiceDto);
		}
	}

	public InvoiceDto updatePaymentDate(Integer rentedRoomId, Integer invoiceId, UpdateInvoicePaymentDateDto updateInvoicePaymentDateDto) {
		RentedRoom rentedRoom = rentedRoomRepository.findById(rentedRoomId)
			.orElseThrow(() -> HttpException.notFound(Message.RENTED_ROOM_NOT_FOUND.getMessage()));

		Invoice invoice = invoiceRepository.findById(invoiceId)
			.orElseThrow(() -> HttpException.notFound(Message.INVOICE_NOT_FOUND.getMessage()));

		if (!invoice.getRentedRoom().getId().equals(rentedRoomId)) {
			throw HttpException.badRequest(Message.INVOICE_NOT_BELONG_TO_RENTED_ROOM.getMessage());
		}

		if (invoice.getPaymentDate() != null) {
			throw HttpException.badRequest(Message.INVOICE_ALREADY_PAID.getMessage());
		}

		invoice.setPaymentDate(updateInvoicePaymentDateDto.getPaymentDate());
		return InvoiceMapper.toDto(invoiceRepository.save(invoice));
	}

	public void delete(Integer roomId, Integer invoiceId) {
		RentedRoom rentedRoom = rentedRoomRepository.findById(roomId)
			.orElseThrow(() -> HttpException.notFound(Message.RENTED_ROOM_NOT_FOUND.getMessage()));

		Invoice invoice = invoiceRepository.findById(invoiceId)
			.orElseThrow(() -> HttpException.notFound(Message.INVOICE_NOT_FOUND.getMessage()));

		if (!invoice.getRentedRoom().getId().equals(roomId)) {
			throw HttpException.badRequest(Message.INVOICE_NOT_BELONG_TO_RENTED_ROOM.getMessage());
		}

		if (invoice.getPaymentDate() != null) {
			throw HttpException.badRequest(Message.INVOICE_ALREADY_PAID.getMessage());
		}

		invoiceRepository.deleteById(invoiceId);
	}

	private InvoiceDto handleInvoiceFromSecondMonth(List<Invoice> invoices, RentedRoom rentedRoom, InvoiceDto invoiceDto) {
		Invoice lastInvoice = invoices.get(invoices.size() - 1);
		Integer roomPrice = rentedRoom.getPrice();

		if (invoiceDto.getElectricityNum() < lastInvoice.getElectricityNum()) {
			throw HttpException.badRequest(Message.INVALID_ELECTRICITY_NUM.getMessage());
		}

		if (invoiceDto.getWaterNum() < lastInvoice.getWaterNum()) {
			throw HttpException.badRequest(Message.INVALID_WATER_NUM.getMessage());
		}

		Integer electricityPrice = rentedRoom.getElectricityPrice() * (invoiceDto.getElectricityNum() - lastInvoice.getElectricityNum());
		Integer waterPrice = rentedRoom.getWaterPrice() * (invoiceDto.getWaterNum() - lastInvoice.getWaterNum());
		Integer internetPrice = rentedRoom.getInternetPrice();
		Integer generalPrice = rentedRoom.getGeneralPrice();
		Integer totalPrice = roomPrice + electricityPrice + waterPrice + internetPrice + generalPrice;
		invoiceDto.setTotalPrice(totalPrice);
		invoiceDto.setElectricityPrice(electricityPrice);
		invoiceDto.setWaterPrice(waterPrice);
		invoiceDto.setRoomPrice(roomPrice);
		invoiceDto.setInternetPrice(internetPrice);
		invoiceDto.setGeneralPrice(generalPrice);

		Integer paymentDay = rentedRoom.getPaymentDay();
		// Set due date to the payment day of the next month
		LocalDate dueDate = lastInvoice.getDueDate().plusMonths(1).withDayOfMonth(paymentDay);
		invoiceDto.setDueDate(dueDate);
		invoiceDto.setPaymentDate(null);

		Invoice invoice = InvoiceMapper.toEntity(invoiceDto);
		invoice.setRentedRoom(rentedRoom);
		InvoiceDto returnInvoice = InvoiceMapper.toDto(invoiceRepository.save(invoice));
		returnInvoice.setTotalPrice(totalPrice);
		return returnInvoice;
	}

	private InvoiceDto handleFirstInvoice(RentedRoom rentedRoom, InvoiceDto invoiceDto) {
		Integer paymentDay = rentedRoom.getPaymentDay();
		LocalDate dueDate = rentedRoom.getStartDate().plusMonths(1).withDayOfMonth(paymentDay);
		invoiceDto.setDueDate(dueDate);
		invoiceDto.setPaymentDate(null);

		Integer roomPrice = rentedRoom.getPrice();
		// Calculate number of days from the start date till the due date
		Integer days = (int) (dueDate.toEpochDay() - rentedRoom.getStartDate().toEpochDay());
		Integer actualPrice = days * roomPrice / 30;

		if (invoiceDto.getElectricityNum() < rentedRoom.getInitElectricityNum()) {
			throw HttpException.badRequest(Message.INVALID_ELECTRICITY_NUM.getMessage());
		}

		if (invoiceDto.getWaterNum() < rentedRoom.getInitWaterNum()) {
			throw HttpException.badRequest(Message.INVALID_WATER_NUM.getMessage());
		}

		Integer electricityPrice = rentedRoom.getElectricityPrice() * (invoiceDto.getElectricityNum() - rentedRoom.getInitElectricityNum());
		Integer waterPrice = rentedRoom.getWaterPrice() * (invoiceDto.getWaterNum() - rentedRoom.getInitWaterNum());
		Integer internetPrice = rentedRoom.getInternetPrice();
		Integer generalPrice = rentedRoom.getGeneralPrice();
		Integer totalPrice = actualPrice + electricityPrice + waterPrice + internetPrice + generalPrice;
		invoiceDto.setTotalPrice(totalPrice);
		invoiceDto.setElectricityPrice(electricityPrice);
		invoiceDto.setWaterPrice(waterPrice);
		invoiceDto.setRoomPrice(actualPrice);
		invoiceDto.setInternetPrice(internetPrice);
		invoiceDto.setGeneralPrice(generalPrice);

		Invoice invoice = InvoiceMapper.toEntity(invoiceDto);
		invoice.setRentedRoom(rentedRoom);
		InvoiceDto returnInvoice = InvoiceMapper.toDto(invoiceRepository.save(invoice));
		returnInvoice.setTotalPrice(totalPrice);
		return returnInvoice;
	}
}
