package com.tasks.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.dto.CreateTaskRequest;
import com.tasks.dto.UpdateTaskRequest;
import com.tasks.dto.UpdateTaskStatusRequest;
import com.tasks.model.Task;
import com.tasks.service.TaskService;

@RestController
public class TaskController {

	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	// POST
	@PostMapping("/tasks")
	public Task createTask(@RequestBody CreateTaskRequest task) {
	    return taskService.createTask(task);
	}
	
	// GET
	@GetMapping("/tasks")
	public List<Task> getAllTasks() {
	    return taskService.getAllTasks();
	}
	
	@GetMapping("/tasks/{id}")
	public Optional<Task> getTaskById(@PathVariable Long id){
		return taskService.getTask(id);
	}
	
	// PUT
	@PutMapping("/tasks/{id}")
	public Task updateTitleAndDescriptionTask(@PathVariable Long id,  @RequestBody UpdateTaskRequest request){
		return taskService.updateTitleAndDescriptionAndDueDateTimeTask(id, request);
	}
	
	//PATCH	
	@PatchMapping("/tasks/{id}/status")
	public Task updateStatusTask(@PathVariable Long id, @RequestBody UpdateTaskStatusRequest request) {
		return taskService.updateStatusTask(id, request);
	}
	
	@PatchMapping("/tasks/{id}/labels")
	public Task updateLabelsTask(@PathVariable Long id, @RequestBody List<Long> labels) {
		return taskService.updateLabelsTask(id, labels);
	}
	
	// DELETE
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id){
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
