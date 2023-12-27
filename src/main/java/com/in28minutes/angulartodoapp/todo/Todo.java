package com.in28minutes.angulartodoapp.todo;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Todo {
	
	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String description;
	private LocalDate targetDate;
	private boolean isDone;
	
	public Todo(int id, String username, String description, LocalDate targetDate, boolean isDone) {
		this.id=id;
		this.username=username;
		this.description=description;
		this.targetDate=targetDate;
		this.isDone=isDone;
	}
	
	public Todo() {
		
	}
	
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Todo"+this.id+": "+this.username+" "+this.description+" "+this.targetDate;
	}
}
