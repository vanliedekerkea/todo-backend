package com.todo.bck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.todo.bck.model.Todo;
import com.todo.bck.repository.TodoRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    // Get All Notes
    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
    	return todoRepository.findAll();
    }
    
    // Create a new Note
    @PostMapping("/todos")
    public Todo createTodo(@Valid @RequestBody Todo todo) {
    	return todoRepository.save(todo);
    }
    
    // Get a Single Note
    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable(value = "id") Long todoId) {
    	Optional<Todo> todo = todoRepository.findById(todoId);
    	if(!todo.isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok().body(todo.get());
    }
    
    // Update a Note
    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable(value="id") Long todoId,
    		@Valid @RequestBody Todo todoDetails) {
    	Optional<Todo> todo = todoRepository.findById(todoId);
    	if(!todo.isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
    	todo.get().setTitle(todoDetails.getTitle());
    	todo.get().setContent(todoDetails.getContent());
    	
    	Todo updatedTodo = todoRepository.save(todo.get());
    	return ResponseEntity.ok(updatedTodo);
    }
    
    // Delete a Note		
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable(value = "id") Long todoId) {
    	Optional<Todo> todo = todoRepository.findById(todoId);
    	if(!todo.isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
    	todoRepository.delete(todo.get());
    	return ResponseEntity.ok().build();
    }
    
    
    
}




