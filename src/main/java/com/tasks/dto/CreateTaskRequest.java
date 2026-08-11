package com.tasks.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.tasks.model.Label;

import jakarta.persistence.ElementCollection;

public class CreateTaskRequest {

	
	private String title;
	private String description;
	private Boolean completed;
	private List<Long> labelIds = new ArrayList<>();
	private LocalDate dueDate;
	private LocalTime dueTime;
	
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
	public List<Long> getLabelIds() {
		return labelIds;
	}
	public void setLabelIds(List<Long> labels) {
		this.labelIds = labels;
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
	
	
}
