package com.tasks.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tasks.constant.ErrorCode;
import com.tasks.exception.BadRequestException;
import com.tasks.model.Label;
import com.tasks.model.Task;
import com.tasks.model.User;
import com.tasks.repository.LabelRepository;
import com.tasks.repository.TaskRepository;
import com.tasks.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class LabelService {

    private final LabelRepository labelRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public LabelService(
            LabelRepository labelRepository,
            UserRepository userRepository,
            TaskRepository taskRepository) {
        this.labelRepository = labelRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // Helpers

    private User getAuthenticatedUser() {

        String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new BadRequestException(
    	        	ErrorCode.USER_NOT_FOUND
        	    ));
    }

    // Gets

    public List<Label> getMyLabels() {
        User user = getAuthenticatedUser();

        return labelRepository.findByUser(user);
    }

    public Label getMyLabel(Long id) {
        User user = getAuthenticatedUser();

        return labelRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new BadRequestException(
    	        	ErrorCode.LABEL_NOT_FOUND
        	    ));
    }

    // Posts

    public Label createLabel(Label label) {

        User user = getAuthenticatedUser();

        labelRepository.findByNameAndUser(label.getName(), user)
            .ifPresent(existingLabel -> {
                throw new BadRequestException(
        	        	ErrorCode.LABEL_ALREADY_EXISTS
                	    );
            });

        label.setUser(user);

        return labelRepository.save(label);
    }

    // Updates

    public Label updateLabel(Long id, Label request) {

        User user = getAuthenticatedUser();

        Label label = labelRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new BadRequestException(
    	        	ErrorCode.LABEL_NOT_FOUND
        	    ));

        label.setName(request.getName());
        label.setColor(request.getColor());
        label.setIcon(request.getIcon());

        return labelRepository.save(label);
    }

    // Deletes
    @Transactional
    public void deleteLabel(Long id) {

        User user = getAuthenticatedUser();

        Label label = labelRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new BadRequestException(
    	        	ErrorCode.LABEL_NOT_FOUND
        	    ));
        
        List<Task> tasks = taskRepository.findByUserAndLabelsContaining(user, label);

        for (Task task : tasks) {
            task.getLabels().remove(label);
        }
        taskRepository.saveAll(tasks);

        labelRepository.delete(label);
    }
}

