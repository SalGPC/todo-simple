package com.portafolio.todo_simple.service;

import com.portafolio.todo_simple.model.Todo;
import com.portafolio.todo_simple.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override  // Added override annotation
    public Todo getTodoById(int id) {  // Corrected method name to match interface
        return todoRepository.findById(id).orElseThrow();
    }

    @Override
    public Todo  createTodo(Todo newTodo) {  // Corrected method name to match interface
        return todoRepository.save(newTodo);
    }

    @Override
    public void deleteTodo(int id) {  // Corrected return type to void
        todoRepository.deleteById(id);
    }

    @Override
    public Todo updateTodo(int id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setTitle(todoDetails.getTitle());
        todo.setCompleted(todoDetails.isCompleted());
        return todoRepository.save(todo);
    }
}