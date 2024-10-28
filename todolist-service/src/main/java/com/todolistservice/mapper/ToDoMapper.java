package com.todolistservice.mapper;

import com.todolistservice.dto.TaskDto;
import com.todolistservice.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoMapper {
	TaskDto toDto(Task task);
	Task toTask(TaskDto taskDto);
}
