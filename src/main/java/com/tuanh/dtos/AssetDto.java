package com.tuanh.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AssetDto extends BaseDto {
	private Integer id;

	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
	private String name;

	@NotBlank(message = "Image Url is required")
	private String imageUrl;

	private RoomDto room;
}
