package com.tuanh.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InvoiceDto extends BaseDto {
	private Integer id;
	private Integer totalPrice;
	private Integer electricityPrice;
	private Integer waterPrice;
	private Integer roomPrice;
	private Integer internetPrice;
	private Integer generalPrice;
	private LocalDate dueDate;
	private LocalDate paymentDate;

	@NotNull(message = "Electricity number is required")
	private Integer electricityNum;

	@NotNull(message = "Water number is required")
	private Integer waterNum;

	private RentedRoomDto rentedRoom;
}
