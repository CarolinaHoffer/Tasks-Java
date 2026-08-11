package com.tasks.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.dto.UpdateNameUser;
import com.tasks.model.Task;
import com.tasks.model.User;
import com.tasks.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Gets
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public User getUser() {
        return userService.getUser();
    }
    
    @GetMapping("/myTasks")
    public List<Task> getTasksByUser() {
        return userService.getMyTasks();
    }
    
    @GetMapping("/myTasks/label/{labelId}")
    public List<Task> getMyTasksByLabel(
            @PathVariable Long labelId) {
        return userService.getMyTasksByLabel(labelId);
    }

    @GetMapping("/myTasks/date/{date}")
    public List<Task> getMyTasksByDate(
            @PathVariable LocalDate date) {
        return userService.getMyTasksByDate(date);
    }
    
    @GetMapping("/myTasks/pending")
    public List<Task> getMyPendingTasks() {
        return userService.getMyPendingTasks();
    }

    @GetMapping("/myTasks/overdue")
    public List<Task> getMyOverdueTasks() {
        return userService.getMyOverdueTasks();
    }

    @GetMapping("/myTasks/completed")
    public List<Task> getMyCompletedTasks() {
        return userService.getMyCompletedTasks();
    }

    // Posts
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Updates
    @PatchMapping("/name")
    public User updateFirstNameAndLastNameUser(@RequestBody UpdateNameUser request) {
        return userService.updateFirstNameAndLastNameUser(request);
    }

    @PatchMapping("/email")
    public User updateEmailUser(@RequestBody String email) {
        return userService.updateEmailUser(email);
    }

    // Deletes
    @DeleteMapping("/me")
    public void deleteUser() {
        userService.deleteUser();
    }
}