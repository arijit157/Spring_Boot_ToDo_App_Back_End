package com.in28minutes.angulartodoapp.todo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.in28minutes.angulartodoapp.todo.Todo;

public interface TodoRepositoryJPA extends JpaRepository<Todo, Integer> {
	List<Todo> findByUsername(String username);
}
