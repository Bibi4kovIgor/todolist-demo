package com.todolistservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public record TaskDto(int id, String text, LocalDateTime deadline) {
	private TaskDto(){
		this(0, "", LocalDateTime.now());
	}
}
