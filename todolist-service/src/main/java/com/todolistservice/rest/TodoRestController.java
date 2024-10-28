package com.todolistservice.rest;

import com.todolistservice.dto.TaskDto;
import com.todolistservice.model.Task;
import com.todolistservice.service.ToDoTaskService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/tasks")
public class TodoRestController {
	private final RabbitTemplate rabbitTemplate;

	private final ToDoTaskService toDoTaskService;

	public TodoRestController(RabbitTemplate rabbitTemplate, ToDoTaskService toDoTaskService) {
		this.rabbitTemplate = rabbitTemplate;
		this.toDoTaskService = toDoTaskService;
	}

	@PostMapping
	public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto task) {
		toDoTaskService.add(task);
		rabbitTemplate.convertAndSend("tasksExchange", "task.created", task);
		return ResponseEntity.status(HttpStatus.CREATED).body(task);
	}

	@GetMapping({"/", "/{page}/{size}"})
	public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable(required = false) int page,
			                                      @PathVariable(required = false) int size) {
		page = page == 0 ? 1 : page;
		size = size == 0 ? 1 : size;
		return ResponseEntity.ok(toDoTaskService.getTasksByPage(page, size));
	}
}
