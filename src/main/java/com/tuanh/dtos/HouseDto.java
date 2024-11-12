package com.tuanh.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class HouseDto extends BaseDto {
	private Integer id;
	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Address line is required")
	private String addressLine;

	@NotBlank(message = "Ward is required")
	private String ward;

	@NotBlank(message = "District is required")
	private String district;

	@NotBlank(message = "City is required")
	private String city;

	@NotNull(message = "Floor count is required")
	@Min(value = 1, message = "Floor count must be greater than 0")
	private Integer floorCount;

	private UserDto owner;
}
