package com.tuanh.dtos;

import jakarta.validation.constraints.NotBlank;
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
public class RentedRoomDto extends BaseDto {
	private Integer id;

	@NotBlank(message = "Tenant name is required")
	private String tenantName;

	@NotBlank(message = "Tenant phone is required")
	private String tenantPhone;

	@NotNull(message = "Number of tenants is required")
	private Integer numberOfTenants;

	@NotNull(message = "Start date is required")
	private LocalDate startDate;

	@NotNull(message = "End date is required")
	private LocalDate endDate;

	@NotNull(message = "Payment day is required")
	private Integer paymentDay;

	@NotBlank(message = "Contract URL is required")
	private String contractUrl;

	@NotNull(message = "Price is required")
	private Integer price;

	@NotNull(message = "Electricity price is required")
	private Integer electricityPrice;

	@NotNull(message = "Water price is required")
	private Integer waterPrice;

	@NotNull(message = "Internet price is required")
	private Integer internetPrice;

	@NotNull(message = "General price is required")
	private Integer generalPrice;

	@NotNull(message = "Initial electricity number is required")
	private Integer initElectricityNum;

	@NotNull(message = "Initial water number is required")
	private Integer initWaterNum;

	private RoomDto room;
}
