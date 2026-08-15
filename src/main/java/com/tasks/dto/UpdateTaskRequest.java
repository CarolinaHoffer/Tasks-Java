package com.tasks.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateTaskRequest {
	   private String title;
	   private String description;
	   private LocalDate dueDate;
		private LocalTime dueTime;
		
	   public String getTitle() {
		   return title;
	   }
	   public String getDescription() {
		   return description;
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
