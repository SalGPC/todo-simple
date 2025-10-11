package com.portafolio.todo_simple.controller;

import com.portafolio.todo_simple.model.Todo;
import org.springframework.web.bind.annotation.*;
import com.portafolio.todo_simple.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController
{


    private final TodoService todoService;

    public TodoController(TodoService thetodoService){
        this.todoService = thetodoService;
    }

    @GetMapping("/{id}")
   public Todo getTodo(@PathVariable int id){
        return todoService.getTodoById(id);

    }
ce

    public Todo getTodoById(int id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TODO not found"));
    }

    @PostMapping
     public Todo createTodo(@RequestBody  Todo newTodo) {
        return todoService.createTodo(newTodo);
    }

    @PutMapping("/{id}")
     public Todo updateTodo(@PathVariable int id, @RequestBody Todo updatedTodo){
        return todoService.updateTodo(id, updatedTodo);
    }



      @DeleteMapping("/{id}")
      public void deleteTodo(@PathVariable int id) {
          todoService.deleteTodo(id);
      }
}





