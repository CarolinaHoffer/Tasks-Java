package com.tasks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;


@Entity
public class Task {
	// Definitions
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String description;
	private Boolean completed;
	private LocalDateTime createdAt;
	private LocalDate dueDate;
	private LocalTime dueTime;
	
	@ManyToMany
	private List<Label> labels = new ArrayList<>();
	
	@ManyToOne
	private User user;
	
	// Getters and setters
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean getCompleted() {
		return completed;
	}
	
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	public List<Label> getLabels() {
	    return labels;
	}

	public void setLabels(List<Label> labels) {
	    this.labels = labels;
	}
	
	public User getUser() {
		return user;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalTime getDueTime() {
		return dueTime;
	}

	public void setDueTime(LocalTime dueTime) {
		this.dueTime = dueTime;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
	 
	// Constructors
	public Task() {}
	
	public Task(String title, String description, User user) {
	    this.title = title;
	    this.description = description;
	    this.completed = false;
	    this.user = user;
	}
}
