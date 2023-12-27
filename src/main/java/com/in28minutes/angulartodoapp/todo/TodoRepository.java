package com.in28minutes.angulartodoapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

//@Service
public class TodoRepository {
	
	private static List<Todo> todos=new ArrayList<Todo>();
	private static int count=0;
	
	static {
		todos.add(new Todo(++count, "in28minutes", "Learn Spring Boot", LocalDate.now().plusYears(2), false));
		todos.add(new Todo(++count, "in28minutes", "Learn Angular", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++count, "in28minutes", "Learn Docker", LocalDate.now().plusYears(3), false));
		todos.add(new Todo(++count, "in28minutes", "Learn GCP", LocalDate.now().plusYears(4), false));
		todos.add(new Todo(++count, "arijit", "Learn DevOps", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++count, "arijit", "Learn React.js", LocalDate.now().plusYears(2), false));
	}
	
	public List<Todo> fetchTodos(){
		return todos;
	}
	
	public List<Todo> findByUsername(String username){
		Predicate<? super Todo> predicate=todo -> todo.getUsername().equals(username);
		List<Todo> specificTodos = todos.stream().filter(predicate).collect(Collectors.toList());
		return specificTodos;
	}
	
	public Todo findById(int id) {
		return todos.stream().filter(todo -> todo.getId() == id).findFirst().get();
	}
	
	public void deleteById(int id) {
		Predicate<? super Todo> predicate=todo -> todo.getId()==id;
		todos.removeIf(predicate);
	}
	
	public boolean updateTodo(Todo todo) {
		deleteById(todo.getId());
		boolean isAdded = todos.add(todo);
		return (isAdded == true ? true : false);
	}
	
	public Todo addNewTodo(String username, String description, LocalDate targetDate, boolean isDone) {
		Todo todo=new Todo(++count, username, description, targetDate, isDone);
		todos.add(todo);
		return todo;
	}
}
