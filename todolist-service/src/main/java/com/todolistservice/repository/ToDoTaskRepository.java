package com.todolistservice.repository;

import com.todolistservice.model.Task;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoTaskRepository extends JpaRepository<Task, Integer> {
	@NonNull
	Page<Task> findAll(@NonNull Pageable pageable);
}
