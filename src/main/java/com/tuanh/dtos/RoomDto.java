package com.tuanh.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoomDto extends BaseDto {
	private Integer id;
	private String name;
	private Integer capacity;
	private String description;
	private Boolean isCurrentlyRented;
	private HouseDto house;
}
