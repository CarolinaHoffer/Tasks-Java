package com.tasks.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasks.model.Label;
import com.tasks.model.Task;
import com.tasks.model.User;

public interface TaskRepository extends JpaRepository<Task, Long>  {

	List<Task> findByUserIdOrderByCreatedAtDesc(Long userId);
	List<Task> findByUserAndLabelsContaining(User user, Label label);
    List<Task> findByUserAndDueDate(User user, LocalDate dueDate);
}
