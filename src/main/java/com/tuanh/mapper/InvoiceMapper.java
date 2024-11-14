package com.tuanh.mapper;

import com.tuanh.dtos.InvoiceDto;
import com.tuanh.entities.Invoice;

public class InvoiceMapper {
	public static InvoiceDto toDto(Invoice invoice) {
		return InvoiceDto.builder()
			.id(invoice.getId())
			.electricityPrice(invoice.getElectricityPrice())
			.waterPrice(invoice.getWaterPrice())
			.roomPrice(invoice.getRoomPrice())
			.internetPrice(invoice.getInternetPrice())
			.generalPrice(invoice.getGeneralPrice())
			.dueDate(invoice.getDueDate())
			.paymentDate(invoice.getPaymentDate())
			.electricityNum(invoice.getElectricityNum())
			.waterNum(invoice.getWaterNum())
			.rentedRoom(RentedRoomMapper.toDto(invoice.getRentedRoom()))
			.createdAt(invoice.getCreatedAt())
			.updatedAt(invoice.getUpdatedAt())
			.build();
	}

	public static Invoice toEntity(InvoiceDto invoiceDto) {
		Invoice invoice = new Invoice();
		merge(invoice, invoiceDto);
		invoice.setCreatedAt(invoiceDto.getCreatedAt());
		invoice.setUpdatedAt(invoiceDto.getUpdatedAt());
		return invoice;
	}

	public static void merge(Invoice invoice, InvoiceDto invoiceDto) {
		invoice.setElectricityPrice(invoiceDto.getElectricityPrice());
		invoice.setWaterPrice(invoiceDto.getWaterPrice());
		invoice.setRoomPrice(invoiceDto.getRoomPrice());
		invoice.setInternetPrice(invoiceDto.getInternetPrice());
		invoice.setGeneralPrice(invoiceDto.getGeneralPrice());
		invoice.setDueDate(invoiceDto.getDueDate());
		invoice.setPaymentDate(invoiceDto.getPaymentDate());
		invoice.setElectricityNum(invoiceDto.getElectricityNum());
		invoice.setWaterNum(invoiceDto.getWaterNum());
	}
}
