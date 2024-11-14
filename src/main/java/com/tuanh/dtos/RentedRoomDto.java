package com.tuanh.dtos;

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
	private String tenantName;
	private String tenantPhone;
	private LocalDate startDate;
	private LocalDate endDate;
	private String contractUrl;

	private RoomDto room;
}
