package com.tasks.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tasks.dto.CreateTaskRequest;
import com.tasks.dto.UpdateTaskRequest;
import com.tasks.dto.UpdateTaskStatusRequest;
import com.tasks.model.Label;
import com.tasks.model.Task;
import com.tasks.model.User;
import com.tasks.repository.LabelRepository;
import com.tasks.repository.TaskRepository;
import com.tasks.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	private final LabelRepository labelRepository;
	
	public TaskService(TaskRepository taskRepository, UserRepository userRepository, LabelRepository labelRepository) {
        this.taskRepository = taskRepository;
		this.userRepository = userRepository;
		this.labelRepository = labelRepository;
    }
	
	// Helpers
	
	private Task getTaskFromAuthenticatedUser(Long taskId) {

	    String email = SecurityContextHolder
	        .getContext()
	        .getAuthentication()
	        .getName();

	    User user = userRepository.findByEmail(email)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    Task task = taskRepository.findById(taskId)
	        .orElseThrow(() -> new RuntimeException("Task not found"));

	    if (!task.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("Task does not belong to user");
	    }

	    return task;
	}
	
	// Gets
	public List<Task> getAllTasks() {
	    return taskRepository.findAll();
	}
	
	public Optional<Task> getTask(Long id) {
		return taskRepository.findById(id);
	};
	
	// Posts
	public Task createTask(CreateTaskRequest taskReq) {

	    String email = SecurityContextHolder
	        .getContext()
	        .getAuthentication()
	        .getName();

	    User user = userRepository.findByEmail(email)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    Task newTask = new Task(taskReq.getTitle(), taskReq.getDescription(), user);

	    List<Long> labelIds = taskReq.getLabelIds();
	    List<Label> labels = labelIds.stream()
		        .map(labelId -> labelRepository.findByIdAndUser(labelId, user)
		            .orElseThrow(() -> new RuntimeException("Label not found")))
		        .toList();
	    newTask.setLabels(labels);
	    newTask.setDueDate(taskReq.getDueDate());
	    newTask.setDueTime(taskReq.getDueTime());
	    newTask.setCompleted(false);
	    
	    return taskRepository.save(newTask);
	}
	
	//Updates
	public Task updateTitleAndDescriptionAndDueDateTimeTask(Long id, UpdateTaskRequest request ) {
		Task task = getTaskFromAuthenticatedUser(id);
		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setDueDate(request.getDueDate());
		task.setDueTime(request.getDueTime());
		return taskRepository.save(task);
	};
	
	public Task updateStatusTask(Long id, UpdateTaskStatusRequest request ) {
		Task task = getTaskFromAuthenticatedUser(id);
		task.setCompleted(request.getCompleted());
		return taskRepository.save(task);
	};
	
	public Task updateLabelsTask(Long id, List<Long> labelIds) {

	    System.out.println(labelIds);
	    System.out.println(id);

	    Task task = getTaskFromAuthenticatedUser(id);

	    List<Label> labels = labelIds.stream()
	        .map(labelId -> labelRepository.findByIdAndUser(labelId, task.getUser())
	            .orElseThrow(() -> new RuntimeException("Label not found")))
	        .collect(Collectors.toList());

	    task.setLabels(labels);

	    return taskRepository.save(task);
	}
	
	
	//Deletes
	public void deleteTask(Long id) {
		Task task = getTaskFromAuthenticatedUser(id);
		taskRepository.delete(task);
	};
}
