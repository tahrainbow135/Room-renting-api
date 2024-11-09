package com.tuanh.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class BaseDto {
	protected Instant createdAt;
	protected Instant updatedAt;
}
