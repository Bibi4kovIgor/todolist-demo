package com.todolist.todolistnotifications.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.todolist.todolistnotifications.model.Task;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

	@RabbitListener(queues = "taskQueue")
	public void receiveTask(Task task) {
		System.out.println("Notification Received: " + task);
	}
}
