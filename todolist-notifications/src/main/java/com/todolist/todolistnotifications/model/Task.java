package com.todolist.todolistnotifications.model;

public class Task {
	private final int id;
	private final boolean status;

	public Task(int id, boolean status) {
		this.id = id;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task{" + "id=" + id + ", status=" + status + '}';
	}
}
