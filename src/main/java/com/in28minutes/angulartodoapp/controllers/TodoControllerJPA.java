package com.in28minutes.angulartodoapp.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.in28minutes.angulartodoapp.todo.Todo;
import com.in28minutes.angulartodoapp.todo.repository.TodoRepositoryJPA;

@RestController
public class TodoControllerJPA {
	
	@Autowired
	private TodoRepositoryJPA todoRepository;
	
	@GetMapping("/test")
	public int testController() {
		return 200;
	}
	
	@GetMapping("/users/{username}/todos")
	public List<Todo> getTodosOfASpecificUser(@PathVariable String username){
		return todoRepository.findByUsername(username);
	}
	
	@GetMapping("/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable int id) {
		return todoRepository.findById(id).get();
	}
	
	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable int id) {
		todoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/users/{username}/todos/{id}")
	public Todo updateTodo(@PathVariable String username, @PathVariable int id, @RequestBody Todo todo) {
		todo.setId(id);
		todo.setUsername(username);
//		todo.setDone(false);
		todoRepository.save(todo);
		return todo;
	}
	
	@PostMapping(path="/users/{username}/todos")
	public ResponseEntity<Todo> addTodo(@PathVariable String username, @RequestBody Todo todo) {
		todo.setUsername(username);
//		todo.setDone(false);
		Todo newTodo = todoRepository.save(todo);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTodo.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
