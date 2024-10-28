package com.todolistservice.service;

import com.todolistservice.dto.TaskDto;
import com.todolistservice.model.Task;
import com.todolistservice.mapper.ToDoMapper;
import com.todolistservice.repository.ToDoTaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoTaskService {
	private final ToDoTaskRepository toDoTaskRepository;
	private final ToDoMapper toDoMapper;


	public ToDoTaskService(ToDoTaskRepository toDoTaskRepository, ToDoMapper toDoMapper) {
		this.toDoTaskRepository = toDoTaskRepository;
		this.toDoMapper = toDoMapper;
	}

	public void add(TaskDto taskDto) {
		toDoTaskRepository.save(toDoMapper.toTask(taskDto));
	}

	public List<TaskDto> getTasksByPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Task> taskPage = toDoTaskRepository.findAll(pageable);
		return taskPage.getContent().stream().map(toDoMapper::toDto).toList();
	}


}
