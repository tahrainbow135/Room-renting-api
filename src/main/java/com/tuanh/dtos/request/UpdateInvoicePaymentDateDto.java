package com.tuanh.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateInvoicePaymentDateDto {
	private LocalDate paymentDate;
}
