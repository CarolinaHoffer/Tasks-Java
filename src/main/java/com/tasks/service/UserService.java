package com.tasks.service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.tasks.constant.ErrorCode;
import com.tasks.dto.ChangePasswordRequest;
import com.tasks.dto.UpdateNameUser;
import com.tasks.exception.BadRequestException;
import com.tasks.model.Label;
import com.tasks.model.Task;
import com.tasks.model.User;
import com.tasks.repository.LabelRepository;
import com.tasks.repository.TaskRepository;
import com.tasks.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TaskRepository taskRepository;
	private final LabelRepository labelRepository;
	
	public UserService(
		    UserRepository userRepository,
		    PasswordEncoder passwordEncoder,
		    TaskRepository taskRepository,
		    LabelRepository labelRepository
		) {
		    this.userRepository = userRepository;
		    this.passwordEncoder = passwordEncoder;
		    this.taskRepository = taskRepository;
		    this.labelRepository = labelRepository;
		}
	
	//Helpers
	private User getAuthenticatedUser() {

	    String email = SecurityContextHolder
	        .getContext()
	        .getAuthentication()
	        .getName();

	    User user = userRepository.findByEmail(email)
	        .orElseThrow(() -> new BadRequestException(
        	        ErrorCode.USER_NOT_FOUND
        	    )); 

	    return user; 
	}
	
	// Gets
	public List<User> getAllUsers() {
	    return userRepository.findAll();
	}
	
	public User getUser() {
	    User user = getAuthenticatedUser();
		return user;
	};
	
	public List<Task> getMyTasks() {
		User user = getAuthenticatedUser();
	    return user.getTasks();
	}
	
	public List<Task> getMyTasksByLabel(Long idLabel) {
	    User user = getAuthenticatedUser();
	    Label label = labelRepository.findByIdAndUser(idLabel, user)
	    	    .orElseThrow(() -> new BadRequestException(
	        	        ErrorCode.LABEL_NOT_FOUND
	        	    ));
	    return taskRepository.findByUserAndLabelsContaining(user, label);
	}
	
	public List<Task> getMyTasksByDate(LocalDate date) {
	    User user = getAuthenticatedUser();
	    return taskRepository.findByUserAndDueDate(user, date);
	}
	
	public List<Task> getMyPendingTasks() {

	    User user = getAuthenticatedUser();
	    LocalDateTime now = LocalDateTime.now();

	    return user.getTasks().stream()
	        .filter(task -> !task.getCompleted())
	        .filter(task -> task.getDueDate() != null)
	        .filter(task -> {
	            if (task.getDueTime() == null) {
	                return task.getDueDate().isAfter(now.toLocalDate());
	            }

	            LocalDateTime dueDateTime = LocalDateTime.of(
	                task.getDueDate(),
	                task.getDueTime()
	            );

	            return dueDateTime.isAfter(now);
	        })
	        .toList();
	}

	public List<Task> getMyOverdueTasks() {

	    User user = getAuthenticatedUser();
	    LocalDateTime now = LocalDateTime.now();

	    return user.getTasks().stream()
	        .filter(task -> !task.getCompleted())
	        .filter(task -> task.getDueDate() != null)
	        .filter(task -> {
	            if (task.getDueTime() == null) {
	                return task.getDueDate().isBefore(now.toLocalDate());
	            }

	            LocalDateTime dueDateTime = LocalDateTime.of(
	                task.getDueDate(),
	                task.getDueTime()
	            );

	            return dueDateTime.isBefore(now);
	        })
	        .toList();
	}

	public List<Task> getMyCompletedTasks() {

	    User user = getAuthenticatedUser();

	    return user.getTasks().stream()
	        .filter(Task::getCompleted)
	        .toList();
	}
	
	// Posts
	public User createUser(User user) {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    return userRepository.save(user);
	}
	
	//Updates
	public User updateFirstNameAndLastNameUser(UpdateNameUser request ) {
		User user = getAuthenticatedUser();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		return userRepository.save(user);
	};  
	
	public User updateEmailUser(String newEmail ) {
	    User userWithMyEmail = getAuthenticatedUser();
				
		userRepository.findByEmailAndIdNot(newEmail, userWithMyEmail.getId())
	    .ifPresent(userWithEmail -> {
	    	throw new BadRequestException(
        	        ErrorCode.EMAIL_ALREADY_USED
        	    );
	    });
		userWithMyEmail.setEmail(newEmail);
		return userRepository.save(userWithMyEmail);
	};
	
	public void changePassword(
	        ChangePasswordRequest request
	    ) {
			
			User user = getAuthenticatedUser();

	        if (!passwordEncoder.matches(
	            request.getCurrentPassword(),
	            user.getPassword()
	        )) {
	        	throw new BadRequestException(
	        	        ErrorCode.CURRENT_PASSWORD_INCORRECT
	        	    );
	        }

	        if (request.getNewPassword().equals(
	            request.getCurrentPassword()
	        )) {
	        	throw new BadRequestException(
	        	        ErrorCode.PASSWORDS_MUST_BE_DIFFERENT
	        	    );
	        }

	        if (request.getNewPassword().length() < 6) {
	        	throw new BadRequestException(
	        	        ErrorCode.PASSWORD_TOO_SHORT
	        	    );
	        }

	        user.setPassword(
	            passwordEncoder.encode(request.getNewPassword())
	        );

	        userRepository.save(user);
	    }
	
	
	//Deletes
	public void deleteUser() {
		User user = getAuthenticatedUser();
		userRepository.delete(user);
	}

}
