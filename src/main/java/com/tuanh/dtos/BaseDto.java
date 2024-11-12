package com.tuanh.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDto {
	protected Instant createdAt;
	protected Instant updatedAt;
}
