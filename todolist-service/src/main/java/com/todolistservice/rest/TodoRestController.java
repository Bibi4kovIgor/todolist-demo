package com.todolistservice.rest;

import com.todolistservice.model.Task;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TodoRestController {
	private final RabbitTemplate rabbitTemplate;
	private final List<Task> tasks = new ArrayList<>();

	public TodoRestController(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@PostMapping
	public ResponseEntity<Task> addTask(@RequestBody Task task) {
		tasks.add(task);
		rabbitTemplate.convertAndSend("tasksExchange", "task.created", task);
		return ResponseEntity.status(HttpStatus.CREATED).body(task);
	}

	@GetMapping
	public ResponseEntity<List<Task>> getTasks() {
		return ResponseEntity.ok(tasks);
	}
}
